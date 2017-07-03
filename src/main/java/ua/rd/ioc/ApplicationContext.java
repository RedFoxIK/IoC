package ua.rd.ioc;

import java.util.Arrays;

public class ApplicationContext implements Context {
    private Config config;

    public ApplicationContext() {

    }

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public String[] getBeanDefinitionsNames() {
        if ( config == null ) {
            return new String[]{};
        }

        return Arrays.stream(config.getBeanDefinitions())
                .map(bd -> bd.getBeanName())
                .toArray(String[]::new);
    }
}
