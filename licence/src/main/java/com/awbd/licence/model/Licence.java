package com.awbd.licence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "licence")
@Entity
public class Licence extends RepresentationModel<Licence> {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name="price")
    private int price;
}
