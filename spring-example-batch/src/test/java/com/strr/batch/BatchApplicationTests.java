package com.strr.batch;

import com.strr.batch.service.PagingJobService;
import com.strr.batch.service.PartitionJobService;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BatchApplicationTests {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    PagingJobService pagingJobService;
    @Autowired
    PartitionJobService partitionJobService;

    @Test
    void testPaging() throws Exception {
        Job job = pagingJobService.buildJob("test_key, test_value", "test_key", "test_demo", chunk -> {
            chunk.getItems().forEach(System.out::println);
        });
        jobLauncher.run(job, new JobParameters());
    }

    @Test
    void testPartition() throws Exception {
        Job job = partitionJobService.buildPartJob("postgres", "select test_key, test_value from test_demo",
                "mysql", "insert into test_demo(test_key, test_value) values(:testKey, :testValue)");
        jobLauncher.run(job, new JobParameters());
    }
}
