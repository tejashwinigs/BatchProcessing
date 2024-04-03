package com.batch.csv.mysql.config;



import javax.print.event.PrintJobListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.batch.csv.mysql.listener.ProductJobListener;
import com.batch.csv.mysql.model.Product;
import com.batch.csv.mysql.processor.ProductProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

//	//1. Reader
//	@Bean
//	public ItemReader<Product> reader(){
//		return new FlatFileItemReader<>() {{
//			setResource(new ClassPathResource("product.csv"));
//			setLineMapper(new DefaultLineMapper<>() {{
//				setLineTokenizer(new DelimitedLineTokenizer() {{
//					setNames("prodId","prodCode","prodCost","prodDisc","prodGst");
//				}});
//				
//				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//					setTargetType(Product.class);
//					
//				}});
//			}});
//			
//		}};
//	}
//	
//	//2. Processor
//	public ItemProcessor<Product,Product> processor(){
//		return new ProductProcessor();
//		
//	}
//	
//	//3. Writer
//	public ItemWriter<Product> writer(){
//		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
//		writer.setSql("INSERT INTO PRODTAB(PID,PCODE,PCOST,PDISC,PGST) VALUES (:prodId,:prodCode,:prodCost,:prodDisc,:prodGst)");
//		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
//	    writer.setDataSource(datasource());
//		return writer;
//		
//	}
//	
//	@Bean
//	public DataSource datasource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		ds.setUrl("jdbc:mysql://localhost:3306/boot9");
//		ds.setUsername("root");
//		ds.setPassword("root");
//		return ds;
//	}
//
//	//4.Listener
//	@Bean
//	public JobExecutionListener listener() {
//		return new ProductJobListener();
//		
//	}
//	
//	// 5.StepBuilderFactory
//	@Autowired
//	private StepBuilderFactory sf;
//	
//	//6. Step obj
//	@Bean
//	public Step stepA() {
//		return sf.get("stepA")
//				.<Product,Product>chunk(3)
//				.reader(reader())
//				.processor(processor())
//				.writer(writer())
//				.build();
//				
//		
//	}
//	
//	//7. JobBuilderFactory
//	@Autowired
//	private JobBuilderFactory jf;
//	
//	//8. Job obj
//	@Bean
//	public Job jobA() {
//		return jf.get("jobA")
//				.listener(listener())
//				.incrementer(new RunIdIncrementer())
//				.start(stepA())
//				.build()
//				;
//		
//	}
//	
	
	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

	JobRepository jobRepo;

	// Step : 1 - Reader
	/*
	 * @Bean public ItemReader<Product> reader1(){ return new
	 * FlatFileItemReader<Product>() {{ setResource(new
	 * ClassPathResource("product.csv")); setLineMapper(new DefaultLineMapper<>() {{
	 * setLineTokenizer(new DelimitedLineTokenizer() {{ setNames("pId", "pName",
	 * "pCost"); }}); setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
	 * setTargetType(Product.class); }}); }}); }}; }
	 */
	
	
	@Bean
	public FlatFileItemReader<Product> reader() {
		log.error("READER EXECUTED");
		return new FlatFileItemReaderBuilder<Product>().name("productItemReader")
				.resource(new ClassPathResource("product.csv"))
				.delimited().names("prodId","prodCode","prodCost")
				.targetType(Product.class)
				.build();
	}
	

	//Step : 2 -  Processor
	/*
	 * @Bean public ItemProcessor<Product, Product> processor() { return new
	 * ProductProcessor(); }
	 */
	
	@Bean
	public ProductProcessor processor() {
		log.error("Processor EXECUTED");
		return new ProductProcessor();
	}
	
	

	//Step : 3 -  Writer
	/*
	 * @Bean public ItemWriter<Product> writer() { JdbcBatchItemWriter<Product>
	 * writer = new JdbcBatchItemWriter<>();
	 * writer.setSql("Insert INTO prod_table (PID,PNAME,PCOST) VALUES" +
	 * " (:pId , :pName , : pCost)"); writer.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<Product>());
	 * writer.setDataSource(dataSource()); return writer; }
	 */
	
	@Bean
	public JdbcBatchItemWriter<Product> writer() {
		log.error("WRITER EXECUTED");
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
		writer.setSql("INSERT INTO PRODTAB(PID,PCODE,PCOST,PDISC,PGST) VALUES (:prodId,:prodCode,:prodCost,:prodDisc,:prodGst)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
		writer.setDataSource(dataSource());
		return writer;
	}

	//Step : 4 -  Listner
	/*
	 * @Bean public JobExecutionListener listner() { return new ProductListener(); }
	 */
	
	@Bean
	public ProductJobListener listner() {
		log.error("LISTNER EXECUTED");
		return new ProductJobListener();
	}
	
	//Step : 5 - StepBuilder
	/*
	 * @Autowired private StepBuilder sf;
	 * 
	 * // Step object
	 * 
	 * @Bean public Step stepA() { return new StepBuilder("stepA",
	 * jobRepo).<Product, Product>chunk(3).reader(reader()).processor(processor())
	 * .writer(writer()).build(); }
	 */
	@Bean
	public Step stepA(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
			FlatFileItemReader<Product> reader, ProductProcessor processor, JdbcBatchItemWriter<Product> writer) {
		log.error("STEP EXECUTED");
		return new StepBuilder("stepA", jobRepository)
				.<Product, Product>chunk(3, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer).build();
	}
	

	// 	//Step : 6 - JobBuilder
	/*
	 * @Autowired private JobBuilderFactory jf;
	 * 
	 * // Step object
	 * 
	 * @Bean public Job jobA() { return new
	 * JobBuilder("jobA").listener(listner()).incrementer(new
	 * RunIdIncrementer()).start(stepA()).build(); }
	 */

	@Bean
	public Job jobA(JobRepository jobRepository, Step stepA, ProductJobListener listener) {
		log.error("JOB EXECUTED");
		return new JobBuilder("jobA", jobRepository)
				.listener(listener)
				.start(stepA)
				.build();
	}

	//Step : 7 -  DataSource
	@Bean
	public DataSource dataSource() {
		log.error("DataSource EXECUTED");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/boot9");
		ds.setUsername("root");
		ds.setPassword("root");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return ds;
	}
}
