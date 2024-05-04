package io.github.chasencode.csconfigclient.spring;

import io.github.chasencode.csconfigclient.repository.CSRepository;
import io.github.chasencode.csconfigclient.repository.CSRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

/**
 * @Program: csconfig
 * @Description: 配置服务
 * @Author: Chasen
 * @Create: 2024-05-03 20:57
 **/
public interface CSConfigService extends CSRepositoryChangeListener {

    static CSConfigService getDefault(ApplicationContext applicationContext, CSConfigMeta meta) {
        CSRepository repository = CSRepository.getDefault(applicationContext, meta);
        CSConfigServiceImpl configService = new CSConfigServiceImpl(applicationContext, repository.getConfig());
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
