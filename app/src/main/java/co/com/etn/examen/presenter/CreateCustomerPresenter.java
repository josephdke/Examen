package co.com.etn.examen.presenter;

import co.com.etn.examen.R;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Product;
import co.com.etn.examen.repository.ICustomerRepository;
import co.com.etn.examen.views.activities.ICreateCustomerView;
import retrofit.RetrofitError;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class CreateCustomerPresenter extends BasePresenter<ICreateCustomerView> {

    private ICustomerRepository customerRepository;

    public CreateCustomerPresenter(ICustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public void createNewCustomer(Customer customer) {
        if (getValidateInternet().isConnected()){
            createThreadCreateCustomer(customer);
        }else{
            getView().showAlertInternet(R.string.error, R.string.validate_internet);
        }
    }

    public void createThreadCreateCustomer(final Customer customer) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createNewCustomerRepository(customer);
            }
        });
        thread.start();
    }

    private void createNewCustomerRepository(Customer customer){
        try{
            customerRepository.createCustomer(customer);
            getView().showResultCreateNewConsumer(true);
        }catch (RetrofitError retrofitError){
            retrofitError.printStackTrace();
            getView().showResultCreateNewConsumer(false);
        }
    }

}
