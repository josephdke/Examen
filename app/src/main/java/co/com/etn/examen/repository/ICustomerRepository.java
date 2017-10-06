package co.com.etn.examen.repository;

import java.util.ArrayList;

import co.com.etn.examen.model.Customer;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public interface ICustomerRepository {

    public ArrayList<Customer> getCustomersList();

    public Customer createCustomer(@Body Customer customer);

}
