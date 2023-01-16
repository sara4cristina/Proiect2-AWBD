package com.awbd.purchase.services.client;

import com.awbd.purchase.model.Licence;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "licence")
public interface LicenceServiceProxy {
    @GetMapping(value="/licence/type/{type}", consumes = "application/json")
    Licence findLicenceByType(@PathVariable String type);
}