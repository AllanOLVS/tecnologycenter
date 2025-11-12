package com.ecommerce.tecnologycenter.services;

import com.ecommerce.tecnologycenter.dto.ProductDTO;
import com.ecommerce.tecnologycenter.entities.Product;
import com.ecommerce.tecnologycenter.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // ReadOnly é pra não dar lock no banco de dados
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        /*
        -> FindById retorta um objeto do tipo OPTIONAL e por isso o resultado da busca é armazenado dentro de uma variavel deste tipo
        Optional<Product> result = repository.findById(id);

        -> esse .get é responsavel por pegar o Objeto de dentro do OPTIONAL
        -> Depois de pegar esse objeto, ele é atribuido a variavel do tipo product, que poder pegar os dados que queremos
        Product product = result.get();

        -> Instanciando e copiando os dados pro DTO
        ProductDTO dto = new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getImgUri());

        -> E retorna esse dto, que vai ter os mesmos dados do produto
        return dto;
         */

        //Com intuido de nn precisar passar todos os dados como parametro no construtor, fiz um novo construtor no ProductDTO
        //Que recebe um objeto de uma vez so pega e clona todos os dados do produto pro produtoDTO
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        // Pegando todos os registros referentes a produto e colocando em uma lista
        Page<Product> results = repository.findAll(pageable);
        //Page é um tipo de lista paginada, nn precisa mais colocar STREAM pq o PAGE ja é um STREAM
        return results.map(x -> new ProductDTO(x));
    }

    // Como vai inserir algo no banco, não é operação somente de leitura, ai é necessario tirar o READ-ONLY
    @Transactional
    public ProductDTO insert(ProductDTO dto){

        //Instanciamos o objeto e passamos os dados vindos da requisição pro objeto
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUri(dto.getImgUrl());

        //Salvando no banco
        entity = repository.save(entity);

        //Reconverto pra DTO pra poder retornar no metodo
        return new ProductDTO(entity);
    }


}
