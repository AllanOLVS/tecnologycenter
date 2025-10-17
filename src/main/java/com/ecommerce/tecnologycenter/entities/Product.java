package com.ecommerce.tecnologycenter.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //Faz com que na hora de mapear pro banco relacional ela vai ser do tipo TEXT e não do tipo VARCHAR
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imgUri;

    //Indica que é uma ligação de MUITOS PRA MUITOS
    @ManyToMany
    //Tabela de JUNÇÃO, que vai ser criada e configurada
    //O primeiro name é: Nome da tabela que vai ser criada, que será a referencia pra as duas chaves estrangeiras
    @JoinTable(name = "tb_product_category",
            //Aqui serão passadas as colunas que vão se juntar
            //No JOINCOLUM é passada o nome desta coluna pra pegar o id dela, que é o product ID
            joinColumns = @JoinColumn(name = "product_id"),
            //No INVERSEJOINCOLUM é passada o nome da coluna da outra tabela
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Product(){
    }

    public Product(Long id, String name, String description, Double price, String imgUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUri = imgUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public Set<Category> getCategories() {
        return categories;
    }

}
