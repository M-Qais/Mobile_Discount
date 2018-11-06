package qais.mirza.mobile_discount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import qais.mirza.adapters.Products_home_adapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    private TextView hello;
     RecyclerView products_recyclerView;
    RecyclerView.Adapter product_adapter;
    RecyclerView.LayoutManager product_layoutManager;

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


//        hello= findViewById(R.id.hellooo);



        products_recyclerView = findViewById(R.id.productsrecyclerview);
        product_layoutManager = new LinearLayoutManager(HomeActivity.this);
        products_recyclerView.setLayoutManager(product_layoutManager);
        products_recyclerView.setHasFixedSize(true);

        product_adapter =  new Products_home_adapter(this,product_image,product_name,product_description,product_type);
        products_recyclerView.setAdapter(product_adapter);
    }






}
