package com.strr.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 分页任务
 */
@Service
public class PagingJobService {
    private static final String JOB_NAME = "pageJob";
    private static final String STEP_NAME = "pageStep";
    private static final String READER_NAME = "pageReader";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public PagingJobService(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    /**
     * 构建 Job
     */
    public Job buildJob(String select, String id, String from, Consumer<Chunk<? extends Map<String, Object>>> writer) throws Exception {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(buildStep(select, id, from, writer))
                .build();
    }

    /**
     * 构建 Step
     */
    private Step buildStep(String select, String id, String from, Consumer<Chunk<? extends Map<String, Object>>> writer) throws Exception {
        // 分页 provider
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause(select);
        provider.setFromClause(from);
        provider.setSortKeys(new HashMap<>(){{put(id, Order.ASCENDING);}});
        // 分页读取器
        JdbcPagingItemReader<Map<String, Object>> reader = new JdbcPagingItemReader<>();
        reader.setName(READER_NAME);
        reader.setDataSource(dataSource);
        reader.setPageSize(10);
        reader.setQueryProvider(provider);
        reader.setRowMapper(new ColumnMapRowMapper());
        reader.afterPropertiesSet();
        // 构建 step
        return new StepBuilder(STEP_NAME, jobRepository)
                .allowStartIfComplete(true)
                .<Map<String, Object>, Map<String, Object>>chunk(100, transactionManager)
                .reader(reader)
                .writer(writer::accept)
                .build();
    }
}
