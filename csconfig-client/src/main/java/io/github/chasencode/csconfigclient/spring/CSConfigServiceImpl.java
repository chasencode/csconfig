package io.github.chasencode.csconfigclient.spring;

import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 配置服务实现类
 * @Author: Chasen
 * @Create: 2024-05-03 21:02
 **/
public class CSConfigServiceImpl implements CSConfigService {

    Map<String, String> config;

    public CSConfigServiceImpl(Map<String, String> config) {
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
}
