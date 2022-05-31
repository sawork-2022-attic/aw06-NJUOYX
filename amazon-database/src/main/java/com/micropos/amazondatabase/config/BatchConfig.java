package com.micropos.amazondatabase.config;

import com.micropos.amazondatabase.model.RawProduct;
import com.micropos.amazondatabase.service.JsonFileReader;
import com.micropos.amazondatabase.service.ProductProcessor;
import com.micropos.amazondatabase.service.ProductWriter;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemReader<JsonNode> itemReader() {
        String path = "E:\\2022\\aw06-NJUOYX_bak\\";
        return new JsonFileReader(path+"meta_Books.json", path+"meta_Video_Games.json");
    }

    @Bean
    public ItemProcessor<JsonNode, RawProduct> itemProcessor() {
        return new ProductProcessor();
    }

    @Bean
    public ItemWriter<RawProduct> itemWriter() {
        return new ProductWriter();
    }

    @Bean
    protected Step processProducts(ItemReader<JsonNode> reader, ItemProcessor<JsonNode, RawProduct> processor, ItemWriter<RawProduct> writer) {
        return stepBuilderFactory.get("processProducts").<JsonNode, RawProduct>chunk(20)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job chunksJob() {
        return jobBuilderFactory
                .get("chunksJob")
                .start(processProducts(itemReader(), itemProcessor(), itemWriter()))
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(60);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(2000);
        return executor;
    }

}
