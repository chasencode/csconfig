package io.github.chasencode.csconfigserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Program: csconfig-server
 * @Description: MySQL 配置
 * @Author: Chasen
 * @Create: 2024-04-27 20:11
 **/
//@Configuration
//@MapperScan(basePackages = "io.github.chasencode.csconfigserver.mapper")
public class MySqlConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
