package com.ecommerce.tecnologycenter.controllers;

import com.ecommerce.tecnologycenter.dto.ProductDTO;
import com.ecommerce.tecnologycenter.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        //Chama o metodo da classe service, que vai no repository, tras o objeto com os dados
        // Aqui ele é passado pra uma variavel do tipo DTO e o objeto é retornado
        ProductDTO dto = service.findById(id);
        // Costumizando a resposta, pra que o codigo que sera retornado seja o 200 e o DTO e o corpo
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    // Pageable pra fazer uma busca paginada
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        // O metodo do CONTROLLER chama o metodo do service que busca no repositorio e ja tras a lista de objetos ProductDTO
        Page<ProductDTO> dtos = service.findAll(pageable);
        return ResponseEntity.ok(dtos);
    }

    // Pois vai ser um metodo de post
    @PostMapping
    // RequestBudy = Faz com que o corpo da requisição que foi enviado pelo front, entre no parametro e instancia um DTO correspondente
    // ResponseEntity = Utilizado pra ter controle sobre as respostas HTTP
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
        // Insert vai inserir no banco de dados
        dto = service.insert(dto);
        // Fazendo isso estamos criando uma URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        // O CREATED define o status da resposta HTTP pra "201 created" - 201 é o codigo pra "CRIADO COM SUCESSO"
        return ResponseEntity.created(uri).body(dto);
    }

    // Metodo de atualizar dados
    @PutMapping(value = "/{id}")
    // Os parametros:
    // PathVariable = É semelhante ao metodo de BUSCAR POR ID, pra que o id da requisição seja usado como parametro do metodo
    // RequestBodu = É o corpo da requisição
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){

        //Chama o metodo do service que se comunica com o REPOSITORY e atualiza os dados
        dto = service.update(id, dto);

        // Retorna resposta OK, com o corpo do DTO
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    // ResponseEntity com < VOID > é como se o corpo da resposta fosse vazio
    public ResponseEntity<Void> delete(@PathVariable Long id){
        // Chama o metodo do service que vai deletar com base no id que veio no http
        service.delete(id);
        // Quando da CERTO, foi deletado com sucesso, mas a resposta não tem corpo, o codigo é 204, por isso usar o noContent.
        // Buid é pra instanciar o response
        return ResponseEntity.noContent().build();
    }

}
