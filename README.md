# Back-end API Companies-Suppliers

### Postos a se destacar:
#### Essa APi foi feita sem nenhuma experiência prévia na linguagem Java, sendo, além do meu primeiro contato com uma linguagem fortemente tipada, o primeiro trabalho com programação POO. Foi desenvolvida em 3 dias e meio. 

### Linguagem de programação escolhida: Java com framework Springboot. 
#### BIBLIOTECA JPA para auxiliar no acesso e manipulação do banco de dados.
#### SPRING MVC para auxiliar com solicitações e respostas HTTP.
#### SPRING SERIALIZR para construir e inicilizar o projeto com Spring boot.
#### MAVEN para compilar e gerir dependências.

### Banco de dados
#### PostgreSQL
##### modelagem de banco de dados: https://app.diagrams.net/


# COMO INICIALIZAR O PROJETO
## IMPORTANTE: o arquivo application.properties.exemple contém as configurações de inicialização, porta do servidor e configurações do banco de dados. Adicione no mesmo nível dele um arquivo chamado "application.properties" e cole o conteúdo do application.properties.example nela. Em seguida configure o seu banco de dados local comn a URL (confira se sua porta é 5432), username e password. 

#### clone o repositório em sua máquina
#### varificar se tem em sua máquina o JDK em sua útilma versao (20), Spring Boot (3.1.0) e Maven instalado.
#### importar o projeto para sua IDE de preferência. No desenvolvimento foi utilizado o Intellij IDEA
#### rode o comando para abrir o servidor na porta configurada. A padrão é 3000: mvn spring-boot:run

# ROTAS (http://localhost:3000)
## COMPANIES 

### GET (/companies)
#### retorno de todas as companies cadastradas no banco de dados. 

### GET (/companies/{id})
#### retorno da company buscada por seu ID.

### GET (/companies/cnpj/{cnpj}) 
#### retorno da company buscada por seu CNPJ

### GET (companies/business_name/{businessName})
#### retorno das companies por seu business_name 

### POST (/companies) 
#### criaçao da company através do corpo completo de requisição: {	"business_name": "string", "cnpj": "string",
#### "cep": "string"}

### PUT (companies/{id})
#### update da company através do envio do seu id e corpo completo de requisição: {	"business_name": "string", "cnpj": "string",
#### "cep": "string"}

### DELETE (/companies/{id})
#### deleçao a company e seus respectivos relacionamentos

### POST (companies/{companyId}/supplier/{supplierId})
#### relaciona um supplier a uma company através dos seus ids passados no parâmetro. 

### DELETE (companies/{companyId}/supplier/{supplierId})
#### deleta o relacionamento de um supplier com uma company


## SUPPLIERS


### GET (/suppliers)
#### retorno de todas os suppliers cadastradas no banco de dados. 

### GET (/suppliers/{id})
#### retorno do supplier buscada por seu ID.

### GET (/suppliers/name/{name})
#### retorno dos suppliers por seu name 

### GET (/suppliers/cpfcnpj/{cpfcnpj}) 
#### retorno do supplier buscado por seu CNPJ ou CPF

### POST (/suppliers) 
#### criaçao do supplier através do envio do seu id e corpo completo de requisição: {	"name": "string",	"cpfCnpj": "string",
####	"email": "string", "cep": "string", "rg": "string", "birthdate": "YYYY-MM-DD"} - RG e Birthdate apenas para pessoa física.

### PUT (/suppliers/{id})
#### cupdate do supplier através do envio do seu id e corpo completo de requisição: {	"name": "string",	"cpfCnpj": "string",
####	"email": "string", "cep": "string", "rg": "string", "birthdate": "YYYY-MM-DD"} - RG e Birthdate apenas para pessoa física.

### DELETE (suppliers/{Id})
#### deleta um supplier e seus respectivos relacionamentos


## COMPANY_SUPPLIER (/companies_suppliers)


### GET (/companies_suppliers)
#### retorno de todops os relacionamentos cadastradas no banco de dados. 

### GET (/companies_suppliers/company_id/{companyId})
#### retorno de todops os relacionamentos que tiverem o id da company_id passada. 

### GET (/companies_suppliers/supplier_id/{supplierId})
#### retorno de todops os relacionamentos que tiverem o id do supplier_id passada. 

### GET (/companies_suppliers/company_id/{companyId}/supplier_id/{supplierId})
#### retorno de todops o relacionamento que corresponder a company_id com o supplier_id passado. 


