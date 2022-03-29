package com.jb.springdata.modelo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
}
