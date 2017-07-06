package ua.rd.ioc;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */

import org.junit.Test;
import ua.rd.domain.Tweet;
import ua.rd.repository.InMemTweetRepository;
import ua.rd.repository.TweetRepository;
import ua.rd.service.SimpleTweetService;
import ua.rd.service.TweetService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ApplicationContextTest {

    private static final String PROTOTYPE_BEAN = "proto";
    private static final String TWEET_BEAN = "tweet";
    private static final String TWEET_REPO_BEAN = "tweetRepository";
    private static final String TWEET_SERVICE_BEAN = "tweetService";

    @Test
    public void testInitContextWithoutConfig() throws Exception {
        Context context = new ApplicationContext();

        assertNotNull(context);
    }

    @Test
    public void testInitContextWithEmptyJavaConfig() throws Exception {
        Config config = new JavaConfig();
        Context context = new ApplicationContext(config);

        assertNotNull(context);
    }

    @Test
    public void testBeanDefinitionNamesWithoutConfig() throws Exception {
        Context context = new ApplicationContext();

        String[] names = context.getBeanDefinitionNames();

        assertEquals(0, names.length);
    }

    @Test
    public void testBeanDefinitionNamesWithEmptyConfig() throws Exception {
        Config config = new JavaConfig();

        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        assertEquals(0, beanDefinitions.length);
    }

    @Test
    public void testBeanDefinitionWithJavaConfig() throws Exception {
        Map<String, Bean> beanDescriptions = createBeanDescriptions();
        Config config = new JavaConfig(beanDescriptions);

        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        assertEquals(2, beanDefinitions.length);
    }

    @Test
    public void testBeanDefinitionNamesWithJavaConfig() throws Exception {
        Map<String, Bean> beanDescriptions = createBeanDescriptions();
        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        String[] names = context.getBeanDefinitionNames();

        assertTrue(Arrays.asList(names).contains(TWEET_BEAN));
    }

    private Map<String, Bean> createBeanDescriptions() {
        Map<String, Bean> beanDescriptions = new HashMap<>();
        beanDescriptions.put(TWEET_BEAN, new Bean(Tweet.class, false));
        beanDescriptions.put(TWEET_REPO_BEAN,
                new Bean(InMemTweetRepository.class, false));
        return beanDescriptions;
    }

    @Test
    public void testGetBean() throws Exception {
        Map<String, Bean> beanDescriptions = createBeanDescriptions();
        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        Object bean = context.getBean(TWEET_BEAN);
        assertNotNull(bean);
    }

    @Test
    public void testGetTwoSameBeansEqual() throws Exception {
        Map<String, Bean> beanDescriptions = createBeanDescriptions();
        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        Object bean1 = context.getBean(TWEET_BEAN);
        Object bean2 = context.getBean(TWEET_BEAN);

        assertSame(bean1, bean2);
    }

    @Test
    public void testGetTwoSameBeansAsPrototypeNotEqual() throws Exception {
        Map<String, Bean> beanDescriptions = new HashMap<>();
        beanDescriptions.put(PROTOTYPE_BEAN,
                new Bean(Tweet.class, true));
        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        Object bean1 = context.getBean(PROTOTYPE_BEAN);
        Object bean2 = context.getBean(PROTOTYPE_BEAN);

        assertNotSame(bean1, bean2);
    }

    @Test
    public void testInitMethodCalled() throws Exception {
        Map<String, Bean> beanDescriptions = createBeanDescriptions();
        Config config = new JavaConfig(beanDescriptions);
        Context context = new ApplicationContext(config);

        TweetRepository tweetRepository = context.getBean(TWEET_REPO_BEAN);

        assertNotNull(tweetRepository.getAllTweets());
    }

    @Test
    public void testBeanWithArgConstructor() throws Exception {
        Map<String, Bean> beanDescription = createBeanDescriptions();
        beanDescription.put(TWEET_SERVICE_BEAN,
                new Bean(SimpleTweetService.class, false));
        Config config = new JavaConfig(beanDescription);
        Context context = new ApplicationContext(config);

        TweetService tweetService = context.getBean(TWEET_SERVICE_BEAN);

        assertTrue(tweetService.getAllTweets().iterator().hasNext());
    }
}
