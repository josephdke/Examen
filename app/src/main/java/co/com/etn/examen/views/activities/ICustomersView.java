package co.com.etn.examen.views.activities;

import java.util.ArrayList;

import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Product;
import co.com.etn.examen.views.IBaseView;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public interface ICustomersView extends IBaseView {

    void showCustomersList(ArrayList<Customer> customersList );

    void showAlertDialogInternet(int title, int message);

    void showAlertError(int title, String message);

}