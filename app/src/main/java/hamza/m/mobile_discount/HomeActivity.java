package hamza.m.mobile_discount;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private String userType;

    RadioButton byname,bytype;
    EditText inputSearch;
    RadioGroup rg;

    ArrayList<ListProductData> list;
    ArrayList<ListProductData> dupliacatelist;

    //firebase real time db values
    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;


    //Location
//    private GoogleMap mMap;
    //play service...
    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

//    private LocationRequest mlocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLAstLocation;


    //arrays for data filling
    /*int[] product_image = {
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
    "Food","Food","Food","Health& Beauty","Health& Beauty","Health& Beauty","Home Appliances","Health& Beauty","Food"};*/
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

        inputSearch = (EditText)findViewById(R.id.inputSearch);
        addform_image = (ImageView) findViewById(R.id.addformimage);
        byname = (RadioButton) findViewById(R.id.byname);
        bytype = (RadioButton) findViewById(R.id.byType);
        rg = (RadioGroup) findViewById(R.id.selecttype_r);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                dosearch();
            }
        });

        //sharedd preferences to check uyser type
        sharedPreferences = getSharedPreferences("selecter",MODE_PRIVATE);
        userType = sharedPreferences.getString("userType","");
        if(userType.equals("user"))
        {
            addform_image.setVisibility(View.GONE);

        }
        else if(userType.equals("shopkeeper")){

            addform_image.setVisibility(View.VISIBLE);
        }


        //
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
            Intent i = new Intent(HomeActivity.this,VerifyScreen.class);
            startActivity(i);
            finish();

        }



//        hello= findViewById(R.id.hellooo);


        //inityializing firerbase objects here !!!!!
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("discount-mobile");

        //accessing values of firebase here !!!!!


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                dupliacatelist = new ArrayList<>();
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
                     String lat = shopkeeperData.getLat();
                     String lng = shopkeeperData.getLng();

                     //adding data to array list of data list model class

                    listProductData.setpName(PName);
                    listProductData.setpDesc(PDescription);
                    listProductData.setpType(PType);
                    listProductData.setpPrice(PPrice);
                    listProductData.setPdiscount(PDiscount);
                    listProductData.setpImage(PImage);
                    listProductData.setpShop(pShop);
                    listProductData.setLat(lat);
                    listProductData.setLng(lng);

                    list.add(listProductData);
                    dupliacatelist.add(listProductData);


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
      //search functionality
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //   Student.this.arrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // To do auto generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // To do auto generated method stub
               dosearch();
            }
            // arrayAdapter.getFilter().filter(text);

                /*for(int i=0;i<array_numbers1.size();i++){
                    array_numbers.equals(array_numbers1.get(i));

                }
*/

        });


    }

    private void dosearch() {

        String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());

        //  array_list.clear();
        if (text.length() == 0) {
            list.clear();
            for (ListProductData wp : dupliacatelist) {

                list.add(wp);

            }


        } else {
            list.clear();
            for (ListProductData wp : dupliacatelist) {
                if(byname.isChecked())
                {

                    if (wp.getpName().toLowerCase(Locale.getDefault()).contains(text)) {
                        list.add(wp);
                    }
                }
                else if(bytype.isChecked())
                {
                    if (wp.getpType().toLowerCase(Locale.getDefault()).contains(text)) {
                        list.add(wp);
                    }

                }

            }
        }
        product_adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();
        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.name_logout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            Intent i = new Intent(HomeActivity.this,SelectUserScreen.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

 /*   LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    double longitude = location.getLongitude();
    double latitude = location.getLatitude();*/

  /*  private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request run time permission...
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION

            }, MY_PERMISSION_REQUEST_CODE);

        } else

        {
            if (checkplayservices()) {
//                buildGoogleApiClient();
//                createLocationRequest();
//                displayLocation();
            }
        }
    }*/

    //search functioanlity..


}
