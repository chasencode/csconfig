package io.github.chasencode.csconfigclient.spring;

import io.github.chasencode.csconfigclient.repository.CSRepositoryChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Program: csconfig
 * @Description: 配置服务实现类
 * @Author: Chasen
 * @Create: 2024-05-03 21:02
 **/
@Slf4j
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
        Set<String> keys  = calcChangeKeys(this.config, event.config());
        if (keys.isEmpty()) {
            log.info("[CSConfig] calcChangedKeys return empty, ignore update.");
            return;
        }
        this.config = event.config();
        if (!config.isEmpty()) {
            log.info("[CSConfig] fire an EnvironmentChangeEvent with keys: {}", keys);
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
        }
    }

    private Set<String> calcChangeKeys(Map<String, String> oldConfigs, Map<String,String> newConfigs) {
        if (oldConfigs.isEmpty()) {
            return newConfigs.keySet();
        }
        if (newConfigs.isEmpty()) {
            return oldConfigs.keySet();
        }
        Set<String> news = newConfigs.keySet().stream()
                .filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key))).collect(Collectors.toSet());
        oldConfigs.keySet().stream()
                .filter(key -> !newConfigs.containsKey(key))
                .forEach(news::add);
        return news;
    }
}
