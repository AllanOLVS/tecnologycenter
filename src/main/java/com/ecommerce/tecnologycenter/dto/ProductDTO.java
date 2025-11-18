package com.ecommerce.tecnologycenter.dto;

import com.ecommerce.tecnologycenter.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Responsavel por carregar os dados basicos do produto
// Não deve ter nada de JPA no DTO
public class ProductDTO {

    private Long id;
    // Validação referente ao tamanho
    @Size(min = 3, max = 80, message = "Nome precisa ter entre 3 e 80 caracteres")
    // Verifica se o campo não esta vazio, tambem não permite que seja colocado varios espaços em branco
    // Message = Aparece pro usuario quando o campo não esta atendendo aos requisitos
    @NotBlank(message = "Campo requerido")
    private String name;
    @Size(min = 10, message = "Descrição precisa ter no minimo 10 caracteres")
    @NotBlank(message = "Campo requerido")
    private String description;
    // Valida o campo preço pra que ele seja positivo
    @Positive(message = "O preço deve ter um valor positivo")
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
