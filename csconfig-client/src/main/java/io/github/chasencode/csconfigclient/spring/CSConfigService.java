package io.github.chasencode.csconfigclient.spring;

/**
 * @Program: csconfig
 * @Description: 配置服务
 * @Author: Chasen
 * @Create: 2024-05-03 20:57
 **/
public interface CSConfigService {

    String[] getPropertyNames();

    String getProperty(String name);
}
