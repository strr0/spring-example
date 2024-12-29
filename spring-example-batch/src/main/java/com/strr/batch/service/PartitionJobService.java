package com.strr.batch.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.strr.batch.core.partition.JdbcRownumPartitioner;
import com.strr.batch.core.processor.CamelCaseProcessor;
import com.strr.batch.core.reader.JdbcPartitionReader;
import com.strr.batch.core.writer.JdbcBatchWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

/**
 * 分区任务
 */
@Service
public class PartitionJobService {
    private static final String JOB_NAME = "partitionJob";
    private static final String PART_STEP_NAME = "partitionStep";
    private static final String PART_NAME = "partition";
    private static final String STEP_NAME = "step";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DynamicRoutingDataSource dataSource;

    public PartitionJobService(JobRepository jobRepository, PlatformTransactionManager transactionManager, DynamicRoutingDataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    /**
     * 构建分区 Job
     */
    public Job buildPartJob(String source, String sourceSql, String target, String targetSql) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(createPartStep(source, sourceSql, target, targetSql))
                .build();
    }

    /**
     * 构建分区 Step
     */
    private Step createPartStep(String source, String sourceSql, String target, String targetSql) {
        // 读取器
        JdbcPartitionReader reader = new JdbcPartitionReader();
        reader.setDataSource(dataSource.getDataSource(source));
        reader.setScript(sourceSql);
        // 处理器
        CamelCaseProcessor processor = new CamelCaseProcessor();
        // 写出器
        JdbcBatchWriter writer = new JdbcBatchWriter();
        writer.setDataSource(dataSource.getDataSource(target));
        writer.setScript(targetSql);
        // 分区器
        JdbcRownumPartitioner partitioner = new JdbcRownumPartitioner(reader.getCount(), 10L);
        return new StepBuilder(PART_STEP_NAME, jobRepository)
                .allowStartIfComplete(true)
                .partitioner(PART_NAME, partitioner)
                .step(createStep(reader, processor, writer))
                .build();
    }

    /**
     * 构建 Step
     */
    private Step createStep(ItemReader<Map<String, Object>> reader, ItemProcessor<Map<String, Object>, Map<String, Object>> processor, ItemWriter<Map<String, Object>> writer) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .allowStartIfComplete(true)
                .<Map<String, Object>, Map<String, Object>>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
