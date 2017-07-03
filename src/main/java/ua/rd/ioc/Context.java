package ua.rd.ioc;

public interface Context {
    String[] getBeanDefinitionsNames();
    <T> T getBean(String beanName);
}
