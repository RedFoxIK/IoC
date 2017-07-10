package ua.rd.ioc;


public class SimpleBeanDefinition implements BeanDefinition {

    private final String name;
    private final Class<?> clazz;
    private final boolean isPrototype;

    public SimpleBeanDefinition(String name, Class<?> value) {
        this(name, value, false);
    }

    public SimpleBeanDefinition(String name,
                                Class<?> clazz, boolean isPrototype) {
        this.name = name;
        this.clazz = clazz;
        this.isPrototype = isPrototype;
    }

    @Override
    public String getBeanName() {
        return name;
    }

    @Override
    public <T> Class<T> getType() {
        return (Class<T>) clazz;
    }

    @Override
    public boolean isPrototype() {
        return isPrototype;
    }
}
