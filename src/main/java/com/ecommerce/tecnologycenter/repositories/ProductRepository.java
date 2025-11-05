package com.ecommerce.tecnologycenter.repositories;


import com.ecommerce.tecnologycenter.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Responsavel por operações no banco de dados referente a PRODUTO
// Dentro do parametro "<>" é colocado o TIPO DA ENTIDADE e o tipo do ID DA ENTIDADE
public interface ProductRepository extends JpaRepository<Product, Long> {



}
