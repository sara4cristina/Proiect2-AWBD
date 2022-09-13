package com.awbd.purchase.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Licence {

    private Long id;
    private String type;
    private int price;
}

