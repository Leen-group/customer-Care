package arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.AdminHomeActivity;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.MyNotificationsModel;
import arabiata.company.leen.com.arabiataapplication.Base.BaseActivity;
import arabiata.company.leen.com.arabiataapplication.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminNotification extends BaseActivity<AdminNotificationPresenter> implements AdminNotificationInterface{
    @BindView(R.id.recycler_view_mynoti)
    public RecyclerView recyclerView;
    public MyNotiAdapter Adapter;
    @BindString(R.string.my_notification)
    String my_notification;


    public List<MyNotificationsModel> cartList;
    @NonNull
    @Override
    protected AdminNotificationPresenter createPresenter(@NonNull Context context) {
        return new AdminNotificationPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);
        ButterKnife.bind(this);
        init();
        mPresenter.getMyNotificationsAPI(cartList, Adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(AdminNotification.this, AdminHomeActivity.class);
        startActivity(i);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification, menu);
        //in case of visitor hide create icon
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("TAG", "MenuItem: " + item);
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(AdminNotification.this, AdminHomeActivity.class);
                startActivity(i);
                Log.i("TAG", "onOptionsItemSelected: " + "hi");
                return true;
            case R.id.delete:
                mPresenter.deleteMyNotificationsAPI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void init() {
        getSupportActionBar().setTitle(my_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        initRecycleList();
    }

    private void initRecycleList() {
        cartList = new ArrayList<>();
        Adapter = new MyNotiAdapter(getApplicationContext(), cartList, this);
        recyclerView.setAdapter(Adapter);
    }
}
