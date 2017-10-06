package co.com.etn.examen.services;

import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.DeleteResponse;
import co.com.etn.examen.model.Product;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by leidyzulu on 16/09/17.
 */

public interface IServices {

    @GET("/products")
    ArrayList<Product> getProductList();

    @POST("/products")
    Product createProduct(@Body Product product);

    @DELETE("/products/{id}")
    DeleteResponse deleteProduct(@Path("id")String id);

    @GET("/customers")
    ArrayList<Customer> getCustomersList();

    @POST("/customers")
    Customer createCustomer(@Body Customer customer);


}
