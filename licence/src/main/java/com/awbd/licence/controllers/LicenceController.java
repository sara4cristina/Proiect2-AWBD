package com.awbd.licence.controllers;

import com.awbd.licence.model.Licence;
import com.awbd.licence.service.LicenceService;
import com.awbd.licence.service.LicenceServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LicenceController {
    @Autowired
    private LicenceService licenceService;

    @GetMapping("/licence/id/{licenceId}")
    public Licence getLicence(@PathVariable Long licenceId) {

        Licence licence = licenceService.findById(licenceId);
        return licence;

    }

    @GetMapping("/licence/type/{type}")
    public Licence getLicenceByType(@PathVariable String type) {
        return licenceService.findByType(type);
    }

    @GetMapping(value = "/licence/list", produces = {"application/hal+json"})
    public CollectionModel<Licence> findAll() {
        List<Licence> licences = licenceService.findAll();
        for (final Licence licence : licences) {
            Link selfLink =  linkTo(methodOn(LicenceController.class).getLicence(licence.getId())).withSelfRel();
            licence.add(selfLink);
            Link deleteLink = linkTo(methodOn(LicenceController.class).deleteLicence(licence.getId())).withRel("deleteLicence");
            licence.add(deleteLink);
        }
        Link link = linkTo(methodOn(LicenceController.class).findAll()).withSelfRel();
        CollectionModel<Licence> result = CollectionModel.of(licences, link);
        return result;
    }

    @Operation(summary = "delete licence by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "licence deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Licence.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Licence not found",
                    content = @Content)})
    @DeleteMapping("/licence/{licenceId}")
    public Licence deleteLicence(@PathVariable Long licenceId) {

        Licence licence = licenceService.delete(licenceId);
        return licence;
    }

    @PostMapping("/licence")
    public ResponseEntity<Licence> save(@Valid @RequestBody Licence licence) {

        Licence savedLicence = licenceService.save(licence);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{licenceId}").buildAndExpand(licence.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(licence);
    }

}