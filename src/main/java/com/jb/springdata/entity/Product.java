package com.jb.springdata.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotEmpty
    private String name;

    @NotEmpty
    private String code;

    @NotEmpty
    private String quantity;

    @NotEmpty
    private String reference;

    private  String image;

}
