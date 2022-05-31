package com.micropos.amazondatabase.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JsonFileReader implements StepExecutionListener, ItemReader<JsonNode> {

    private BufferedReader reader;

    private ObjectMapper objectMapper;

    private Queue<String> files;


    public JsonFileReader(String... filesName) {
        for (String file : filesName) {
            addFile(file);
        }
    }

    private void addFile(String file){
        if(this.files == null) {
            this.files = new LinkedList<>();
        }
        if (file.matches("^file:(.*)"))
            file = file.substring(file.indexOf(":") + 1);
        this.files.add(file);
    }

    private BufferedReader initReader() throws FileNotFoundException {
        String fileName = files.poll();
        if(fileName == null){
            return null;
        }else {
            File file = new File(fileName);
            return new BufferedReader(new FileReader(file));
        }
    }

    private String tryRead() throws IOException {
        if(reader == null){
            reader = initReader();
            if(reader == null){
                return null;
            }
        }
        String line = reader.readLine();
        if(line == null){
            reader = null;
            return tryRead();
        }
        return line;
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
