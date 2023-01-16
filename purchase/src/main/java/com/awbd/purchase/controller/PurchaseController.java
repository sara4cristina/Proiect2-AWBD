package com.awbd.purchase.controller;

import com.awbd.purchase.model.Licence;
import com.awbd.purchase.model.Purchase;
import com.awbd.purchase.services.PurchaseService;
import com.awbd.purchase.services.client.LicenceServiceProxy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @Autowired
    LicenceServiceProxy licenceServiceProxy;

//    @GetMapping("/purchase/car/{car}/type/{type}")
//    Purchase findByCarAndType(@PathVariable String car,
//                                 @PathVariable String type){
//        Purchase purchase = purchaseService.findByCarAndType(car, type);
//        Licence licence = licenceServiceProxy.findLicenceByType(type);
//        log.info(String.valueOf(licence.getPrice()));
//        purchase.setPrice(licence.getPrice());
//        return purchase;
//    }

    @GetMapping("/")
    String helloWord() {
        return "Hello Word!";
    }

    @GetMapping(value = "/purchase/list", produces = {"application/hal+json"})
    public CollectionModel<Purchase> findAll() {
        List<Purchase> purchases = purchaseService.findAll();
        for (final Purchase purchase : purchases) {
            Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(purchase.getId())).withSelfRel();
            purchase.add(selfLink);
            Link deleteLink = linkTo(methodOn(PurchaseController.class).deletePurchase(purchase.getId())).withRel("deletePurchase");
            purchase.add(deleteLink);
        }
        Link link = linkTo(methodOn(PurchaseController.class).findAll()).withSelfRel();
        CollectionModel<Purchase> result = CollectionModel.of(purchases, link);
        return result;
    }

    @GetMapping("/purchase/car/{car}/type/{type}")
    Purchase findByCarAndType(@PathVariable String car,
                              @PathVariable String type) {
        Purchase purchase = purchaseService.findByCarAndType(car, type);
        Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(purchase.getId())).withSelfRel();
        purchase.add(selfLink);
        return purchase;
    }

    @PostMapping("/purchase")
    @CircuitBreaker(name="licenceById", fallbackMethod = "savePurchaseFallback")
    public ResponseEntity<Purchase> save(@Valid @RequestBody Purchase purchase) {

        Licence licence = licenceServiceProxy.findLicenceByType(purchase.getType());
        purchase.setPrice(licence.getPrice());
        log.warn("The price of the purchase in the PurchaseController is " + licence.getPrice());
        Purchase savedPurchase = purchaseService.save(purchase);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{purchaseId}").buildAndExpand(purchase.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(purchase);
    }

    public ResponseEntity<Purchase> savePurchaseFallback( Purchase purchase, Throwable throwable){
        //If licence is not available we will set the price set in the body of the request
        Purchase savedPurchase = purchaseService.save(purchase);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{purchaseId}").buildAndExpand(purchase.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(purchase);
    }


    @Operation(summary = "delete purchase by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "purchase deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Purchase.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Purchase not found",
                    content = @Content)})
    @DeleteMapping("/purchase/{purchaseId}")
    public Purchase deletePurchase(@PathVariable Long purchaseId) {

        Purchase purchase = purchaseService.delete(purchaseId);
        return purchase;
    }

    @GetMapping("/purchase/{purchaseId}")
    public Purchase getPurchase(@PathVariable Long purchaseId) {

        Purchase purchase = purchaseService.findById(purchaseId);
        return purchase;

    }
}
