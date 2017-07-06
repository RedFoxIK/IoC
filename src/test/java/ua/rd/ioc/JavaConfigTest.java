package ua.rd.ioc;

import org.junit.Test;
import ua.rd.domain.Tweet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vitalii_Sharapov on 7/6/2017.
 */
public class JavaConfigTest {

    @Test
    public void emptyConfigHasNoBeanDefinitions() throws Exception {
        Config config = new JavaConfig();

        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        assertEquals(0, beanDefinitions.length);
    }

    @Test
    public void testAddBeanDefinitionToEmptyConfig() throws Exception {
        Config config = new JavaConfig();
        BeanDefinition beanDefinition = new SimpleBeanDefinition(
                "bean", Tweet.class);

        config.addBeanDefinition(beanDefinition);
        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        assertEquals(1, beanDefinitions.length);
    }

    @Test
    public void testAddBeanDefinitionToNonEmptyConfig() throws Exception {
        Map<String, Bean> beanDescriptions = new HashMap<>();
        beanDescriptions.put("old bean", new Bean(Tweet.class, false));
        Config config = new JavaConfig(beanDescriptions);

        BeanDefinition beanDefinition = new SimpleBeanDefinition(
                "new bean", Tweet.class);

        config.addBeanDefinition(beanDefinition);
        BeanDefinition[] beanDefinitions = config.getBeanDefinitions();

        assertEquals(2, beanDefinitions.length);
    }
}
