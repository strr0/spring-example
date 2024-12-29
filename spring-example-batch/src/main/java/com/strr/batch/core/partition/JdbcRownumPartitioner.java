package com.strr.batch.core.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Jdbc 分区器
 */
public class JdbcRownumPartitioner implements Partitioner {
    private final Long total;
    private final Long pageSize;

    public JdbcRownumPartitioner(Long total, Long pageSize) {
        this.total = total;
        this.pageSize = pageSize;
    }

    /**
     * 根据页大小分区
     */
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> contextMap = new HashMap<>();
        int idx = 0;
        for (int row = 0; row < total; row += pageSize) {
            ExecutionContext context = new ExecutionContext();
            context.put("startRow", row);
            context.put("pageSize", pageSize);
            contextMap.put(String.valueOf(idx++), context);
        }
        return contextMap;
    }
}
