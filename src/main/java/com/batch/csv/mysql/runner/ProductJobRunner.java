package com.batch.csv.mysql.runner;

import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ProductJobRunner{ // implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job jobA;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ProductJobRunner.class);
	
	 @Scheduled(cron="0 0/1 * * * ?") 
	//@Override
	    public void execute() throws Exception {
	            logger.info("Scheduler triggered. Launching job...");
	            jobLauncher.run(jobA, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
	     
	    }

		
}
