package com.milena.companyAndSuppliers.repository;
import com.milena.companyAndSuppliers.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s FROM Supplier s WHERE s.name = :name")
    List<Supplier> findAllByName(@Param("name") String name);
    @Query("SELECT s FROM Supplier s WHERE s.cpfCnpj = :cpfCnpj")
    Optional<Supplier> findByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);

}
