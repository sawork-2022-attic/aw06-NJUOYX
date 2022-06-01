package com.micropos.amazondatabase.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader implements StepExecutionListener, ItemReader<JsonNode> {

    private ObjectMapper objectMapper;

    private final List<BufferedReader> readers;

    public JsonFileReader(String... filesName)  {
        readers = new ArrayList<>();
        for (String file : filesName) {
            try {
                readers.add(new BufferedReader(new FileReader(file)));
            }catch (FileNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
    }


    private String tryRead() throws IOException {
        for (BufferedReader reader : readers) {
            String line = reader.readLine();
            if(line!=null){
                return line;
            }
        }
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public JsonNode read() throws Exception {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();

        String line = tryRead();

        if (line != null)
            return objectMapper.readTree(line);
        else
            return null;
    }
}
