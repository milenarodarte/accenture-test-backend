package com.milena.companyAndSuppliers.service;
import com.milena.companyAndSuppliers.Exception.AppException;
import com.milena.companyAndSuppliers.model.CompanySupplier;
import com.milena.companyAndSuppliers.model.Supplier;
import com.milena.companyAndSuppliers.repository.CompanySupplierRepository;
import com.milena.companyAndSuppliers.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;
@Service
public class SuppliersService {

    private final SupplierRepository supplierRepository;
    private final CompanySupplierService companySupplierService;

    private final CompanySupplierRepository companySupplierRepository;
    @Autowired

    private RestTemplate restTemplate = new RestTemplate();
    CEPValidator cepValidator = new CEPValidator(restTemplate);

    public SuppliersService(SupplierRepository supplierRepository, CompanySupplierService companySupplierService, CompanySupplierRepository companySupplierRepository){

        this.supplierRepository = supplierRepository;
        this.companySupplierService = companySupplierService;
        this.companySupplierRepository = companySupplierRepository;

    }

    public Supplier createSupplier(final Supplier supplierData) throws IOException {
        boolean isCepValid = cepValidator.validateCEP(supplierData.getCep());

        if (isCepValid) {
            if (supplierData.getCpfCnpj().length() == 11) {
                try{
                    final Supplier supplier = new Supplier(supplierData.getName(),
                            supplierData.getCpfCnpj(), supplierData.getEmail(),
                            supplierData.getCep(), supplierData.getRg(),
                            supplierData.getBirthdate());
                    return supplierRepository.save(supplier);
                } catch (Exception e) {
                    throw new AppException("CPF already on database", HttpStatus.BAD_REQUEST);
                }
            } else if (supplierData.getCpfCnpj().length() == 14){
                try {
                    final Supplier supplier = new Supplier(supplierData.getName(),
                            supplierData.getCpfCnpj(), supplierData.getEmail(),
                            supplierData.getCep(), null, null);
                    return supplierRepository.save(supplier);
                } catch (Exception e) {
                    throw new AppException("CNPJ already on database", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new AppException("your CPF must have 11 numbers or your CNPJ must have 14 numbers",
                        HttpStatus.NOT_FOUND);
            }}
        else {
            throw new AppException("Invalid CEP", HttpStatus.BAD_REQUEST);
        }

    };
    public List<Supplier> readSuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier retrieveSupplierById(final long id) {

        final Supplier supplier = supplierRepository.findById(id).orElseThrow(()
                -> new AppException("supplier not found by id", HttpStatus.NOT_FOUND));
        return supplier;

    }
    public List<Supplier> retrieveSupplierByName(final String name) {
        final List<Supplier> suppliers = supplierRepository.findAllByName(name);
        if (suppliers.isEmpty()) {
            new AppException("No suppliers found by name", HttpStatus.NOT_FOUND);
        }
        return suppliers;
    };
    public Supplier retrieveSupplierByCpfCnpj(final String cpfCnpj) {
        final Supplier supplier = supplierRepository.findByCpfCnpj(cpfCnpj).orElseThrow(()
                -> new AppException("Supplier not found by CPF/CNPJ", HttpStatus.NOT_FOUND));;
        return supplier;
    };

    public Supplier updateSupplier(final Supplier supplierData, final long id) throws IOException {
        final Supplier foundSupplier = supplierRepository.findById(id).orElseThrow(()
                -> new AppException("Supplier not found", HttpStatus.NOT_FOUND));
        boolean isCepValid = cepValidator.validateCEP(supplierData.getCep());

        if (isCepValid) {

            if (foundSupplier.getCpfCnpj().length() == 11) {
                try {
                    foundSupplier.setName(supplierData.getName());
                    foundSupplier.setCep(supplierData.getCep());
                    foundSupplier.setBirthdate(supplierData.getBirthdate());
                    foundSupplier.setEmail(supplierData.getEmail());
                    foundSupplier.setCpfCnpj(supplierData.getCpfCnpj());
                    foundSupplier.setRg(supplierData.getRg());
                    return supplierRepository.save(foundSupplier);
                } catch (Exception e) {
                    throw new AppException("CPF already on database", HttpStatus.BAD_REQUEST);
                }
            } else if (foundSupplier.getCpfCnpj().length() == 14) {
                try {
                    foundSupplier.setName(supplierData.getName());
                    foundSupplier.setCep(supplierData.getCep());
                    foundSupplier.setEmail(supplierData.getEmail());
                    foundSupplier.setCpfCnpj(supplierData.getCpfCnpj());
                    return supplierRepository.save(foundSupplier);
                } catch (Exception e) {
                    throw new AppException("CNPJ already on database", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new AppException("your CPF must have 11 numbers or your CNPJ must have 14 numbers",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("Invalid CEP", HttpStatus.BAD_REQUEST);
        }
        

    };

    public void deleteSupplier(final long id) {
        final Supplier supplierToBeDeleted = supplierRepository.findById(id).orElseThrow(()
                -> new AppException("Supplier not found", HttpStatus.NOT_FOUND));
        try {
            final List<CompanySupplier> companiesSuppliersToBeDeleted = companySupplierService.readCompanySupplierBySupplierId(id);
            companiesSuppliersToBeDeleted.forEach(cs -> {
                CompanySupplier companySupplier = companySupplierRepository.findById(cs.getId()).orElseThrow(()
                        -> new AppException("Supplier not found", HttpStatus.NOT_FOUND));

                companySupplierRepository.delete(companySupplier);
            });

            supplierRepository.delete(supplierToBeDeleted);
        } catch (Exception e) {
            new AppException( e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
