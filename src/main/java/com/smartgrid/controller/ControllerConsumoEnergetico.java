package com.smartgrid.controller;

import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.service.ServiceConsumoEnergetico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/energy")
@CrossOrigin(origins = "*")
public class ControllerConsumoEnergetico {

    private final ServiceConsumoEnergetico service;

    public ControllerConsumoEnergetico(ServiceConsumoEnergetico service) {
        this.service = service;
    }

    @PostMapping
    public ConsumoEnergetico save(@RequestBody ConsumoEnergetico consumoEnergetico) {
        return service.save(consumoEnergetico);
    }

    @GetMapping
    public List<ConsumoEnergetico> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoEnergetico> update(
            @PathVariable String id,
            @RequestBody ConsumoEnergetico consumoEnergetico
    ) {
        return ResponseEntity.ok(service.update(id, consumoEnergetico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/externos")
    public List<ConsumoEnergetico> findAllExternos() {
        return service.findAllExternos();
    }

    @PostMapping("/externos")
    public ConsumoEnergetico saveExterno(@RequestBody ConsumoEnergetico consumoEnergetico) {
        return service.saveExterno(consumoEnergetico);
    }

    @PutMapping("/externos/{id}")
    public ResponseEntity<ConsumoEnergetico> updateExterno(
            @PathVariable String id,
            @RequestBody ConsumoEnergetico consumoEnergetico
    ) {
        return ResponseEntity.ok(service.updateExterno(id, consumoEnergetico));
    }

    @DeleteMapping("/externos/{id}")
    public ResponseEntity<Void> deleteExterno(@PathVariable String id) {
        service.deleteExternoById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/externos/{id}/restaurar")
    public ResponseEntity<ConsumoEnergetico> restaurarUltimoCambioExterno(@PathVariable String id) {
        return ResponseEntity.ok(service.restaurarUltimoCambioExterno(id));
    }
}