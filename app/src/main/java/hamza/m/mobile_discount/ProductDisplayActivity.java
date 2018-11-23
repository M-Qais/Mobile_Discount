package hamza.m.mobile_discount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import static hamza.m.adapters.Products_home_adapter.URL;

public class ProductDisplayActivity extends AppCompatActivity {

    PhotoView photoView;
    TextView name,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        photoView = (PhotoView) findViewById(R.id.imgg_display);
        name = (TextView) findViewById(R.id.imag_prod_name);
        description = (TextView) findViewById(R.id.image_prod_desc);

        Intent i = getIntent();
        final String imageurl = i.getStringExtra(URL);
        String nameget = i.getStringExtra("p_name");
        String descget = i.getStringExtra("p_desc");

        Picasso.get().load(imageurl).fit().centerInside().into(photoView);
        name.setText(nameget);
        description.setText(descget);

    }
}
