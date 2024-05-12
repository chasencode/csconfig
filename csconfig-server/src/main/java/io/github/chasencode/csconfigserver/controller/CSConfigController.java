package io.github.chasencode.csconfigserver.controller;

import io.github.chasencode.csconfigserver.DistributedLocks;
import io.github.chasencode.csconfigserver.mapper.ConfigsMapper;
import io.github.chasencode.csconfigserver.model.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: csconfig-server
 * @Description: 服务
 * @Author: Chasen
 * @Create: 2024-04-27 20:19
 **/
@RestController
public class CSConfigController {

    @Autowired
    ConfigsMapper configsMapper;

    @Autowired
    DistributedLocks distributedLocks;


    Map<String, Long> VERSIONS = new HashMap<>();

    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return configsMapper.list(app, env, ns);
    }

    @PostMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
        params.forEach((k,v)->{
            Configs configs = new Configs(app, env, ns, k, v);
            insertOrUpdate(configs);
        });
        VERSIONS.put(app + env + ns, System.currentTimeMillis());
        return configsMapper.list(app, env, ns);
    }

    @GetMapping("/version")
    public long
    version(@RequestParam("app") String app,
            @RequestParam("env") String env,
            @RequestParam("ns") String ns)
    {
        return VERSIONS.getOrDefault(app + env + ns, -1L);
    }


    @GetMapping("/status")
    public boolean status()
    {
        return distributedLocks.getLocked().get();
    }

    private void insertOrUpdate(Configs config) {
        Configs conf = configsMapper.select(config.getApp(), config.getEnv(), config.getNs(), config.getPkey());
        if (conf == null) {
            configsMapper.insert(config);
        } else {
            configsMapper.update(config);
        }
    }
}
