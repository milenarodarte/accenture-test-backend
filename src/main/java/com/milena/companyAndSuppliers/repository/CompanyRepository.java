package com.milena.companyAndSuppliers.repository;
import com.milena.companyAndSuppliers.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    @Query("SELECT c FROM Company c WHERE c.business_name = :business_name")
    List<Company> findAllByBusinessName(@Param("business_name") String companyName);

    @Query("SELECT c FROM Company c WHERE c.cnpj = :cnpj")
    Optional<Company> findByCnpj(@Param("cnpj") String cnpj);

    List<Company> findCompaniesBySuppliersId(Long supplierId);
}
