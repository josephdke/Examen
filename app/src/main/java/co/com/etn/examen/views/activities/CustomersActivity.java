package co.com.etn.examen.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import co.com.etn.examen.R;
import co.com.etn.examen.helper.Constants;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.presenter.CustomersPresenter;
import co.com.etn.examen.repository.CustomerRepository;
import co.com.etn.examen.views.BaseActivity;
import co.com.etn.examen.views.adapter.CustomerAdapter;

public class CustomersActivity extends BaseActivity<CustomersPresenter> implements ICustomersView {

    ArrayList<Customer> customersList;
    private ListView customersListView;
    private CustomerAdapter customerAdapter;
    private ContentLoadingProgressBar progress;
    private FloatingActionButton buttonLaunchCreate;
    private FloatingActionButton buttonLaunchMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        setPresenter(new CustomersPresenter( new CustomerRepository() ));
        getPresenter().inject(this, getValidateInternet());

        customersListView = (ListView) findViewById(R.id.customers_listView);
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress.show();
        getPresenter().getListCustomers();

        initListeners();
    }

    private void initListeners() {
        buttonLaunchCreate = (FloatingActionButton) findViewById(R.id.fab_launch_createproduct);
        buttonLaunchCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this, CreateCustomerActivity.class);
                startActivity(intent);
            }
        });
        buttonLaunchMap = (FloatingActionButton) findViewById(R.id.fab_launch_map);
        buttonLaunchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this, MapsActivity.class);
                intent.putExtra(Constants.EXTRA_CUSTOMERS,customersList);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.show();
        getPresenter().getListCustomers();
    }

    @Override
    public void showCustomersList(final ArrayList<Customer> customersList) {
        this.customersList = customersList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.hide();
                callAdapter(customersList);
            }
        });
    }

    @Override
    public void showAlertDialogInternet(final int title, final int message) {
        showAlertDialog(title, getResources().getString(message));
    }

    @Override
    public void showAlertError(int title, String message) {
        showAlertDialog(title, message);
    }

    private void showAlertDialog(final int title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().getListCustomers();
                    }
                }, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    private void callAdapter( final ArrayList<Customer> customersList ) {
        customerAdapter =  new CustomerAdapter(this, R.id.customers_listView, customersList);
        customersListView.setAdapter(customerAdapter);
    }

}
