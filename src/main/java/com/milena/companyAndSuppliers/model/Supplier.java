package com.milena.companyAndSuppliers.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 14, nullable = false, unique = true)
    private String cpfCnpj;

    @Column(length = 127, nullable = false)
    private String email;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 9, nullable = true)
    private String rg;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
            }, mappedBy = "suppliers")
    private Set<Company> companies = new HashSet<>();

    public Supplier() {
    }

    public Supplier(String name, String cpfCnpj, String email, String cep, String rg, Date birthdate) {
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.cep = cep;
        this.rg = rg;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }
}
