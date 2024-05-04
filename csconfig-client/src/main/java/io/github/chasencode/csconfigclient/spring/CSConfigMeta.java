package io.github.chasencode.csconfigclient.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Program: csconfig
 * @Description: 配置数据
 * @Author: Chasen
 * @Create: 2024-05-04 19:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSConfigMeta {

    public String app;

    public String ns;


    public String env;

    public String configServer;

    public String buildKey() {
        return app + "_" + ns + "_" + env;
    }

    public String toListUrl() {
        return configServer + "/list?app=" + app + "&ns=" + ns + "&env=" + env;
    }

    public String toHeartUrl() {
        return configServer + "/version?app=" + app + "&ns=" + ns + "&env=" + env;
    }

}
