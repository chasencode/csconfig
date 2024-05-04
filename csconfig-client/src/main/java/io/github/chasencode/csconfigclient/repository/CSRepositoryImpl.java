package io.github.chasencode.csconfigclient.repository;

import com.alibaba.fastjson.TypeReference;
import io.github.chasencode.csconfigclient.spring.CSConfigMeta;
import io.github.chasencode.csconfigclient.utils.http.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 默认远程配置存储
 * @Author: Chasen
 * @Create: 2024-05-04 19:12
 **/

@Data
@AllArgsConstructor
public class CSRepositoryImpl implements CSRepository {

    CSConfigMeta meta;
    @Override
    public Map<String, String> getConfig() {

        String listPath = meta.getConfigServer() + "/list?app="
                + meta.getApp() + "&env=" + meta.getEnv() + "&ns=" + meta.getNs();
        List<Configs> configs = HttpUtil.httpGet(listPath, new TypeReference<List<Configs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(config -> {
            resultMap.put(config.getPkey(), config.getPval());
        });
        return resultMap;
    }
}
