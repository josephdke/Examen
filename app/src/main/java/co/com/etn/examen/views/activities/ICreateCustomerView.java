package co.com.etn.examen.views.activities;

import co.com.etn.examen.views.IBaseView;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public interface ICreateCustomerView extends IBaseView {

    void showAlertInternet(int title, int message);

    public void showResultCreateNewConsumer(boolean isCreated);
    
}
