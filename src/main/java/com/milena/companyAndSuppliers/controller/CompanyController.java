package com.milena.companyAndSuppliers.controller;
import com.milena.companyAndSuppliers.model.Company;
import com.milena.companyAndSuppliers.model.CompanySupplier;
import com.milena.companyAndSuppliers.service.CompanyService;
import com.milena.companyAndSuppliers.service.CompanySupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanySupplierService companySupplierService;

    public CompanyController(CompanyService companyService, CompanySupplierService companySupplierService) {
        this.companyService = companyService;
        this.companySupplierService = companySupplierService;
    }
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody final Company companyData) throws Exception {
        final Company createdCompany = companyService.createCompany(companyData);

        return new ResponseEntity<Company>(createdCompany, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Company>> readCompanies(){
        final List<Company> allCompanies = companyService.readCompanies();

        return new ResponseEntity<List<Company>>(allCompanies, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> retrieveCompanyById(@PathVariable final String id) {
        final Company company = companyService.retrieveCompanyById(Long.parseLong(id));
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Company> retrieveCompanyByCnpj(@PathVariable final String cnpj)  {
        final Company company = companyService.retrieveCompanyByCnpj(cnpj);
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @GetMapping("/business_name/{businessName}")
    public ResponseEntity<List<Company>> retrieveCompanyByBusinessName(@PathVariable final String businessName)  {
        final List<Company> companies = companyService.retrieveCompanyByBusinessName(businessName);
        return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody final Company companyData, @PathVariable final  String id) throws Exception{
        final Company updatedCompany = companyService.updateCompany(companyData ,Long.parseLong(id));
        return new ResponseEntity<Company>(updatedCompany, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable final String id) {
        companyService.deleteCompany(Long.parseLong(id));
        return new ResponseEntity<Void>( HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{companyId}/supplier/{supplierId}")
    public ResponseEntity<List<CompanySupplier>> addSupplierToCompany(@PathVariable final String companyId, @PathVariable final String supplierId) throws Exception {
        final List<CompanySupplier> companyWithSupplier = companySupplierService.addSupplierToCompany(Long.parseLong(companyId), Long.parseLong(supplierId));
        return new ResponseEntity<>(companyWithSupplier, HttpStatus.CREATED);
    }
    @DeleteMapping("/{companyId}/supplier/{supplierId}")
    public ResponseEntity<Void> deleteCompanySupplier(@PathVariable final String companyId, @PathVariable final String supplierId)  {
        companySupplierService.deleteCompanySupplier(Long.parseLong(companyId), Long.parseLong(supplierId));
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
