package ua.rd.ioc;

/**
 * Created by Vitalii_Sharapov on 7/6/2017.
 */
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
