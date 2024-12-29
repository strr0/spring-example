package com.strr.shardingsphere.config.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;

/**
 * 表分片规则
 */
public class MyTableShardingAlgorithm implements StandardShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        Integer value = preciseShardingValue.getValue();
        if (value > 50) {
            return "test_demo_copy5";
        }
        if (value > 40) {
            return "test_demo_copy4";
        }
        if (value > 30) {
            return "test_demo_copy3";
        }
        if (value > 20) {
            return "test_demo_copy2";
        }
        if (value > 10) {
            return "test_demo_copy1";
        }
        return "test_demo";
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        return null;
    }
}
