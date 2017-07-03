package ua.rd.ioc;


public class SimpleBeanDefinition implements BeanDefinition {
    private final String beanName;
    private final Class<?> type;

    public SimpleBeanDefinition(String beanName, Class<?> type) {
        this.beanName = beanName;
        this.type = type;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }
}
