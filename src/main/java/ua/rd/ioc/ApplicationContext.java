package ua.rd.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
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
        T bean = null;

        if (context.containsKey(beanName)) {
            bean = (T) context.get(beanName);
            return bean;
        }
        bean = createBeanWithNoArgConstructor(beanName);
        callInitMethod(bean);

        context.put(beanName, bean);

        return bean;
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

    private <T> T createBeanWithNoArgConstructor(String beanName) {
        return (T) Arrays.stream(config.getBeanDefinitions())
                .filter(e -> e.getBeanName().equals(beanName))
                .findAny()
                .map(b -> b.getType())
                .map(c -> newInstance(c))
                .orElse(null);
    }

    private <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
