package ua.rd.ioc;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public interface Context {
    String[] getBeanDefinitionNames();

    <T> T getBean(String tweet);
}
