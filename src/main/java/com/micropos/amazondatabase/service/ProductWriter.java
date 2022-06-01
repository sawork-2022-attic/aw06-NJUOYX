package com.micropos.amazondatabase.service;

import com.micropos.amazondatabase.model.Product;
import com.micropos.amazondatabase.model.RawProduct;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ProductWriter implements ItemWriter<RawProduct>, StepExecutionListener {

    @Autowired
    private DataBaseService dataBaseService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void write(List<? extends RawProduct> list) throws Exception {
        for (RawProduct rawProduct : list) {
            Product product = Product.parseRawProduct(rawProduct);
            if(product != null) {
                dataBaseService.add(product);
            }
        }
        dataBaseService.flush();
    }
}
