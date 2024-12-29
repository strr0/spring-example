package com.strr.shardingsphere.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableReferenceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.audit.ShardingAuditStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class ShardingSphereConfig {
    @Bean
    public DataSource shardingSphereDataSource(DataSourceProperties properties) throws SQLException {
        // 模式配置（单机模式）
        ModeConfiguration modeConfig = new ModeConfiguration("Standalone", new StandalonePersistRepositoryConfiguration("JDBC", new Properties()));
        // 真是数据源
        Map<String, DataSource> dataSourceMap = createDataSources(properties);
        // 规则配置
        Collection<RuleConfiguration> ruleConfigs = Collections.singleton(createShardingRuleConfiguration());
        // 属性配置
        Properties props = new Properties();
        return ShardingSphereDataSourceFactory.createDataSource("shardingsphere", modeConfig, dataSourceMap, ruleConfigs, props);
    }

    /**
     * 数据源
     */
    private static Map<String, DataSource> createDataSources(DataSourceProperties properties) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置数据源
        HikariDataSource dataSource = DataSourceBuilder.create(properties.getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
        dataSourceMap.put("ds", dataSource);
        return dataSourceMap;
    }

    /**
     * 分片规则
     */
    private static ShardingRuleConfiguration createShardingRuleConfiguration() {
        ShardingRuleConfiguration config = new ShardingRuleConfiguration();
        config.getTables().add(getDemoTableRuleConfiguration());
        config.getBindingTableGroups().add(new ShardingTableReferenceRuleConfiguration("foo", "my_demo"));
        config.setDefaultDatabaseShardingStrategy(new NoneShardingStrategyConfiguration());
        // 分片字段及分片算法
        config.setDefaultTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "my-table-sharding-algorithm"));
        Properties props = new Properties();
        // 指定算法类
        props.setProperty("algorithmClassName", "com.strr.shardingsphere.config.algorithm.MyTableShardingAlgorithm");
        props.setProperty("strategy", "standard");
        // CLASS_BASES 类型算法
        config.getShardingAlgorithms().put("my-table-sharding-algorithm", new AlgorithmConfiguration("CLASS_BASED", props));
        config.getKeyGenerators().put("snowflake", new AlgorithmConfiguration("SNOWFLAKE", new Properties()));
        config.getAuditors().put("sharding_key_required_auditor", new AlgorithmConfiguration("DML_SHARDING_CONDITIONS", new Properties()));
        return config;
    }

    private static ShardingTableRuleConfiguration getDemoTableRuleConfiguration() {
        ShardingTableRuleConfiguration config = new ShardingTableRuleConfiguration("my_demo", "ds.test_demo,ds.test_demo_copy1,ds.test_demo_copy2,ds.test_demo_copy3,ds.test_demo_copy4,ds.test_demo_copy5");
        config.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "snowflake"));
        config.setAuditStrategy(new ShardingAuditStrategyConfiguration(Collections.singleton("sharding_key_required_auditor"), true));
        return config;
    }
}
