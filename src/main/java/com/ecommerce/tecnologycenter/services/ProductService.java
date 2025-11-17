package com.ecommerce.tecnologycenter.services;

import com.ecommerce.tecnologycenter.dto.ProductDTO;
import com.ecommerce.tecnologycenter.entities.Product;
import com.ecommerce.tecnologycenter.repositories.ProductRepository;
import com.ecommerce.tecnologycenter.services.exceptions.DatabaseException;
import com.ecommerce.tecnologycenter.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        //OrElseThrow = Ele tenta acessar o objeto que esta sendo passado, caso não encontre, lança a exceção
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        // Pegando todos os registros referentes a produto e colocando em uma lista
        Page<Product> results = repository.findAll(pageable);
        //Page é um tipo de lista paginada, nn precisa mais colocar STREAM pq o PAGE ja é um STREAM
        return results.map(x -> new ProductDTO(x));
    }

    // Metodo que Copia os dados do DTO pra ENTIDADE
    private void copyDtoToEntity(Product entity, ProductDTO dto){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUri(dto.getImgUrl());
    }

    // Como vai inserir algo no banco, não é operação somente de leitura, ai é necessario tirar o READ-ONLY
    @Transactional
    public ProductDTO insert(ProductDTO dto){
        //Instanciamos o objeto e passamos os dados vindos da requisição pro objeto
        Product entity = new Product();
        // Atualizamos chamando o metodo que copia dos dados do DTO pra ENTIDADE
        copyDtoToEntity(entity, dto);

        //Salvando no banco
        entity = repository.save(entity);

        //Reconverto pra DTO pra poder retornar no metodo
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        // Try pra tratar exessão na hora de atualizar dados de um produto
        // Ja que pra atualizar tem que buscar o produto, quando ele não for encontrado ja que não existe, da o erro de
        // RECURSO NÃO ENCONTRADO
        // Como o HANDLER esta pronto pra essa exceprion, ela ja utiliza aquele metodo
        try {

            // Objeto esta monitorado pela JPA
            // Instanciando um produto com a referencia do ID
            Product entity = repository.getReferenceById(id);
            // Pegamos, pela ref do ID, os dados da entidade que queremos atualizar
            // Atualizamos chamando o metodo que copia dos dados do DTO pra ENTIDADE
            copyDtoToEntity(entity, dto);

            // Salvando no repositorio a entidade com os novos dados que atualizamos
            entity = repository.save(entity);

            // Instanciamos e Retornamos um ProductDTO passando os dados da entidade que atualizamos
            return new ProductDTO(entity);

        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    // Propagation = Este parametro faz com que a TRANSAÇÃO seja executada somente se ESTE metodo estiver no contexto
    //de outra transação
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        try {

            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Resource not found");
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Fail of referential integrity");
        }
    }


}
