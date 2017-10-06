package co.com.etn.examen.presenter;

import java.util.ArrayList;

import co.com.etn.examen.R;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.repository.CustomerRepository;
import co.com.etn.examen.repository.MapperError;
import co.com.etn.examen.repository.RepositoryError;
import co.com.etn.examen.views.activities.ICustomersView;
import retrofit.RetrofitError;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class CustomersPresenter extends BasePresenter<ICustomersView> {

    private CustomerRepository customerRepository;

    public CustomersPresenter( CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;
    }

    public void getListCustomers() {
        if (getValidateInternet().isConnected()) {
            createThreadGetListCostumers();
        } else {
            getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
        }
    }

    private void createThreadGetListCostumers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getListCustomersRepository();
            }
        });
        thread.start();
    }

    private void getListCustomersRepository() {
        try {
            ArrayList<Customer> customersList = customerRepository.getCustomersList();
            getView().showCustomersList(customersList);
        } catch (RetrofitError retrofitError) {
            RepositoryError repositoryError = MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
            getView().showAlertError(R.string.error, repositoryError.getMessage());
        }
    }

}
