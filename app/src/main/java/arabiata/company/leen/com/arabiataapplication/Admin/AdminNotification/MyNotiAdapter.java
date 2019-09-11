package arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.MyNotificationsModel;
import arabiata.company.leen.com.arabiataapplication.R;

import static android.content.Context.MODE_PRIVATE;

class MyNotiAdapter extends RecyclerView.Adapter<MyNotiAdapter.MyViewHolder> {

    private Context context;
    private List<MyNotificationsModel> cartList;

    SharedPreferences sharedpreferences;
    AdminNotificationInterface notificationsView;

    public MyNotiAdapter(Context context, List<MyNotificationsModel> cartList, AdminNotificationInterface notificationsView) {
        this.context = context;
        this.cartList = cartList;
        this.notificationsView = notificationsView;
        sharedpreferences = context.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mynotifications_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyNotificationsModel recipe = cartList.get(position);
        //final String id_ = cartList.get(holder.getAdapterPosition()).getAdId();
        //  Log.i("TAG", "Stringid_: " + id_);


        holder.timestamp.setText(recipe.getCreatedAt());
        if (recipe.getBatteryLevel().equals("null")) {
            holder.user_phone.setText(recipe.getPhone());

            holder.name.setText(recipe.getName());
            if (recipe.getHappy().equals("1")) {
                holder.ivstatus.setImageDrawable((context.getResources().getDrawable(R.drawable.happy)));
            } else {
                holder.ivstatus.setImageDrawable((context.getResources().getDrawable(R.drawable.unhappy)));
            }
            holder.tvbranch.setText(recipe.getBranch());
            holder.ivstatus.setVisibility(View.VISIBLE);

        } else {
            holder.name.setText(context.getResources().getString(R.string.batterylevel) + recipe.getBatteryLevel() + "%");
            holder.ivstatus.setVisibility(View.GONE);
            holder.tvbranch.setText(recipe.getBatteryBranch());
            holder.user_phone.setVisibility(View.GONE);

        }

//holder.tvbranch.setText(recipe.getBranch());


    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + cartList.size());
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView timestamp;
        public TextView user_phone, tvbranch;
        public ImageView ivstatus;
        public RelativeLayout relativeLayoutContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvnoti);
            timestamp = itemView.findViewById(R.id.timestamp_noti);
            user_phone = itemView.findViewById(R.id.tvuserphone);
            ivstatus = itemView.findViewById(R.id.iv_noti_image);
            tvbranch = itemView.findViewById(R.id.tv_branch);

        }
    }


}
