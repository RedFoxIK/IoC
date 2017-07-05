package ua.rd.ioc;

import java.util.Map;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public class JavaConfig implements Config {

    private final BeanDefinition[] beanDefinitions;

    public JavaConfig() {
        this.beanDefinitions = new BeanDefinition[0];
    }

    public JavaConfig(Map<String, Class<?>> beanDescriptions) {
        this.beanDefinitions = beanDescriptions.entrySet()
                .stream()
                .map(e -> new SimpleBeanDefinition(e.getKey(), e.getValue()))
                .toArray(BeanDefinition[]::new);
    }

    @Override
    public BeanDefinition[] getBeanDefinitions() {
        return beanDefinitions;
    }
}
