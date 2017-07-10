package ua.rd.ioc;

import java.util.Map;


public class JavaConfig implements Config {

    private BeanDefinition[] beanDefinitions;

    public JavaConfig() {
        this.beanDefinitions = new BeanDefinition[0];
    }

    public JavaConfig(Map<String, Bean> beanDescriptions) {
        this.beanDefinitions = beanDescriptions.entrySet()
                .stream()
                .map(this::beanDefinitionFromMapEntry)
                .toArray(BeanDefinition[]::new);
    }

    private SimpleBeanDefinition beanDefinitionFromMapEntry(
            Map.Entry<String, Bean> entry) {
        Bean bean = entry.getValue();
        return new SimpleBeanDefinition(
                entry.getKey(), bean.getClazz(), bean.isPrototype());
    }

    @Override
    public BeanDefinition[] getBeanDefinitions() {
        return beanDefinitions;
    }

    @Override
    public void addBeanDefinition(BeanDefinition beanDefinition) {
        int length = beanDefinitions.length;
        BeanDefinition[] result = new BeanDefinition[length + 1];
        System.arraycopy(beanDefinitions, 0, result, 0, length);
        result[length] = beanDefinition;
        this.beanDefinitions = result;
    }
}
