package io.github.chasencode.csconfigclient.spring;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * @Program: csconfig
 * @Description: 配置源
 * @Author: Chasen
 * @Create: 2024-05-03 20:51
 **/
public class CSPropertySource extends EnumerablePropertySource<CSConfigService> {

    public CSPropertySource(String name,CSConfigService csConfigService) {
        super(name, csConfigService);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }


    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
