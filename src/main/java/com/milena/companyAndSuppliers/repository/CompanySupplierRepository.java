package com.milena.companyAndSuppliers.repository;
import com.milena.companyAndSuppliers.model.CompanySupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface CompanySupplierRepository extends JpaRepository<CompanySupplier, Long> {
    @Query("SELECT cs FROM CompanySupplier cs WHERE cs.company.id = :companyId AND cs.supplier.id = :supplierId")
    List<CompanySupplier> findByCompanyIdAndSupplierId(@Param("companyId") Long companyId, @Param("supplierId") Long supplierId);
    @Query("SELECT cs FROM CompanySupplier cs WHERE cs.company.id = :companyId ")
    List<CompanySupplier> findByCompanyId(@Param("companyId") Long companyId);
    @Query("SELECT cs FROM CompanySupplier cs WHERE cs.supplier.id = :supplierId ")
    List<CompanySupplier> findBySupplierId(@Param("supplierId") Long supplierId);
}

