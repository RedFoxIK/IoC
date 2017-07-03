package ua.rd.ioc;

import org.junit.jupiter.api.Test;
import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextTest {

    @Test
    public void testInitContextWithoutJavaConfig() {
        Context context = new ApplicationContext();
        assertNotNull(context);
    }

    @Test
    public void testInitContextWithEmptyJavaConfig() {
        Config javaConfig = new JavaConfig();
        Context context = new ApplicationContext(javaConfig);
        assertNotNull(context);
    }

    @Test
    public void testBeanDefinitionWithEmptyJavaConfig() {
        Config javaConfig = new JavaConfig();
        BeanDefinition[] beanDefinitions = javaConfig.getBeanDefinitions();
        assertEquals(beanDefinitions.length, 0);

    }

    @Test
    public void tesBeanDefinitionsWithJavaConfig() {
        Map<String, Class<?>> beanDescriptions = createTestBeanDescriptions();
        Config javaConfig = new JavaConfig(beanDescriptions);
        BeanDefinition[] beanDefinitions = javaConfig.getBeanDefinitions();
        assertEquals(beanDefinitions.length, 1);
    }

    @Test
    public void testBeanDefinitionWithoutJavaConfig() {
        Context context = new ApplicationContext();
        String[] beanDefinitions = context.getBeanDefinitionsNames();
        assertEquals(beanDefinitions.length, 0);
    }


    @Test
    public void testBeanDefinitionNamesWithJavaConfig() {
        Map<String, Class<?>> beanDescriptions = createTestBeanDescriptions();
        Context context = new ApplicationContext(new JavaConfig(beanDescriptions));
        String[] beanDefinitionsNames = context.getBeanDefinitionsNames();
        assertTrue(Arrays.asList(beanDefinitionsNames).contains("tweet"));
    }

    @Test
    public void testGetBean() {
        Map<String, Class<?>> beanDescriptions = createTestBeanDescriptions();
        Context context = new ApplicationContext(new JavaConfig(beanDescriptions));
        assertNotNull(context.getBean("tweet"));
    }


    private Map<String, Class<?>> createTestBeanDescriptions() {
        return new HashMap<String, Class<?>>() {{
            put("tweet", Tweet.class);
        }};
    }
}
