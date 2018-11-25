package hamza.m.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

import hamza.m.Model.ListProductData;
import hamza.m.mobile_discount.ProductDisplayActivity;
import hamza.m.mobile_discount.R;


public class Products_home_adapter extends RecyclerView.Adapter<DuaViewHolder> {
    private Context context;
    public static final String URL ="url";

    ArrayList<ListProductData> listdata;
    FirebaseAuth mAuth;

    int[] product_image;
    String[] product_name;
    String[] product_description;
    String[] product_type;

  /*  public Products_home_adapter(Context context,int[] duaname, String[] dauarbi, String[] dua_english, String[] dua_urdu) {
       this.context = context;
        this.product_image = duaname;
        this.product_name = dauarbi;
        this.product_description = dua_english;
        this.product_type = dua_urdu;
    }*/
  public Products_home_adapter (Context context,ArrayList<ListProductData> listProductData)
  {
      this.listdata = listProductData;
      this.context = context;
  }

    @NonNull
    @Override
    public DuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View v = LayoutInflater.from(context).inflate(R.layout.shopkeeperaddeddata, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopkeeperaddeddata,parent,false);
        DuaViewHolder duaViewHolder = new DuaViewHolder(view);

        return duaViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DuaViewHolder holder, final int position) {
       final ListProductData listProductData = listdata.get(position);

//         holder.product_image.setImageResource(product_image[position]);
        holder.product_name.setText(listProductData.getpName());
//        holder.product_desc.setText(listProductData.getpDesc());
        holder.product_type.setText(listProductData.getpType());
        holder.product_price.setText(listProductData.getpPrice());
        holder.product_discount.setText(listProductData.getPdiscount() + "%");
        Picasso.get()
               .load(listProductData.getpImage())
               .into(holder.product_image);

        holder.mcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ProductDisplayActivity.class);
                i.putExtra(URL,listProductData.getpImage());
                i.putExtra("p_name",listProductData.getpDesc());
                i.putExtra("p_desc",listProductData.getpName());
                i.putExtra("lat",listProductData.getLat());
                i.putExtra("lng",listProductData.getLng());
                context.startActivity(i);
            }
        });
     /*   holder.mcardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mAuth=FirebaseAuth.getInstance();
                String valueid = mAuth.getCurrentUser().getUid();
//                String value = FirebaseDatabase.getInstance().getReference().getRef("discount-mobile").
                DatabaseReference s_k_product = FirebaseDatabase.getInstance().getReference("discount-mobile").child(valueid);
                s_k_product.removeValue();
                Toast.makeText(context, "Item has been removed Successfully !", Toast.LENGTH_LONG).show();
                return true;
            }
        });*/
     holder.mcardView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
//             adapter.getRef(position).remove();
             String itemLabel = String.valueOf(listdata.get(position));
             listdata.remove(position);
             notifyItemRemoved(position);
             notifyItemRangeChanged(position,listdata.size());
             Toast.makeText(context," Item Removed : " ,Toast.LENGTH_SHORT).show();
             return true;
         }
     });




       /* Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
//        });*/
//        builder.build().load(listProductData.getpImage()).into(holder.product_image);

       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listdata.remove(position);
                return true;
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


}

class DuaViewHolder extends RecyclerView.ViewHolder {
    private View mview;
    ImageView product_image;
    TextView product_name;
    TextView product_type;
    TextView product_price;
    TextView product_discount;
    CardView mcardView;

    public DuaViewHolder(View itemView) {
        super(itemView);
        mview = itemView;

      product_image = mview.findViewById(R.id.product_imageView);
        product_name = mview.findViewById(R.id.product_name_s);
//        product_desc = itemView.findViewById(R.id.product_description);
        product_type = mview.findViewById(R.id.product_type_s);
        product_price = mview.findViewById(R.id.product_price_s);
        product_discount = mview.findViewById(R.id.product_discount_s);
        mcardView = mview.findViewById(R.id.product_homeclick);

    }

}
