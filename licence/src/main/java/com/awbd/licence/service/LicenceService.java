package com.awbd.licence.service;

import com.awbd.licence.model.Licence;

import java.util.List;

public interface LicenceService {

    Licence findByType(String type);
    Licence findById(Long id);
    List<Licence> findAll();
    Licence delete(Long id);
    Licence save(Licence licence);

}
