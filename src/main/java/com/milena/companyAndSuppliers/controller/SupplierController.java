package com.milena.companyAndSuppliers.controller;
import com.milena.companyAndSuppliers.model.Supplier;
import com.milena.companyAndSuppliers.service.SuppliersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SuppliersService suppliersService;
    public SupplierController(SuppliersService suppliersService){
        this.suppliersService = suppliersService;
    }
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody final Supplier supplierData) throws Exception {
        final Supplier createdSupplier = suppliersService.createSupplier(supplierData);
        return new ResponseEntity<Supplier>(createdSupplier, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Supplier>> readSuppliers(){
        final List<Supplier> allSuppliers = suppliersService.readSuppliers();
        return new ResponseEntity<List<Supplier>>(allSuppliers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> retrieveSupplierById(@PathVariable final String id) {
        final Supplier supplier = suppliersService.retrieveSupplierById(Long.parseLong(id));
        return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Supplier>> retrieveSupplierByName(@PathVariable final String name) {
        final List<Supplier> suppliers = suppliersService.retrieveSupplierByName(name);
        return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
    };
    @GetMapping("/cpfcnpj/{cpfcnpj}")
    public ResponseEntity<Supplier> retrieveSupplierByCpfCnpj(@PathVariable final String cpfcnpj) {
        final Supplier supplier = suppliersService.retrieveSupplierByCpfCnpj(cpfcnpj);
        return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
    };
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody final Supplier supplierData, @PathVariable final  String id) throws Exception{
        final Supplier updatedSupplier = suppliersService.updateSupplier(supplierData ,Long.parseLong(id));
        return new ResponseEntity<Supplier>(updatedSupplier, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable final String id) {
        suppliersService.deleteSupplier(Long.parseLong(id));
        return new ResponseEntity<Void>( HttpStatus.NO_CONTENT);
    }


}
