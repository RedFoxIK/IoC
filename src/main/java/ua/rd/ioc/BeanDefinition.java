package ua.rd.ioc;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public interface BeanDefinition {
    String getBeanName();

    <T> Class<T> getType();

    boolean isPrototype();
}
