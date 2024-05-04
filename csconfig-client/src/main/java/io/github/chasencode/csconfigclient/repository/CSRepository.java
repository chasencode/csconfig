package io.github.chasencode.csconfigclient.repository;

import io.github.chasencode.csconfigclient.spring.CSConfigMeta;
import io.github.chasencode.csconfigclient.spring.CSConfigService;
import io.github.chasencode.csconfigclient.spring.CSConfigServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 远程配置存储接口服务
 * @Author: Chasen
 * @Create: 2024-05-04 19:10
 **/
public interface CSRepository {

    Map<String, String> LAST_CONFIG = new HashMap<>();
    Map<String, Long> LAST_TIMESTAMP = new HashMap<>();


    static CSRepository getDefault(ApplicationContext applicationContext, CSConfigMeta meta) {
        return new CSRepositoryImpl(applicationContext, meta);
    }

    void addListener(CSRepositoryChangeListener listener);

    Map<String, String> getConfig();

}
