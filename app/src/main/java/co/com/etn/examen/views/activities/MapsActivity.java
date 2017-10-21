package co.com.etn.examen.views.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import co.com.etn.examen.R;
import co.com.etn.examen.helper.Constants;
import co.com.etn.examen.model.Customer;
import co.com.etn.examen.model.Phone;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Customer> customersList;
    public static final String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        customersList = (ArrayList<Customer>)getIntent().getExtras().get(Constants.EXTRA_CUSTOMERS);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createMarkers();
        changeStateControls();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void createMarkers() {
        BitmapDescriptor bitmap = bitmapDescriptorFromVector(this,R.drawable.ic_assistant_photo_black_24dp);
        ArrayList<LatLng> markers = new ArrayList<>();
        for( int i=0; i<customersList.size(); i++ ){
            Customer customer = customersList.get( i );
            for( int j=0; j<customer.getPhoneList().size(); j++ ){
                Phone phone = customer.getPhoneList().get(j);
                LatLng marker = new LatLng(phone.getLocation().getCoordinates()[0], phone.getLocation().getCoordinates()[1]);
                mMap.addMarker(new MarkerOptions().position(marker).title( phone.getDescription() ).icon(bitmap));
                markers.add( marker );
            }
        }

        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(markers.get(0),11) );
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(markers.get(0))      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(80)                   // Sets the tilt of the camera to 30 degrees
                .build();

        mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );


        calculateRoads( markers );
    }

    private void calculateRoads(ArrayList<LatLng> markers) {
        RoutingListener routingListener = new RoutingListener(){

            @Override
            public void onRoutingFailure(RouteException e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onRoutingStart() {
                Log.i(TAG,"Iniciando");
            }

            @Override
            public void onRoutingSuccess(ArrayList<Route> routes, int shortestRouteIndex ) {
                //ArrayList polyLines = new ArrayList();
                Log.w("myApp","d:"+routes.size());
                for( int i=0; i<routes.size(); i++ ){
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.color( ContextCompat.getColor( getApplicationContext(), R.color.colorAccent ) );
                    polylineOptions.width(10);
                    polylineOptions.addAll( routes.get(i).getPoints() );
                    Polyline polyline = mMap.addPolyline(polylineOptions);
                    //polyLines.add( polyline );
                    Toast.makeText(MapsActivity.this, routes.get(i).getDistanceText()+" "+routes.get(i).getDurationText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRoutingCancelled() {
                Log.w(TAG,"Cancelando");
            }
        };

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(markers)
                .key(getString(R.string.google_maps_key))
                .optimize(true)
                .alternativeRoutes(true)
                .withListener(routingListener)
                .build();
        routing.execute();

        centerJustBounds(markers);
    }

    private void centerJustBounds( ArrayList<LatLng> markers ) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for( int i=0; i<markers.size(); i++ ){
            builder.include( markers.get(i) );
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,50);
        //mMap.animateCamera( cameraUpdate );
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorId ) {
        Drawable vectorDrawable = ContextCompat.getDrawable( context, vectorId );
        vectorDrawable.setBounds( 0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight() );
        Bitmap bitmap = Bitmap.createBitmap( vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );
        vectorDrawable.draw( canvas );
        return BitmapDescriptorFactory.fromBitmap( bitmap );
    }

    private void changeStateControls() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

}
