package ua.rd.ioc;


public class Bean {
    private Class<?> clazz;
    private boolean isPrototype;

    public Bean(Class<?> clazz, boolean isPrototype) {
        this.clazz = clazz;
        this.isPrototype = isPrototype;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isPrototype() {
        return isPrototype;
    }
}
