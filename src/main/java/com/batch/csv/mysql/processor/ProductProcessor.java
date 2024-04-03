package com.batch.csv.mysql.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.csv.mysql.model.Product;


public class ProductProcessor implements ItemProcessor<Product, Product> {

	

	@Override
	public Product process(Product item) throws Exception {
		var cost = item.getProdCost();
		item.setProdDisc(cost*6/100.0);
		item.setProdGst(cost*8/100.0);
		return item;
	}

}
