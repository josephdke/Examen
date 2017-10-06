package co.com.etn.examen.repository;

import co.com.etn.examen.helper.ServicesFactory;
import co.com.etn.examen.model.DeleteResponse;
import co.com.etn.examen.model.Product;
import co.com.etn.examen.services.IServices;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by leidyzulu on 16/09/17.
 */

public class ProductRepository implements IProductRepository {

    private IServices services;


    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }
    @Override
    public ArrayList<Product> getProductList() throws RetrofitError{
        ArrayList<Product>  products = services.getProductList();
        return products;
    }
    @Override
    public Product createProduct(Product product) throws RetrofitError{
        Product productCreated = services.createProduct(product);
        return productCreated;
    }

    @Override
    public DeleteResponse deleteProduct(String id)  throws RepositoryError {
        try {

           return services.deleteProduct(id);

        }catch (RetrofitError retrofitError){

            throw  MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }

    }
}
