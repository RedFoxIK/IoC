package ua.rd.ioc;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public class SimpleBeanDefinition implements BeanDefinition {
    private final String name;
    private final Class<?> clazz;

    public SimpleBeanDefinition(String name, Class<?> value) {
        this.name = name;
        this.clazz = value;
    }

    @Override
    public String getBeanName() {
        return name;
    }

    @Override
    public <T> Class<T> getType() {
        return (Class<T>) clazz;
    }
}
