package com.micropos.amazondatabase.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "main_cat", nullable = false, length = 512)
    private String mainCat;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "asin", nullable = false, length = 16)
    private String asin;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "image_url", nullable = false, length = 1024)
    private String imageUrl;

    @Column(name = "image_url_high_res", nullable = false, length = 1024)
    private String imageUrlHighRes;

    public static Product parseRawProduct(RawProduct rawProduct){
        Optional<String> image_url_high_res = rawProduct.getImageURLHighRes().stream().findFirst();
        Optional<String> image_url = rawProduct.getImageURL().stream().findFirst();
        if(image_url_high_res.isEmpty() || image_url.isEmpty()) {
            return null;
        }
        Product product = new Product();
        if(!rawProduct.getAsin().matches("^[A-Za-z0-9]+$")){
            return null;
        }
        product.setAsin(rawProduct.getAsin());
        if(!rawProduct.getMain_cat().matches("^[A-Za-z]+\\S[A-Za-z]+$")){
            return null;
        }
        product.setMainCat(rawProduct.getMain_cat());
        if(rawProduct.getTitle().length()>128){
            return null;
        }
        product.setTitle(rawProduct.getTitle());
        String sPrice = rawProduct.getPrice();
        if(!sPrice.matches("^\\$\\d+(\\.\\d+)?$")){
            return null;
        }
        product.setPrice(Double.parseDouble(sPrice.substring(1)));
        product.setImageUrl(image_url.get());
        product.setImageUrlHighRes(image_url_high_res.get());
        return product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainCat() {
        return mainCat;
    }

    public void setMainCat(String mainCat) {
        this.mainCat = mainCat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlHighRes() {
        return imageUrlHighRes;
    }

    public void setImageUrlHighRes(String imageUrlHighRes) {
        this.imageUrlHighRes = imageUrlHighRes;
    }

}