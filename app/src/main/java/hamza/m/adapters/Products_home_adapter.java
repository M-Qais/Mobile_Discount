package hamza.m.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.AccessControlContext;

import hamza.m.mobile_discount.R;


public class Products_home_adapter extends RecyclerView.Adapter<DuaViewHolder> {
    private Context context;
    int[] product_image;
    String[] product_name;
    String[] product_description;
    String[] product_type;

    public Products_home_adapter(Context context,int[] duaname, String[] dauarbi, String[] dua_english, String[] dua_urdu) {
       this.context = context;
        this.product_image = duaname;
        this.product_name = dauarbi;
        this.product_description = dua_english;
        this.product_type = dua_urdu;
    }

    @NonNull
    @Override
    public DuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.product_row_layout, parent, false);
        DuaViewHolder duaViewHolder = new DuaViewHolder(v);
        return duaViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DuaViewHolder holder, int position) {

        holder.product_image.setImageResource(product_image[position]);
        holder.product_name.setText(product_name[position]);
        holder.product_desc.setText(product_description[position]);
        holder.product_type.setText(product_type[position]);


    }

    @Override
    public int getItemCount() {
        return product_name.length;
    }

}

class DuaViewHolder extends RecyclerView.ViewHolder {
    ImageView product_image;
    TextView product_name;
    TextView product_desc;
    TextView product_type;

    public DuaViewHolder(View itemView) {
        super(itemView);

        product_image= itemView.findViewById(R.id.product_imageView);
        product_name = itemView.findViewById(R.id.product_name);
        product_desc = itemView.findViewById(R.id.product_description);
        product_type = itemView.findViewById(R.id.product_type);

    }

}
