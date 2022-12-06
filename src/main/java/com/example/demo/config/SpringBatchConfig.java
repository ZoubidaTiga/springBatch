package com.example.demo.config;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfiguration {

    @Autowired
    TransactionRepository transactionRepository;

@Bean
public FlatFileItemReader <TransactionDto> reader(){
    FlatFileItemReader <TransactionDto> itemReader=new FlatFileItemReader<>();
    itemReader.setResource(new ClassPathResource("transactions.csv"));
    itemReader.setLinesToSkip(1);
    itemReader.setName("csvReader");
    itemReader.setLineMapper(lineMapper());
    return itemReader;
}
    private LineMapper<TransactionDto> lineMapper() {
        DefaultLineMapper<TransactionDto>lineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);

        lineTokenizer.setNames("idTransaction", "idAccount", "montant", "dateTransaction" );
        BeanWrapperFieldSetMapper<TransactionDto>fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransactionDto.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public TransactionDtoProcessor processor(){
    return new TransactionDtoProcessor();
    }

public RepositoryItemWriter<Transaction> writer(){

    RepositoryItemWriter<Transaction>writer=new RepositoryItemWriter<>();
    writer.setRepository(transactionRepository);
    writer.setMethodName("save");
    return writer;
}
@Bean
public Step step(){
   return new StepBuilder("csv-step",jobRepository())
            .<TransactionDto,Transaction>chunk(10,getTransactionManager())
            .writer(writer())
            .reader(reader())
            .processor(processor()).build();
}

    @Bean
    public Job job(Step step) {
        return new JobBuilder("importTransaction", jobRepository())
                .start(step)
                .build();
    }

 }

