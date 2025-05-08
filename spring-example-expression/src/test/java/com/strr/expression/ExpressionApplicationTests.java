package com.strr.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ExpressionApplicationTests {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    void getValueTest() {
        EvaluationContext context = new StandardEvaluationContext();
        Map<String, Object> map = getMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        System.out.printf("a + b + c = %d", parser.parseExpression("#a+#b+#c").getValue(context, Integer.class));
    }

    @Test
    void getValueFromMapTest() {
        EvaluationContext context = new StandardEvaluationContext(getMap());
        System.out.printf("a + b + c = %d", parser.parseExpression("#root[a]+#root[b]+#root[c]").getValue(context, Integer.class));
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        return map;
    }
}
