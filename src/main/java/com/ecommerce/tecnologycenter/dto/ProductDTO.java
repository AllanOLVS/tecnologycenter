package com.ecommerce.tecnologycenter.dto;

import com.ecommerce.tecnologycenter.entities.Product;

// Responsavel por carregar os dados basicos do produto
// Não deve ter nada de JPA no DTO
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    public ProductDTO(){
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    //Construtor praticamente identico ao de cima, mas com intuito de facilitar a vida do SERVICE
    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUri();
    }

    //So existe a necessidade de existirem os GETTERS pois não tem sentido alterar os dados aqui
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
