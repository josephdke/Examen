package co.com.etn.examen.repository;

import co.com.etn.examen.model.DeleteResponse;
import co.com.etn.examen.model.Product;

import java.util.ArrayList;

/**
 * Created by leidyzulu on 23/09/17.
 */

public interface IProductRepository {


    ArrayList<Product> getProductList();

    Product createProduct(Product product);


    DeleteResponse deleteProduct(String id) throws RepositoryError;
}
