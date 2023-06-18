package com.milena.companyAndSuppliers.controller;
import com.milena.companyAndSuppliers.model.CompanySupplier;
import com.milena.companyAndSuppliers.service.CompanySupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies_suppliers")
public class CompanySupplierController {
    private final CompanySupplierService companySupplierService;
    public CompanySupplierController(CompanySupplierService companySupplierService){
        this.companySupplierService =companySupplierService;
    }
    @GetMapping
    public ResponseEntity<List<CompanySupplier>> readCompaniesSuppliers(){
        final List<CompanySupplier> allCompaniesSuppliers = companySupplierService.readCompanySupplier();
        return new ResponseEntity<List<CompanySupplier>>(allCompaniesSuppliers, HttpStatus.OK);
    }
    @GetMapping("/company_id/{companyId}")
    public ResponseEntity<List<CompanySupplier>> readCompaniesSuppliersByCompanyId(@PathVariable final String companyId) throws Exception {
        final List<CompanySupplier> companiesSuppliers = companySupplierService.readCompanySupplierByCompanyId(Long.parseLong(companyId));
        return new ResponseEntity<List<CompanySupplier>>(companiesSuppliers, HttpStatus.OK);
    }
    @GetMapping("/supplier_id/{supplierId}")
    public ResponseEntity<List<CompanySupplier>> readCompaniesSuppliersBySupplierId(@PathVariable final String supplierId) throws Exception {
        final List<CompanySupplier> companiesSuppliers = companySupplierService.readCompanySupplierBySupplierId(Long.parseLong(supplierId));
        return new ResponseEntity<List<CompanySupplier>>(companiesSuppliers, HttpStatus.OK);
    }
    @GetMapping("/company_id/{companyId}/supplier_id/{supplierId}")
    public ResponseEntity<List<CompanySupplier>> readCompaniesSuppliersBySupplierId( @PathVariable final String companyId,@PathVariable final String supplierId) throws Exception {
        final List<CompanySupplier> companiesSuppliers = companySupplierService.readCompanySupplierByCompanyIdAndSupplierId(Long.parseLong(companyId) ,Long.parseLong(supplierId));
        return new ResponseEntity<List<CompanySupplier>>(companiesSuppliers, HttpStatus.OK);
    }




}
