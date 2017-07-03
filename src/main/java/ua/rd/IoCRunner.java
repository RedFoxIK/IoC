package ua.rd;

import ua.rd.domain.Tweet;
import ua.rd.ioc.ApplicationContext;
import ua.rd.ioc.Config;
import ua.rd.ioc.Context;
import ua.rd.ioc.JavaConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IoCRunner {
    public static void main(String[] args) {
        Map<String, Class<?>> beanDefinitions = new HashMap<String, Class<?>>() {{
            put("tweet", Tweet.class);
        }};
        Config config = new JavaConfig(beanDefinitions);
        Context context = new ApplicationContext(config);
        System.out.println(Arrays.toString(context.getBeanDefinitionsNames()));
        Tweet tweet = context.getBean("tweet");
        System.out.println(tweet);
    }
}
