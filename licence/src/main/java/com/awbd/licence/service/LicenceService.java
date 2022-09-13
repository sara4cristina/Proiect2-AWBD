package com.awbd.licence.service;


import com.awbd.licence.config.PropertiesConfig;
import com.awbd.licence.exceptions.LicenceNotFound;
import com.awbd.licence.model.Licence;
import com.awbd.licence.repo.LicenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LicenceService {

    @Autowired
    private PropertiesConfig configuration;

    @Autowired
    private LicenceRepository licenceRepository;

    public Licence findByType(String type) {
        Licence purchase = licenceRepository.findByType(type);
        purchase.setPrice(purchase.getPrice() - purchase.getPrice()*configuration.getDiscount()/100);
        return purchase;
    }

    public Licence findById(Long id) {
        Optional<Licence> purchaseOptional = licenceRepository.findById(id);
        if (! purchaseOptional.isPresent())
            throw new LicenceNotFound("Licence " + id + " not found!");
        return purchaseOptional.get();
    }

}
