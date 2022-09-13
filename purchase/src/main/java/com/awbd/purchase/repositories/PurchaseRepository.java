package com.awbd.purchase.repositories;

import com.awbd.purchase.model.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Purchase findByCarAndType(String car, String type);
}
