package co.com.etn.examen.views.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.etn.examen.R;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Product;

/**
 * Created by jose.cardenas on 03/10/2017.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> customersList;
    private Activity context;
    private Customer customer;
    private TextView name;

    public CustomerAdapter(Activity context, int resource, ArrayList<Customer> customersList) {
        super(context, resource, customersList);
        this.context = context;
        this.customersList = customersList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        this.customer = this.customersList.get(position);

        /*Log.w("myApp","name:"+customer.getName());
        Log.w("myApp","surname:"+customer.getSurname());
        if( customer.getPhoneList().size()>0 ) {
            Log.w("myApp", "phone type:" + customer.getPhoneList().get(0).getDescription());
            Log.w("myApp", "phone number:" + customer.getPhoneList().get(0).getNumber());
            Log.w("myApp", "location type:" + customer.getPhoneList().get(0).getLocation().getType());
            Log.w("myApp", "location latitud:" + customer.getPhoneList().get(0).getLocation().getCoordinates()[0]);
            Log.w("myApp", "location longitud:" + customer.getPhoneList().get(0).getLocation().getCoordinates()[1]);
        }*/

        loadView(convertView);
        name.setText(customer.getName());
        return convertView;
    }

    private void loadView(View convertView){
        name = (TextView) convertView.findViewById(R.id.item_name_product);
    }
}
