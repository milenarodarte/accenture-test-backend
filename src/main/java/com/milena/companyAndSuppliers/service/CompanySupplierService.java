package com.milena.companyAndSuppliers.service;
import com.milena.companyAndSuppliers.Exception.AppException;
import com.milena.companyAndSuppliers.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.milena.companyAndSuppliers.model.Company;
import com.milena.companyAndSuppliers.model.CompanySupplier;
import com.milena.companyAndSuppliers.repository.CompanyRepository;
import com.milena.companyAndSuppliers.repository.CompanySupplierRepository;
import com.milena.companyAndSuppliers.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CompanySupplierService {
    private static final Logger logger = LoggerFactory.getLogger(CompanySupplierService.class);
    private final CompanySupplierRepository companySupplierRepository;
    private final CompanyRepository companyRepository ;
    private final SupplierRepository supplierRepository;
    private RestTemplate restTemplate = new RestTemplate();
    CEPValidator cepValidator = new CEPValidator(restTemplate);
    public CompanySupplierService(CompanySupplierRepository companySupplierRepository, CompanyRepository companyRepository, SupplierRepository supplierRepository) {
        this.companySupplierRepository = companySupplierRepository;
        this.companyRepository = companyRepository;
        this.supplierRepository = supplierRepository;
    }
        public List<CompanySupplier> readCompanySupplier () {
            return companySupplierRepository.findAll();
        }

        public List<CompanySupplier> readCompanySupplierByCompanyId(final Long companyId) {
           try {
               final List<CompanySupplier> companySuppliersFound = companySupplierRepository.findByCompanyId(companyId);
               return companySuppliersFound;
           } catch (Exception e) {
               throw new AppException("CompanySuppliers not found by companyId", HttpStatus.NOT_FOUND);
           }
        }
        public List<CompanySupplier> readCompanySupplierBySupplierId(final Long SupplierId) {
            try {
                final List<CompanySupplier> companySuppliersFound = companySupplierRepository.findBySupplierId(SupplierId);
                return companySuppliersFound;
            } catch (Exception e) {
                throw new AppException("CompanySuppliers not found by supplierId", HttpStatus.NOT_FOUND);

            }
        }
        public List<CompanySupplier> readCompanySupplierByCompanyIdAndSupplierId(final Long companyId ,final Long supplierId){
            try {
                final List<CompanySupplier> companySuppliersFound = companySupplierRepository.findByCompanyIdAndSupplierId(companyId, supplierId);
                return companySuppliersFound;
            } catch (Exception e) {
                throw new AppException("CompanySuppliers not found by supplierId and SupplierId", HttpStatus.NOT_FOUND);
            }
        }
         public List<CompanySupplier> addSupplierToCompany (final Long companyId, Long supplierId) throws Exception {

            final Company companyFound = companyRepository.findById(companyId).orElseThrow(()
                    -> new AppException("company not found", HttpStatus.NOT_FOUND));
            final Supplier supplierFound = supplierRepository.findById(supplierId).orElseThrow(()
                    -> new AppException("supplier not found", HttpStatus.NOT_FOUND));
            boolean isParana = cepValidator.isParana(supplierFound.getCep());
            Date supplierBirthday =  supplierFound.getBirthdate();

            LocalDate birthdate = supplierBirthday.toLocalDate();

            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthdate, currentDate).getYears();

            if (!isParana) {
                companyFound.addSupplier(supplierFound);
                companyRepository.save(companyFound);
                final List<CompanySupplier> companySupplierFound = companySupplierRepository.findByCompanyIdAndSupplierId(companyId, supplierId);
                return companySupplierFound;
            } else if (isParana && age >= 18) {
                 companyFound.addSupplier(supplierFound);
                 companyRepository.save(companyFound);
                 final List<CompanySupplier> companySupplierFound = companySupplierRepository.findByCompanyIdAndSupplierId(companyId, supplierId);
                 return companySupplierFound;
             } else {
                throw new AppException("Suppliers must be 18 Years Old or older to be a supplier of a Paraná based Company",
                        HttpStatus.BAD_REQUEST);
            }
        };
        public void deleteCompanySupplier(final long companyId, long supplierId) {
            final Company companyFound = companyRepository.findById(companyId).orElseThrow(()
                    -> new AppException("company not found by Id", HttpStatus.NOT_FOUND));
            try {
                companyFound.removeSupplier(supplierId);
            } catch (Exception e) {
                throw new AppException("SupplierId not found", HttpStatus.NOT_FOUND);
            }
        }
}

