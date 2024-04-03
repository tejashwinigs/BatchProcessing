package com.batch.csv.mysql.listener;

import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


public class ProductJobListener implements JobExecutionListener {

	private Logger log = org.slf4j.LoggerFactory.getLogger(ProductJobListener.class);

    public void beforeJob(JobExecution jobExecution) {
    	log.info("Before job starting {}",jobExecution.getStatus());
	}


	public void afterJob(JobExecution jobExecution) {
		log.info("After job finish {}",jobExecution.getStatus());
	}

}
