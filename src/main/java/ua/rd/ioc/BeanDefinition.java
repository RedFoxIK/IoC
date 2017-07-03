package ua.rd.ioc;

public interface BeanDefinition {
    String getBeanName();
    <T> Class<T> getType();
}
