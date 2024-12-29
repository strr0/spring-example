package com.strr.batch.core.processor;

import com.strr.batch.util.StringUtil;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 下划线转驼峰处理器
 */
public class CamelCaseProcessor implements ItemProcessor<Map<String, Object>, Map<String, Object>> {
    @Override
    public Map<String, Object> process(Map<String, Object> item) throws Exception {
        if (item == null || item.isEmpty()) {
            return null;
        }
        Map<String, Object> out = new HashMap<>();
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            out.put(StringUtil.toCamelCase(entry.getKey()), entry.getValue());
        }
        return out;
    }
}
