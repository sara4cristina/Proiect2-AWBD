package com.awbd.purchase.services;

import com.awbd.purchase.exceptions.*;
import com.awbd.purchase.model.Purchase;
import com.awbd.purchase.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    public Purchase findByCarAndType(String car, String type) {
        Purchase purchase = purchaseRepository.findByCarAndType(car, type);
        return purchase;
    }

    @Override
    public Purchase save(Purchase purchase) {
        Purchase purchaseSave = purchaseRepository.save(purchase);
        return purchaseSave;
    }

    @Override
    public List<Purchase> findAll(){
        List<Purchase> purchases = new LinkedList<>();
        purchaseRepository.findAll().iterator().forEachRemaining(purchases::add);
        return purchases;
    }

    @Override
    public Purchase delete(Long id){
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (! purchaseOptional.isPresent())
            throw new PurchaseNotFound("Purchase " + id + " not found!");
        purchaseRepository.delete(purchaseOptional.get());
        return purchaseOptional.get();
    }

    @Override
    public Purchase findById(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (! purchaseOptional.isPresent())
            throw new PurchaseNotFound("Purchase " + id + " not found!");
        return purchaseOptional.get();
    }


}
