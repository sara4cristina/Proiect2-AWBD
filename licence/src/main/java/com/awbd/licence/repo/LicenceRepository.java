package com.awbd.licence.repo;

import com.awbd.licence.model.Licence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenceRepository extends CrudRepository<Licence, Long> {

    Licence findByType(String type);
}
