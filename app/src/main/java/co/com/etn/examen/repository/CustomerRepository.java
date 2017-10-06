package co.com.etn.examen.repository;

import java.util.ArrayList;

import co.com.etn.examen.helper.ServicesFactory;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.DeleteResponse;
import co.com.etn.examen.model.Product;
import co.com.etn.examen.services.IServices;
import retrofit.RetrofitError;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class CustomerRepository implements ICustomerRepository {

    private IServices services;

    public CustomerRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Customer> getCustomersList() throws RetrofitError {
        ArrayList<Customer> customers = services.getCustomersList();
        return customers;
    }

    @Override
    public Customer createCustomer(Customer customer) throws RetrofitError{
        Customer customerCreated = services.createCustomer(customer);
        return customerCreated;
    }

}
