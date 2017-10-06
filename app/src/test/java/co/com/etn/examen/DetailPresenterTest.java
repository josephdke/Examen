package co.com.etn.examen;

import co.com.etn.examen.helper.Constants;
import co.com.etn.examen.helper.IValidateInternet;
import co.com.etn.examen.model.DeleteResponse;
import co.com.etn.examen.model.Product;
import co.com.etn.examen.repository.IProductRepository;
import co.com.etn.examen.repository.RepositoryError;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by leidyzulu on 23/09/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {



    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductRepository productRepository;

    @Mock
    IDetailProductView detailProductView;

    DetailProductPresenter detailProductPresenter;

    private Product getProduct(){
        Product product =  new Product();
        product.setDescription("Empanada");
        product.setName("Empanada");
        product.setId("121u2gjg1g");
        product.setPrice("1500");
        return  product;
    }




    @Before
    public void setUp() throws Exception{
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(productRepository));
        detailProductPresenter.inject(detailProductView, validateInternet);
    }


    @Test
    public void methodDeleteProductWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        Product product = getProduct();
        String id = "13g1jhhd232";
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductPresenter).createThreadDeleteProduct(id);
        verify(detailProductView, never()).showToast(R.string.validate_internet);
    }


    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "13g1jhhd232";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductView).showToast(R.string.validate_internet);
        verify(detailProductPresenter, never()).createThreadDeleteProduct(id);

    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryTrue() throws RepositoryError{
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(true);
        String id = "13g1jhhd232";
        when(productRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertTrue(deleteResponse.isStatus());
        verify(detailProductView).showToast(R.string.correct);
        verify(detailProductView, never()).showToast(R.string.error_delete_product);

    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryFalse() throws RepositoryError{
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(false);
        String id = "13g1jhhd232";
        when(productRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteProductRepository(id);
        Assert.assertFalse(deleteResponse.isStatus());
        verify(detailProductView).showToast(R.string.error_delete_product);
        verify(detailProductView, never()).showToast(R.string.correct);

    }

    /*@Test
    public void methodCreateThreadShouldShowProgressDialog(){
        String id = "13g1jhhd232";
        detailProductPresenter.createThreadDeleteProduct(id);
        verify(detailProductView).showProgress(R.string.loading_message);
    }*/

    @Test
    public void methodDeleteProductShouldShowAlertWhenRepositoryReturnError()throws RepositoryError{
        String id = "13g1jhhd232";
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(productRepository.deleteProduct(id)).thenThrow(repositoryError);
        detailProductPresenter.deleteProductRepository(id);
        verify(detailProductView).showToast(repositoryError.getMessage());
        verify(detailProductView, never()).showToast(R.string.correct);
        verify(detailProductView, never()).showToast(R.string.error_delete_product);
    }


}
