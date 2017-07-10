package ua.rd.ioc;

import java.lang.reflect.*;
import java.util.*;

public class ApplicationContext implements Context {

    private final Config config;
    private final Map<String, Object> context = new HashMap<>();

    public ApplicationContext() {
        this(null);
    }

    public ApplicationContext(Config config) {
        this.config = config;

        if (config != null) {
            initApplicationContext();
        }
    }

    private void initApplicationContext() {
        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (!beanDefinition.isPrototype()) {
                createBean(beanDefinition.getBeanName());
            }
        }
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return config == null ? new String[0] :
                Arrays.stream(config.getBeanDefinitions())
                        .map(BeanDefinition::getBeanName)
                        .toArray(String[]::new);
    }

    @Override
    public <T> T getBean(String beanName) {
        return (T) Arrays.stream(config.getBeanDefinitions())
                .filter(bd -> bd.getBeanName().equals(beanName))
                .findFirst()
                .filter(BeanDefinition::isPrototype)
                .map(bd -> newInstance(bd.getType()))
                .orElse(context.get(beanName));
    }

    private <T> void callInitMethod(T bean) {
        Class<?> beanClass = bean.getClass();
        try {
            Method init = beanClass.getMethod("init");
            try {
                init.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchMethodException ignored) {
        }
    }

    private void createBean(String beanName) {
        if (context.containsKey(beanName)) {
            return;
        }

        Class<?> beanType = getBeanDefinition(beanName).getType();
        Constructor<?> beanConstructor = beanType.getConstructors()[0];

        Object bean;
        if (beanConstructor.getParameterCount() == 0) {
            bean = createBeanWithNoArgConstructor(beanType);
        } else {
            bean = createBeanWithArgConstruct(beanConstructor);
        }

        callInitMethod(bean);
        bean = createBenchmarkProxy(bean);

        context.put(beanName, bean);
    }

    private Object createBeanWithArgConstruct(Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List args = new ArrayList();
        for (Parameter parameter : parameters) {
            String beanName = generateBeanName(parameter);

            if (!context.containsKey(beanName)) {
                addBeanToContext(parameter.getType(), beanName);
            }

            args.add(getBean(beanName));
        }

        return newInstance(constructor, args.toArray());
    }

    private String generateBeanName(Parameter parameter) {
        String className = parameter.getType().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    private void addBeanToContext(Class<?> clazz, String beanName) {
        BeanDefinition beanDefinition = new SimpleBeanDefinition(
                beanName, clazz, false);

        // TODO: 7/6/2017 ask if updating config is ok
        config.addBeanDefinition(beanDefinition);

        createBean(beanName);
    }

    private Object newInstance(Constructor<?> constructor, Object[] args) {
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object createBenchmarkProxy(Object bean) {
        Class<?> beanType = bean.getClass();
        boolean hasMethodToBenchmark = Arrays.stream(beanType.getMethods())
                .anyMatch(m -> m.isAnnotationPresent(Benchmark.class));

        Object proxyBean = bean;
        if (hasMethodToBenchmark) {
            proxyBean = replaceWithBenchmarkProxy(bean);
        }

        return proxyBean;
    }

    private Object replaceWithBenchmarkProxy(Object bean) {
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> doBenchmark(bean, method, args)
        );
    }

    private Object doBenchmark(Object bean, Method method, Object[] args)
            throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Object result;

        Method actualMethod = bean.getClass().getMethod(method.getName());
        if (actualMethod.isAnnotationPresent(Benchmark.class)
                && actualMethod.getAnnotation(Benchmark.class).isOn()) {
            System.out.println("Benchmarking : " + method.getName());

            long before = System.nanoTime();
            result = method.invoke(bean, args);
            long after = System.nanoTime();

            System.out.println("Took : " + (after - before) + " ns");
        } else {
            result = method.invoke(bean, args);
        }

        return result;
    }

    private BeanDefinition getBeanDefinition(String beanName) {
        return Arrays.stream(config.getBeanDefinitions())
                .filter(e -> e.getBeanName().equals(beanName))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    private <T> T createBeanWithNoArgConstructor(Class<T> beanType) {
        return newInstance(beanType);
    }

    private <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
