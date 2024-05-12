package io.github.chasencode.csconfigserver;

import io.github.chasencode.csconfigserver.mapper.LocksMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Program: csconfig
 * @Description: distributed locks
 * @Author: Chasen
 * @Create: 2024-05-12 21:00
 **/
@Component
@Slf4j
@Data
public class DistributedLocks {

    @Autowired
    DataSource dataSource;

    Connection connection;

    private AtomicBoolean locked = new AtomicBoolean(false);

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        executor.scheduleWithFixedDelay(this::tryLock, 1000, 5000, TimeUnit.MILLISECONDS);
    }
    public boolean lock() {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // innodb_lock_wait_timeout 要与executor 事件要保持一致
            connection.createStatement().execute("set innodb_lock_wait_timeout=5");
            connection.createStatement().execute("SELECT app FROM locks WHERE id = 1 FOR UPDATE");
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

        if (locked.get()) {
            log.info("===>>> reenter this dist lock.");
        } else {
            log.info("===>>> get a dist lock.");
        }
        return true;
    }

    private void tryLock() {
        try {
            lock();
            locked.set(true);
        } catch (Exception e) {
            log.info(" ===>>> try lock failed", e);
            locked.set(false);

        }
    }

    @PreDestroy
    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            log.error(" ===>>> close error", e);
        }
    }
}