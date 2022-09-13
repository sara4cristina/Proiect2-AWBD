package com.awbd.purchase.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "purchase")
public class Purchase extends RepresentationModel<Purchase> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="type")
    @Size(max=20, message = "Ex: ROVINIETA, ITP")
    private String type;

    @Column(name="car")
    private String car;

    @Column(name="price")
    private int price;

}
