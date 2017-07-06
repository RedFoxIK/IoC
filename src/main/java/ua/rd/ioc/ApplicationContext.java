package ua.rd.ioc;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Considered that bean has only one construct - non arg or with arg but not
 * both of them
 * <p>
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
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
            String beanName = beanDefinition.getBeanName();

            // TODO: 7/5/2017 ignore prototypes
            createBean(beanName);
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
        // TODO: 7/5/2017 if prototype create bean
        // call destroy on prototype because it is not in context

        return (T) context.get(beanName);
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

        // TODO: 7/5/2017 consider prototype
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Class<?> beanType = beanDefinition.getType();
        Constructor<?> beanConstructor = beanType.getConstructors()[0];
        Object bean = null;
        if (beanConstructor.getParameterCount() == 0) {
            bean = createBeanWithNoArgConstructor(beanType);
        } else {
            // TODO: 7/5/2017 implement
            // bean = createBeanWithArgConstruct();
        }

        callInitMethod(bean);
        bean = createBenchmarkProxy(bean);

        context.put(beanName, bean);
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
