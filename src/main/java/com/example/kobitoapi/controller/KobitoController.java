package com.example.kobitoapi.controller;

import com.example.kobitoapi.dto.Kobito;
import com.example.kobitoapi.service.KobitoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/kobito")
public class KobitoController {
    private final KobitoService kobitoService;

    public KobitoController(KobitoService kobitoService) {
        this.kobitoService = kobitoService;
    }
    @GetMapping
    public ResponseEntity<List<Kobito>> findAllKobito(){
        List<Kobito> kobito =  kobitoService.findAll();

        return new ResponseEntity<>(kobito, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Kobito> saveKobito(@RequestBody Kobito kobito,
                                             UriComponentsBuilder uriComponentsBuilder){
        Kobito createdKobito = kobitoService.saveKobito(kobito);

        URI location = uriComponentsBuilder.path("/api/kobito/{id}").buildAndExpand(createdKobito.getId()).toUri();

        return ResponseEntity.created(location).body(createdKobito);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kobito> findById(@PathVariable Long id){
        Kobito kobito = kobitoService.findById(id);
        return ResponseEntity.ok(kobito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){

        kobitoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kobito> update(@PathVariable long id,
                                         @RequestBody Kobito kobito){
        kobitoService.update(kobito,id);
        return ResponseEntity.ok(kobito);
    }

    @GetMapping("/search")
    public List<Kobito> searchByName(@RequestParam String name){
        return kobitoService.findByName(name);
    }

}
