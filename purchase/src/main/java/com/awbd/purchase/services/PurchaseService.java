package com.awbd.purchase.services;

import com.awbd.purchase.model.Purchase;

import java.util.List;

public interface PurchaseService {
    Purchase findByCarAndType(String car, String type);
    Purchase save(Purchase Purchase);
    List<Purchase> findAll();
    Purchase delete(Long id);

    Purchase findById(Long id);
}
