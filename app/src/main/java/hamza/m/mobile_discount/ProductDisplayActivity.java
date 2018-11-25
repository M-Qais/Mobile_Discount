package hamza.m.mobile_discount;

import android.app.Dialog;
import android.content.Intent;
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

        Picasso.get().load(imageurl).fit().centerInside().into(photoView);
        name.setText(nameget);
        description.setText(descget);


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
                .title("Marker"));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        markers.add(marker);

        for (Marker m : markers) {
            builder.include(m.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 12.0f));

        /*Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng)))
                .radius(1000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));*/
        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng)))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
//        circle.setCenter();
    }

}
