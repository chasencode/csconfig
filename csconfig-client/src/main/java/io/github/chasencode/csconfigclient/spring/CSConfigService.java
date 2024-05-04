package io.github.chasencode.csconfigclient.spring;

import io.github.chasencode.csconfigclient.repository.CSRepository;

/**
 * @Program: csconfig
 * @Description: 配置服务
 * @Author: Chasen
 * @Create: 2024-05-03 20:57
 **/
public interface CSConfigService {

    static CSConfigService getDefault(CSConfigMeta meta) {
        CSRepository repository = CSRepository.getDefault(meta);
        return new CSConfigServiceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
