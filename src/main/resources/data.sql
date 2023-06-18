
CREATE SEQUENCE companies_id_seq;
ALTER TABLE companies ALTER COLUMN id SET DEFAULT nextval('companies_id_seq');

CREATE SEQUENCE suppliers_id_seq;
ALTER TABLE suppliers  ALTER COLUMN id SET DEFAULT nextval('suppliers_id_seq');

CREATE SEQUENCE company_supplier_id_seq;
ALTER TABLE company_supplier  ALTER COLUMN id SET DEFAULT nextval('company_supplier_id_seq');

INSERT INTO "companies" ("business_name", "CNPJ", "CEP")
VALUES
    ('Rodarte Tecnologia', '36512544000180', '54410010');

INSERT INTO "suppliers" ("name", "CPF_CNPJ", "email", "CEP", "RG", "birthdate" )
VALUES
    ('Yuri', '65498725410', 'yuri@mail.com', '54410090', '947641286', '1993-09-12');

INSERT INTO "company_supplier" ("company_id", "supplier_id")
VALUES
    (1,1);
