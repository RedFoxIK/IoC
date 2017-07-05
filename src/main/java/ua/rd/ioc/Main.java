package ua.rd.ioc;

import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, Class<?>> beanDescriptions = new HashMap<>();
        beanDescriptions.put("tweet", Tweet.class);

        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        System.out.println("Bean definitions : " + Arrays.toString(config.getBeanDefinitions()));
        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

        Tweet tweet = context.getBean("tweet");

        System.out.println("Bean name : " + tweet);
    }
}
