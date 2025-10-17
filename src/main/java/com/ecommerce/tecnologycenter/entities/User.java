package com.ecommerce.tecnologycenter.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Especificando que esta classe é uma entidade do negocio
@Entity
//Espefificando o nome que deve aparecer esta tabela de usuario no banco de dados
@Table(name = "tb_user")
public class User {

    //Diz que é a PRIMARY KEY
    @Id
    //Faz com que o ID seja gerado automaticamente, AUTO-INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //Faz com que o campo E-mail na hora de ser convertido para o BANCO RELACIONAl esta coluna sera unica, o banco não
    // ira permitir repetição
    @Column(unique = true)
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String password;
    //Será implementado depois
    //private String[] roles;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public User(){
    }

    public User(Long id, String name, String email, String phone, LocalDate birthDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
