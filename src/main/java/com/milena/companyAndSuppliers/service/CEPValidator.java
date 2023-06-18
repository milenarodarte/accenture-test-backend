package com.milena.companyAndSuppliers.service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CEPValidator {
    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    public CEPValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static class CepResponse {
        private String cep;
        private String uf;
        private String cidade;
        private String bairro;
        private String logradouro;

        @JsonIgnoreProperties(ignoreUnknown = true)
        private String aux;

        public String getAux() {
            return aux;
        }

        public String getCep() {
            return cep;
        }

        public String getUf() {
            return uf;
        }
        public String getCidade() {
            return cidade;
        }

        public String getBairro() {
            return bairro;
        }

        public String getLogradouro() {
            return logradouro;
        }
    }
    public boolean validateCEP(String cep) throws IOException {

        URL url = new URL("http://cep.la/"+cep);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept", "application/json");

        InputStream responseStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper();

        CepResponse cepResponse = mapper.readValue(responseStream, CepResponse.class);

        String city = cepResponse.getCidade();

        if (!city.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isParana(String cep) throws IOException {

        URL url = new URL("http://cep.la/"+cep);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept", "application/json");

        InputStream responseStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper();

        CepResponse cepResponse = mapper.readValue(responseStream, CepResponse.class);

        String uf = cepResponse.getUf();

        if (uf.equals("PR")){
            return true;
        } else {
            return false;
        }
    }

}
