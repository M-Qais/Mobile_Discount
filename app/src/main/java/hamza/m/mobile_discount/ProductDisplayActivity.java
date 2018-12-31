package hamza.m.mobile_discount;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static hamza.m.adapters.Products_home_adapter.URL;

public class ProductDisplayActivity extends AppCompatActivity implements OnMapReadyCallback {

    PhotoView photoView;
    TextView name, description;
    private List<Marker> markers;
    private GoogleMap mMap;

    String lat, lng;

    String cu_lat, cu_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        photoView = (PhotoView) findViewById(R.id.imgg_display);
        name = (TextView) findViewById(R.id.imag_prod_name);
        description = (TextView) findViewById(R.id.image_prod_desc);

        markers = new ArrayList<>();

        Intent i = getIntent();
        final String imageurl = i.getStringExtra(URL);
        String nameget = i.getStringExtra("p_name");
        String descget = i.getStringExtra("p_desc");
        lat = i.getStringExtra("lat");
        lng = i.getStringExtra("lng");

        SharedPreferences sharedPreferences = getSharedPreferences("location", MODE_PRIVATE);
        cu_lat = sharedPreferences.getString("lat", "");
        cu_long = sharedPreferences.getString("lng", "");


        Picasso.get().load(imageurl).fit().centerInside().into(photoView);
        name.setText("Product Name : "+nameget);
        description.setText("Product Description : "+descget);


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(), requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


            // Getting GoogleMap object from the fragment
            fm.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // LatLng object to store user input coordinates

        mMap = googleMap;

        Log.e("lat", String.valueOf(lat));
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                .title("Product Here"));

        Marker m1 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.4014273, 74.2110784))
                .title("Shafeeq Alpiances")
                .snippet("Home Appliances"));


        Marker m2 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.402890, 74.212234))
                .anchor(0.5f, 0.5f)
                .title("AR Foods")
                .snippet("Food"));


        Marker m3 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.396389 ,74.205333))
                .anchor(0.5f, 0.5f)
                .title("Bookshop")
                .snippet("Stationary"));
        Marker m4 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.386382 ,74.225356))
                .anchor(0.5f, 0.5f)
                .title("Shoe&Tie")
                .snippet("Shoe"));
        Marker m5 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.376379 ,74.235678))
                .anchor(0.5f, 0.5f)
                .title("Cars")
                .snippet("Cars"));
        Marker m6 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.420087 ,74.185672))
                .anchor(0.5f, 0.5f)
                .title("Bookshop")
                .snippet("Stationary"));
        Marker m7 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.366963 ,74.216778))
                .anchor(0.5f, 0.5f)
                .title("HealthCare")
                .snippet("Food& health"));
        Marker m8 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.376665 ,74.224556))
                .anchor(0.5f, 0.5f)
                .title("Johnny & Juggnu")
                .snippet("Food"));
        Marker m9 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.125679,74.345356))
                .anchor(0.5f, 0.5f)
                .title("YourClothes")
                .snippet("Clothes"));
        Marker m10 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.338456 ,74.205446))
                .anchor(0.5f, 0.5f)
                .title("SkinCare")
                .snippet("Health And Beauty"));


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        markers.add(marker);
        markers.add(m1);
        markers.add(m2);
        markers.add(m3);
        markers.add(m4);
        markers.add(m5);
        markers.add(m6);
        markers.add(m7);
        markers.add(m8);
        markers.add(m9);
        markers.add(m10);


        for (Marker m : markers) {
            builder.include(m.getPosition());
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 12.0f));

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(cu_lat),Double.parseDouble(cu_long)))
                .radius(250)
                .strokeColor(android.R.color.white)
                .fillColor(R.color.owsome));

        LatLng location = new LatLng(Double.parseDouble(cu_lat), Double.parseDouble(cu_long));

        circle.setCenter(location);


    }

}
