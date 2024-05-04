package io.github.chasencode.csconfigclient.repository;

import com.alibaba.fastjson.TypeReference;
import io.github.chasencode.csconfigclient.spring.CSConfigMeta;
import io.github.chasencode.csconfigclient.utils.http.HttpUtil;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Program: csconfig
 * @Description: 默认远程配置存储
 * @Author: Chasen
 * @Create: 2024-05-04 19:12
 **/

@Data
public class CSRepositoryImpl implements CSRepository {

    CSConfigMeta meta;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    ApplicationContext applicationContext;

    List<CSRepositoryChangeListener> listeners = new ArrayList<>();



    public CSRepositoryImpl(ApplicationContext applicationContext, CSConfigMeta meta) {
        this.meta = meta;
        this.applicationContext = applicationContext;
        executor.scheduleAtFixedRate(this::heartbeat, 1_000, 5_000, TimeUnit.MILLISECONDS);
    }
    @Override
    public void addListener(CSRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        if (LAST_CONFIG.containsKey(meta.buildKey())) {
            return LAST_CONFIG;
        }
        return LAST_CONFIG;
    }


    private void heartbeat() {
        try {
            String url = meta.toHeartUrl();
            long current = Long.parseLong(HttpUtil.Default.get(url));
            Long last = LAST_TIMESTAMP.getOrDefault(meta.buildKey(), 0L);
            System.out.println(" ======>>>> heartbeat current = " + current + ", last = " + last);
            if (current > last) {
                System.out.println(" ====>>>> need update for new timestamp ...");
                LAST_TIMESTAMP.put(meta.buildKey(), current);
                fetchAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fetchAll() {
        String url = meta.toListUrl();
        List<Configs> configs = HttpUtil.httpGet(url, new TypeReference<List<Configs>>() {
        });
        System.out.println(" ======= configs ======");
        System.out.println(configs);
        System.out.println(" ======= configs ======");
        Map<String, String> config = new HashMap<>();
        configs.forEach(c -> {
            config.put(c.getPkey(), c.getPval());
        });

        listeners.forEach(listener -> {
            listener.onChange(new CSRepositoryChangeListener.CSRepositoryChangeEvent(meta, config));
        });
        LAST_CONFIG.clear();
        LAST_CONFIG.putAll(config);
    }
}
