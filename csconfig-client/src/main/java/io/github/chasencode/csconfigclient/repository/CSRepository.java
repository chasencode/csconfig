package io.github.chasencode.csconfigclient.repository;

import io.github.chasencode.csconfigclient.spring.CSConfigMeta;
import io.github.chasencode.csconfigclient.spring.CSConfigService;
import io.github.chasencode.csconfigclient.spring.CSConfigServiceImpl;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 远程配置存储接口服务
 * @Author: Chasen
 * @Create: 2024-05-04 19:10
 **/
public interface CSRepository {


    static CSRepository getDefault(CSConfigMeta meta) {
        return new CSRepositoryImpl(meta);
    }

    Map<String, String> getConfig();
}
