package ua.rd.ioc;


public interface Config {
    BeanDefinition[] getBeanDefinitions();

    void addBeanDefinition(BeanDefinition beanDefinition);
}
