package hamza.m.adapters;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnSuccessListener;
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
import hamza.m.mobile_discount.Shopkeeperform;


public class Products_home_adapter extends RecyclerView.Adapter<DuaViewHolder> {
    private Context context;
    public static final String URL ="url";

    ArrayList<ListProductData> listdata;
    FirebaseAuth mAuth;
    DatabaseReference mUserDatabase;
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
        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("discount-mobile");
        return duaViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DuaViewHolder holder, final int position) {
       final ListProductData listProductData = listdata.get(position);

//         holder.product_image.setImageResource(product_image[position]);
        holder.product_name.setText("Product Name:"+listProductData.getpName());
//        holder.product_desc.setText(listProductData.getpDesc());
        holder.product_type.setText("Product Type:"+listProductData.getpType());
        holder.product_price.setText("Product Price: "+listProductData.getpPrice());
        holder.product_discount.setText("Product Discount: "+listProductData.getPdiscount() + "%");
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
             //pass the 'context' here
             AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
             alertDialog.setTitle("Are you sure ");
             alertDialog.setMessage("Delete Item ");
             alertDialog.setCancelable(true);
             alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();

                     Log.e("key", listProductData.getKey());

                     Intent intent = new Intent(context, Shopkeeperform.class);
                     intent.putExtra("image", listProductData.getpImage());
                     intent.putExtra("shopname", listProductData.getpShop());
                     intent.putExtra("name", listProductData.getpName());
                     intent.putExtra("description", listProductData.getpDesc());
                     intent.putExtra("type", listProductData.getpType());
                     intent.putExtra("price", listProductData.getpPrice());
                     intent.putExtra("discount", listProductData.getPdiscount());
                     intent.putExtra("key", listProductData.getKey());
                     context.startActivity(intent);

                 }
             });
             final String id = mAuth.getCurrentUser().getUid();

             alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     // DO SOMETHING HERE


                         mUserDatabase.child(listProductData.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 listdata.remove(position);
                                 notifyItemRemoved(position);
                                 notifyItemRangeChanged(position,listdata.size());
                                 Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                             }
                         });

                    /* String itemLabel = String.valueOf(listdata.get(position));
                     listdata.remove(position);
                     notifyItemRemoved(position);
                     notifyItemRangeChanged(position,listdata.size());
                     Toast.makeText(context," Item Removed : " ,Toast.LENGTH_SHORT).show();*/


                 }
             });

             if(listProductData.getId().equals(id)) {

                 AlertDialog dialog = alertDialog.create();
                 dialog.show();
             }
             else {
                 Toast.makeText(context, "You cannout delete or update item", Toast.LENGTH_LONG).show();
             }
//             adapter.getRef(position).remove();
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
