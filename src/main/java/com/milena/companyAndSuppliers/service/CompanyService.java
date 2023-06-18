package com.milena.companyAndSuppliers.service;
import com.milena.companyAndSuppliers.Exception.AppException;
import com.milena.companyAndSuppliers.model.Company;
import com.milena.companyAndSuppliers.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private RestTemplate restTemplate = new RestTemplate();
    CEPValidator cepValidator = new CEPValidator(restTemplate);

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(final Company companyData) throws Exception {
        boolean isCepValid = cepValidator.validateCEP(companyData.getCep());
        if (isCepValid) {
            try{
                final Company company = new Company(companyData.getBusiness_name(),
                        companyData.getCnpj(), companyData.getCep());
                return companyRepository.save(company);} catch (Exception e) {
                    throw new AppException("CNPJ already on database", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new AppException("Invalid CEP", HttpStatus.BAD_REQUEST);
        }
    }
    public List<Company> readCompanies(){
        return companyRepository.findAll();
    }

    public Company retrieveCompanyById(final long id)  {
        final Company company = companyRepository.findById(id).orElseThrow(()
                -> new AppException("Company not found by ID", HttpStatus.NOT_FOUND));
        return company;
    }

    public Company retrieveCompanyByCnpj(final String cnpj) {
        final Company company = companyRepository.findByCnpj(cnpj).orElseThrow(() ->
                new AppException("Company not found by CNPJ", HttpStatus.NOT_FOUND));;
        return company;
    };

    public List<Company> retrieveCompanyByBusinessName(final String businessName)  {
        final List<Company> companies = companyRepository.findAllByBusinessName(businessName);
        if (companies.isEmpty()) {
            new AppException("No companies found by name", HttpStatus.NOT_FOUND);
        }
        return companies;
    };

    public Company updateCompany(final Company companyData, final long id) throws Exception{
        final Company foundCompany = companyRepository.findById(id).orElseThrow(() ->
                new AppException("Company not found", HttpStatus.NOT_FOUND));
        boolean isCepValid = cepValidator.validateCEP(companyData.getCep());
        if (isCepValid){
            foundCompany.setCep(companyData.getCep());
            try {
                foundCompany.setCnpj(companyData.getCnpj());
            } catch (Exception e) {
                throw new AppException("CNPJ already on database", HttpStatus.BAD_REQUEST);
            }
            foundCompany.setBusiness_name(companyData.getBusiness_name());
            return companyRepository.save(foundCompany);
        } else {
            throw new AppException("Invalid CEP", HttpStatus.BAD_REQUEST);
        }
    };

    public void deleteCompany(final long id) {
        final Company companyToBeDeleted = companyRepository.findById(id).orElseThrow(()
                -> new AppException("Company not found by id", HttpStatus.NOT_FOUND));
        companyRepository.delete(companyToBeDeleted);
    }

}
