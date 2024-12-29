package com.strr.batch.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBatchConfig extends DefaultBatchConfiguration {
    private final DataSource dataSource;

    public DataBatchConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 修改默认配置数据源
    @Override
    protected DataSource getDataSource() {
        if (dataSource instanceof DynamicRoutingDataSource ds) {
            return ds.getDataSource("postgres");
        }
        return dataSource;
    }
}
