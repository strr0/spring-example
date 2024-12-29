package com.strr.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.strr.mybatis.core.model.BaseEntity;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {
    private static final Long DEFAULT_USER_ID = 1L;

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE) {
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(parserMulti(mpBs.sql(), ms.getId()));
            // 设置参数映射
            List<ParameterMapping> parameterMappings = mpBs.parameterMappings();
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "createBy", Long.class).build());
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "createTime", Date.class).build());
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "updateBy", Long.class).build());
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), "updateTime", Date.class).build());
            mpBs.parameterMappings(parameterMappings);
        }
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        if (parameter instanceof Map map) {
            Date current = new Date();
            map.put("createBy", DEFAULT_USER_ID);
            map.put("createTime", current);
            map.put("updateBy", DEFAULT_USER_ID);
            map.put("updateTime", current);
        } else if (parameter instanceof BaseEntity baseEntity) {
            Date current = new Date();
            baseEntity.setCreateBy(DEFAULT_USER_ID);
            baseEntity.setCreateTime(current);
            baseEntity.setUpdateBy(DEFAULT_USER_ID);
            baseEntity.setUpdateTime(current);
        }
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        insert.addColumns(new Column("create_by"), new Column("create_time"),
                new Column("update_by"), new Column("update_time"));
        List<Expression> expressions = insert.getItemsList(ExpressionList.class).getExpressions();
        int size = expressions.size();
        expressions.add(new JdbcParameter(size + 1, false));
        expressions.add(new JdbcParameter(size + 2, false));
        expressions.add(new JdbcParameter(size + 3, false));
        expressions.add(new JdbcParameter(size + 4, false));
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        // TODO
    }
}
