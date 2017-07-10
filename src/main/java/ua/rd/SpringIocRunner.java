package ua.rd;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.repository.TweetRepository;
import ua.rd.service.TweetService;

import java.util.Arrays;

public class SpringIocRunner {
    public static void main(String[] args) {
        GenericBeanDefinition genericBeanDefinition;
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                "appContext.xml"
        );

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext(
                new String[]{"repoContext.xml"}, context
        );
        ConfigurableApplicationContext serviceContext = new ClassPathXmlApplicationContext(
                new String[]{"serviceContext.xml"}, repoContext
        );
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        TweetService tweetService = (TweetService) serviceContext.getBean("tweetService");
//        TweetRepository tweetRepository = (TweetRepository) context.getBean("tweetRepository");
        BeanDefinition beanDefinition = serviceContext.getBeanFactory().getBeanDefinition("tweetService");

        System.out.println(beanDefinition);
        System.out.println(tweetService.getAllTweets());
        System.out.println(tweetService);

        serviceContext.close();
        repoContext.close();
        context.close();
    }
}
