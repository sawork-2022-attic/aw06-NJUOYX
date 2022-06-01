package com.micropos.amazondatabase.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawProduct {

    private String main_cat;

    private String title;

    private String asin;

    private String price;

    private List<String> category;

    private List<String> imageURLHighRes;

    private List<String> imageURL;
}
