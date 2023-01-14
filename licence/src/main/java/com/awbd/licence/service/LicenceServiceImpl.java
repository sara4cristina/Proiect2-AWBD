package com.awbd.licence.service;


import com.awbd.licence.config.PropertiesConfig;
import com.awbd.licence.exceptions.LicenceNotFound;
import com.awbd.licence.model.Licence;
import com.awbd.licence.repo.LicenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LicenceServiceImpl implements LicenceService{

    @Autowired
    private PropertiesConfig configuration;

    @Autowired
    private LicenceRepository licenceRepository;

    @Override
    public Licence findByType(String type) {
        Licence licence = licenceRepository.findByType(type);
        return licence;
    }

    @Override
    public Licence findById(Long id) {
        Optional<Licence> licenceOptional = licenceRepository.findById(id);
        if (! licenceOptional.isPresent())
            throw new LicenceNotFound("Licence " + id + " not found!");
        return licenceOptional.get();
    }
    @Override
    public List<Licence> findAll(){
        List<Licence> licences = new LinkedList<>();
        licenceRepository.findAll().iterator().forEachRemaining(licences::add);
        return licences;
    }

    @Override
    public Licence delete(Long id){
        Optional<Licence> licenceOptional = licenceRepository.findById(id);
        if (! licenceOptional.isPresent())
            throw new LicenceNotFound("Licence " + id + " not found!"); licenceRepository.delete(licenceOptional.get());
        return licenceOptional.get();
    }

    @Override
    public Licence save(Licence licence){
        Licence licenceSaved= licenceRepository.save(licence);
        return licenceSaved;
    }

}













