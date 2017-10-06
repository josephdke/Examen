package co.com.etn.examen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import co.com.etn.examen.helper.IValidateInternet;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Location;
import co.com.etn.examen.model.Phone;
import co.com.etn.examen.presenter.CreateCustomerPresenter;
import co.com.etn.examen.repository.ICustomerRepository;
import co.com.etn.examen.views.activities.ICreateCustomerView;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerPresenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    ICustomerRepository customerRepository;

    @Mock
    ICreateCustomerView createCustomerView;

    CreateCustomerPresenter createCustomerPresenter;

    @InjectMocks
    Customer customer;

    @Before
    public void setUp() throws Exception{
        createCustomerPresenter = Mockito.spy(new CreateCustomerPresenter(customerRepository));
        createCustomerPresenter.inject(createCustomerView, validateInternet);
    }

    private Customer getCustomer(){
        Customer customer = new Customer();
        customer.setName("name");
        customer.setSurname("surname");
        customer.setPhoneList(new ArrayList<Phone>());
        customer.getPhoneList().add( new Phone( "tel1", "number1", new Location( "type1", new double[]{ 1, 0 } ) ) );
        customer.getPhoneList().add( new Phone( "tel2", "number2", new Location( "type2", new double[]{ 1, 0 } ) ) );
        return customer;
    }

    @Test
    public void methodCreateCustomerWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        Customer customer = getCustomer();
        when(validateInternet.isConnected()).thenReturn(true);
        createCustomerPresenter.createNewCustomer( customer );
        verify(createCustomerPresenter).createThreadCreateCustomer(refEq(customer));
        verify(createCustomerView, never()).showAlertInternet(R.string.error, R.string.validate_internet);
    }

    @Test
    public void methodCreateCustomerWithoutConnectionShouldShowAlertDialog(){
        Customer customer = getCustomer();
        when(validateInternet.isConnected()).thenReturn(false);
        createCustomerPresenter.createNewCustomer( customer );
        verify(createCustomerView).showAlertInternet(R.string.error, R.string.validate_internet);
        verify(createCustomerPresenter, never()).createThreadCreateCustomer(refEq(customer));
    }

    /*@Test
    public void methodCreateCustomerShouldCallMethodCreateCustomerInRepositoryTrue() throws RepositoryError {
        Customer customer = getCustomer();
        when(customerRepository.createCustomer(refEq(customer))).thenReturn(deleteResponse);
        createCustomerPresenter.createCustomerRepository(refEq(customer));
    }*/

}
