package com.ecommerce.tecnologycenter.services;

import com.ecommerce.tecnologycenter.dto.ProductDTO;
import com.ecommerce.tecnologycenter.entities.Product;
import com.ecommerce.tecnologycenter.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // ReadOnly é pra não dar lock no banco de dados
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        //FindById retorta um objeto do tipo OPTIONAL e por isso o resultado da busca é armazenado dentro de uma variavel
        // deste tipo
        Optional<Product> result = repository.findById(id);

        //esse .get é responsavel por pegar o Objeto de dentro do OPTIONAL
        // Depois de pegar esse objeto, ele é atribuido a variavel do tipo product, que poder pegar os dados que queremos
        Product product = result.get();

        //Instanciando e copiando os dados pro DTO
        ProductDTO dto = new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getImgUri());

        // E retorna esse dto, que vai ter os mesmos dados do produto
        return dto;

        /*
        -> JEITO RESUMIDO

        Product product = repository.findById(id).get();
        return new ProductDTO(product);

        */
    }

    /*
    Com intuido de nn precisar passar todos os dados como parametro no construtor, fiz um novo construtor no ProductDTO
    Que recebe um objeto de uma vez so pega e clona todos os dados do produto pro produtoDTO

    public ProductDTO findById(Long id){

        Optional<Product> result = repository.findById(id);

        Product product = result.get();

        ProductDTO dto = new ProductDTO(product);
        return dto;
    }
    */

}
