package ua.rd.ioc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

public class ApplicationContext implements Context {
    private Config config;

    public ApplicationContext() {

    }

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public String[] getBeanDefinitionsNames() {
        if ( config == null ) {
            return new String[]{};
        }

        return Arrays.stream(config.getBeanDefinitions())
                .map(bd -> bd.getBeanName())
                .toArray(String[]::new);
    }

    @Override
    public <T> T getBean(String beanName) {

        Optional<BeanDefinition> beanDef = Arrays.stream(config.getBeanDefinitions())
                .filter(bd -> bd.getBeanName().equals(beanName))
                .findAny();

        return (T) beanDef
                .map(BeanDefinition::getType)
                .map(cl -> newInstance(cl))
                .orElse(null);
    }

    private <T> T newInstance(Class <T> c1) {
        try {
            return c1.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
