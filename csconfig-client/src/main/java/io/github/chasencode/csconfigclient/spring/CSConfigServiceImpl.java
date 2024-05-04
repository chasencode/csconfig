package io.github.chasencode.csconfigclient.spring;

import io.github.chasencode.csconfigclient.repository.CSRepositoryChangeListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 配置服务实现类
 * @Author: Chasen
 * @Create: 2024-05-03 21:02
 **/
public class CSConfigServiceImpl implements CSConfigService {

    Map<String, String> config;

    ApplicationContext applicationContext;

    public CSConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(String[]::new);
    }

    @Override
    public String getProperty(String name) {
        return this.config.get(name);
    }

    @Override
    public void onChange(CSRepositoryChangeEvent event) {
        this.config = event.config();
        if (!config.isEmpty()) {
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
