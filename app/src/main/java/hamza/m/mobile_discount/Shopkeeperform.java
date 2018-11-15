package hamza.m.mobile_discount;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Shopkeeperform extends AppCompatActivity {


    EditText p_name,p_type,p_desc,p_price,p_discount;
    Button saveformdata;
    RelativeLayout parentlayout;

    //initializing shopkeperdata class
    ShopkeeperData shopkeeperData;

    //firebase initialization
    FirebaseDatabase shopkeeperdatabse;
    DatabaseReference db_sk_reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperform);

        //initializing the fields here

        p_name = (EditText)findViewById(R.id.product_name_sk);
        p_type = (EditText)findViewById(R.id.Product_type_sk);
        p_desc = (EditText)findViewById(R.id.product_description_sk);
        p_price = (EditText)findViewById(R.id.product_price_sk);
        p_discount = (EditText)findViewById(R.id.product_disocunt_sk);

        saveformdata = (Button)findViewById(R.id.saveformsk_btn);
        parentlayout =(RelativeLayout) findViewById(R.id.relativelayoutOfForm);
        shopkeeperData = new ShopkeeperData();

        shopkeeperdatabse =FirebaseDatabase.getInstance();
        db_sk_reference =shopkeeperdatabse.getReference("discount-mobile");



        saveformdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_sk_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        //passing object to the firbase for storage
                        db_sk_reference.child("products_data_shopkeeper").setValue(shopkeeperData);
                        Snackbar.make(parentlayout,"Product Inserted Successfully",Snackbar.LENGTH_LONG)

                                .setActionTextColor(Color.GREEN)
                                .show();
                      /*  p_name.getText().clear();
                        p_desc.getText().clear();
                        p_type.getText().clear();
                        p_price.getText().clear();
                        p_discount.getText().clear();*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void getValues()
    {
       shopkeeperData.setpName(p_name.getText().toString());
       shopkeeperData.setpType(p_type.getText().toString());
       shopkeeperData.setpDesc(p_desc.getText().toString());
       shopkeeperData.setpPrice(p_price.getText().toString());
       shopkeeperData.setPdiscount(p_discount.getText().toString());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
