package ua.rd.ioc;

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
        } else {
            bean = (T) Arrays.stream(config.getBeanDefinitions())
                    .filter(e -> e.getBeanName().equals(beanName))
                    .findAny()
                    .map(b -> b.getType())
                    .map(c -> newInstance(c))
                    .orElse(null);

            context.put(beanName, bean);
        }

        return bean;
    }

    private <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
