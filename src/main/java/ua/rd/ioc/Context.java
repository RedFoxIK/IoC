package ua.rd.ioc;


public interface Context {
    String[] getBeanDefinitionNames();

    <T> T getBean(String tweet);
}
