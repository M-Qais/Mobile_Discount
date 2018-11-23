package hamza.m.mobile_discount;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hamza.m.Model.ListProductData;
import hamza.m.Model.ShopkeeperData;
import hamza.m.adapters.Products_home_adapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    private TextView hello;
     RecyclerView products_recyclerView;
    RecyclerView.Adapter product_adapter;
    RecyclerView.LayoutManager product_layoutManager;
    ImageView addform_image ;

    ArrayList<ListProductData> list;

    //firebase real time db values
    FirebaseDatabase database;
    DatabaseReference myRef;

    //arrays for data filling
    int[] product_image = {
            R.drawable.food, R.drawable.beauty, R.drawable.health, R.drawable.health_2,R.drawable.health_3,R.drawable.home,R.drawable.home_2,
            R.drawable.home_3,R.drawable.food, R.drawable.food,R.drawable.beauty,R.drawable.health_2,R.drawable.health_3,R.drawable.home_3,
            R.drawable.health,R.drawable.food,
    };
    String[] product_name= {
            "Juices& Jaguana", "Bouful_Beauty", "GetFit", "Care","Skincare","Appliances","Stationary_Things",
            "Groceries", "Food_To_Do","YummTaste","OmlyYouBeauty","Acne","MattersYou","ElectricAppliances",
            "hairCare","Johny&jugnu",

    } ;
    String[] product_description = {"This product is for food, The delicious and Traditional food here",
            "This product is for health and beauty which cares about your life,",
            "This product is for health that takes you towards the health",
            "This product is for health which makes sure about your helath",
            "This product is for health which makes you fit",
         "This product is for home appliances",
            "This product is for home appliances that takes you to towards stationary",
    "This product is for Home appliances adn groceries",
    "This product is for food some food that makes you feel good",
    "This product is for food the food that is yummmiest and Affordable",
    "This product is for beauty that take cares aboutr you",
    "This product is for helth that takes about your skin problems",
    "This product is for health that examines you like every reactions in body, like blood pressure,suger",
    "This product is for electric appliances , the best and cheap for you",
    "This product is for helath that takes care about your hairs its the solution for every hairfall",
    "This product is for food the delicious and yummm fast food point"};

    String[] product_type = {"Food","Health& Beauty","Health& Beauty","Health& Beauty","Health& Beauty","Home Appliances","Home Appliances","Home Appliances",
    "Food","Food","Food","Health& Beauty","Health& Beauty","Health& Beauty","Home Appliances","Health& Beauty","Food"};
    //arrays thats it


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CaviarDreams.ttf").
                        setFontAttrId(R.attr.fontPath).
                        build());
        setContentView(R.layout.activity_home);
        addform_image = (ImageView) findViewById(R.id.addformimage);



//        hello= findViewById(R.id.hellooo);


        //inityializing firerbase objects here !!!!!
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("discount-mobile");

        //accessing values of firebase here !!!!!


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    ShopkeeperData shopkeeperData = dataSnapshot1.getValue(ShopkeeperData.class);
                    ListProductData listProductData = new ListProductData();
                     String PName = shopkeeperData.getpName();
                     String PDescription = shopkeeperData.getpDesc();
                     String PType = shopkeeperData.getpType();
                     String PPrice = shopkeeperData.getpPrice();
                     String PDiscount = shopkeeperData.getPdiscount();
                     String PImage = shopkeeperData.getpImage();
                     String pShop = shopkeeperData.getpShop();

                     //adding data to array list of data list model class

                    listProductData.setpName(PName);
                    listProductData.setpDesc(PDescription);
                    listProductData.setpType(PType);
                    listProductData.setpPrice(PPrice);
                    listProductData.setPdiscount(PDiscount);
                    listProductData.setpImage(PImage);
                    listProductData.setpShop(pShop);

                    list.add(listProductData);


                    products_recyclerView = findViewById(R.id.productsrecyclerview);


                    product_layoutManager = new LinearLayoutManager(HomeActivity.this);
                    products_recyclerView.setLayoutManager(product_layoutManager);
                    products_recyclerView.setHasFixedSize(true);
                    products_recyclerView.setItemAnimator( new DefaultItemAnimator());

                    product_adapter =  new Products_home_adapter(HomeActivity.this,list);

                    products_recyclerView.setAdapter(product_adapter);

//                    products_recyclerView.notify();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*products_recyclerView = findViewById(R.id.productsrecyclerview);
        product_layoutManager = new LinearLayoutManager(HomeActivity.this);
        products_recyclerView.setLayoutManager(product_layoutManager);
        products_recyclerView.setHasFixedSize(true);

        product_adapter =  new Products_home_adapter(this,product_image,product_name,product_description,product_type);
        products_recyclerView.setAdapter(product_adapter);*/


        addform_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addform = new Intent (HomeActivity.this,Shopkeeperform.class);
                startActivity(addform);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(HomeActivity.this,SelectUserScreen.class);
        startActivity(i);
       finish();
    }
}
