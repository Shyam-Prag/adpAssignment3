package za.ac.cput.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.entity.Customer;
import za.ac.cput.factory.CustomerFactory;
import za.ac.cput.entity.Customer.Builder.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    private static Customer customer= CustomerFactory.createCustomer("John","Doe");

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private String customerURL="http://localhost:8080/customer/";

    private String username = "username";
    private String password = "password";

    @Test
    void create() {
        String url=customerURL+"create";
        httpHeaders.setBasicAuth(username, password);
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer,httpHeaders);
        ResponseEntity<Customer> postResponse = testRestTemplate.exchange(url,HttpMethod.POST ,httpEntity, Customer.class);
        System.out.println("Customer added: " + customer);
        customer = postResponse.getBody();
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(customer.getId(), postResponse.getBody().getId());
    }

    @Test
    void read() {
        String url=customerURL+"read/"+customer.getId();
        System.out.println("URL: "+url);
        ResponseEntity<Customer> responseEntity=testRestTemplate.getForEntity(url,Customer.class);
        customer=responseEntity.getBody();
        assert customer != null;
        assertEquals(customer.getId(), Objects.requireNonNull(responseEntity.getBody()).getId());
    }

    /* @Test
    void update() {
        Customer newCustomer=new Customer.Builder().copy(customer).setFirstName("Cameron").setFirstName("Noemdo").build();
        String url=customerURL+"update";

        System.out.println("Update customer: "+newCustomer);

        System.out.println("URL: "+url);

        ResponseEntity<Customer> responseEntity=testRestTemplate.withBasicAuth(username,password).postForEntity(url,newCustomer,Customer.class);
        assertEquals(customer.getId(),responseEntity.getBody().getId());
    }*/

    @Test
    void delete() {
        String url=customerURL+"delete/"+customer.getId();
        System.out.println("URL: "+url);
        testRestTemplate.delete(url);
    }

    @Test
    void getAll()
    {
        String url=customerURL+"getall";
        HttpHeaders httpHeaders=new HttpHeaders();
        HttpEntity<String> entity=new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> response=testRestTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        System.out.println(response);
        System.out.println(response.getBody());
    }
}