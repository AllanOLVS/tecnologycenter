package com.ecommerce.tecnologycenter.controllers;

import com.ecommerce.tecnologycenter.dto.ProductDTO;
import com.ecommerce.tecnologycenter.entities.Product;
import com.ecommerce.tecnologycenter.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Esta anotation configura pra que quando a applicaçao rodar, o que tiver dentro desta classe vai responder pela web
@RestController
//Configurando a ROTA
//No VELUE é passado a rota que essa classe vai se comunicar
@RequestMapping(value = "/products")
public class ProductController {

    // Injetando produto do componente SERVICE aqui
    //O autowired é usado pra injetar
    @Autowired
    private ProductService service;

    //Respondendo a rota PRODUCTS pelo metodo GET, passando o id do produto que quer ver os dados
    @GetMapping(value = "/{id}")
    // O PathVariable configura o parametro de rota, casando a rota que foi passada no GetMApping com o do parametro do metodo
    public ProductDTO findById(@PathVariable Long id){
        //Chama o metodo da classe service, que vai no repository, tras o objeto com os dados
        // Aqui ele é passado pra uma variavel do tipo DTO e o objeto é retornado
        ProductDTO dto = service.findById(id);
        return dto;
    }

    @GetMapping
    // Pageable pra fazer uma busca paginada
    public Page<ProductDTO> findAll(Pageable pageable){
        // O metodo do CONTROLLER chama o metodo do service que busca no repositorio e ja tras a lista de objetos ProductDTO
        return service.findAll(pageable);
    }

    // Pois vai ser um metodo de post
    @PostMapping
    // RequestBudy = Faz com que o corpo da requisição que foi enviado pelo front, entre no parametro e instancia um DTO correspondente
    public ProductDTO insert(@RequestBody ProductDTO dto){
        return service.insert(dto);
    }

}
