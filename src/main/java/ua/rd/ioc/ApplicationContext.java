package ua.rd.ioc;

import java.util.Arrays;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public class ApplicationContext implements Context {

    private Config config;

    public ApplicationContext() {
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
    public <T> T getBean(String tweet) {
        return (T) Arrays.stream(config.getBeanDefinitions())
                .filter(e -> e.getBeanName().equals(tweet))
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
