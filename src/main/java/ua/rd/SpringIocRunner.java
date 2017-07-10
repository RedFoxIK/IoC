package ua.rd;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.repository.TweetRepository;

import java.util.Arrays;

public class SpringIocRunner {
    public static void main(String[] args) {
        GenericBeanDefinition genericBeanDefinition;
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        TweetRepository tweetRepository = (TweetRepository) context.getBean("tweetRepository");
        BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition("tweetRepository");

        System.out.println(beanDefinition);
        System.out.println(tweetRepository.getAllTweets());
        System.out.println(tweetRepository);

        context.close();
    }
}
