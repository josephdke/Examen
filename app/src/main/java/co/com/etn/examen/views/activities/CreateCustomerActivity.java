package co.com.etn.examen.views.activities;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.examen.R;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Location;
import co.com.etn.examen.model.Phone;
import co.com.etn.examen.presenter.CreateCustomerPresenter;
import co.com.etn.examen.repository.CustomerRepository;
import co.com.etn.examen.views.BaseActivity;

public class CreateCustomerActivity extends BaseActivity<CreateCustomerPresenter> implements ICreateCustomerView, TextWatcher {

    private ContentLoadingProgressBar progress;
    private EditText create_customer_edittext_name;
    private EditText create_customer_edittext_surname;
    private EditText create_customer_edittext_phone_type;
    private EditText create_customer_edittext_phone_number;
    private EditText create_customer_edittext_latitud;
    private EditText create_customer_edittext_longitud;
    private Button product_btnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        setPresenter(new CreateCustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());

        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress.hide();

        loadViews();

        product_btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer();
                customer.setName(create_customer_edittext_name.getText().toString());
                customer.setSurname(create_customer_edittext_surname.getText().toString());
                customer.setPhoneList(new ArrayList<Phone>());
                customer.getPhoneList().add(new Phone());
                customer.getPhoneList().get(0).setDescription(create_customer_edittext_phone_type.getText().toString());
                customer.getPhoneList().get(0).setNumber(create_customer_edittext_phone_number.getText().toString());
                customer.getPhoneList().get(0).setLocation(new Location());
                customer.getPhoneList().get(0).getLocation().setType(customer.getPhoneList().get(0).getDescription());
                customer.getPhoneList().get(0).getLocation().setCoordinates(new double[]{
                        Double.parseDouble(create_customer_edittext_latitud.getText().toString()),
                        Double.parseDouble(create_customer_edittext_longitud.getText().toString())
                });
                getPresenter().createNewCustomer(customer);
            }
        });

    }

    private void loadViews() {
        create_customer_edittext_name = (EditText) findViewById(R.id.create_customer_edittext_name);
        create_customer_edittext_name.addTextChangedListener(this);
        create_customer_edittext_surname = (EditText) findViewById(R.id.create_customer_edittext_surname);
        create_customer_edittext_surname.addTextChangedListener(this);
        create_customer_edittext_phone_type = (EditText) findViewById(R.id.create_customer_edittext_phone_type);
        create_customer_edittext_phone_type.addTextChangedListener(this);
        create_customer_edittext_phone_number = (EditText) findViewById(R.id.create_customer_edittext_phone_number);
        create_customer_edittext_phone_number.addTextChangedListener(this);
        create_customer_edittext_latitud = (EditText) findViewById(R.id.create_customer_edittext_latitud);
        create_customer_edittext_latitud.addTextChangedListener(this);
        create_customer_edittext_longitud = (EditText) findViewById(R.id.create_customer_edittext_longitud);
        create_customer_edittext_longitud.addTextChangedListener(this);
        product_btnCreate = (Button) findViewById(R.id.product_btnCreate);
    }

    @Override
    public void showAlertInternet(final int title, final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateCustomerActivity.this, R.string.validate_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!create_customer_edittext_name.getText().toString().trim().isEmpty() && !create_customer_edittext_surname.getText().toString().trim().isEmpty() &&
                !create_customer_edittext_phone_type.getText().toString().trim().isEmpty() && !create_customer_edittext_phone_number.getText().toString().trim().isEmpty() &&
                !create_customer_edittext_latitud.getText().toString().trim().isEmpty() && !create_customer_edittext_longitud.getText().toString().trim().isEmpty()) {
            product_btnCreate.setBackgroundResource(R.color.colorPrimary);
            product_btnCreate.setEnabled(true);
        }else{
            product_btnCreate.setBackgroundResource(R.color.colorGray);
            product_btnCreate.setEnabled(false);
        }
    }

    @Override
    public void showResultCreateNewConsumer(final boolean isCreated) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.hide();
                if(isCreated){
                    Toast.makeText(CreateCustomerActivity.this, getResources().getString(R.string.okResponseCreateConsumer), Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(CreateCustomerActivity.this, getResources().getString(R.string.errResponseCreateConsumer), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
