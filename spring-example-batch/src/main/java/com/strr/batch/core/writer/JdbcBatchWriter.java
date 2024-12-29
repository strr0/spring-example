package com.strr.batch.core.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Jdbc 写出器
 */
public class JdbcBatchWriter implements ItemWriter<Map<String, Object>> {
    // 数据源
    private DataSource dataSource;
    // 执行脚本
    private String script;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public void write(Chunk<? extends Map<String, Object>> chunk) throws Exception {
        if (dataSource == null || script == null) {
            return;
        }
        List<? extends Map<String, Object>> items = chunk.getItems();
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        for (Map<String, Object> item : items) {
            jdbcTemplate.update(script, item);
        }
    }
}
