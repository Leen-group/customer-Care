package arabiata.company.leen.com.arabiataapplication.Survey;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import arabiata.company.leen.com.arabiataapplication.Base.BaseActivity;
import arabiata.company.leen.com.arabiataapplication.MainActivity;
import arabiata.company.leen.com.arabiataapplication.R;
import arabiata.company.leen.com.arabiataapplication.Survey.Model.SurveyModelItem;
import arabiata.company.leen.com.arabiataapplication.UserProfile.UserProfileActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SurveyActivity extends BaseActivity<SurveyPresenter> implements SurveyView {
    public List<SurveyModelItem> cartList;
    ArrayList<Integer> arrayListIdsActivity;
    public SurveyAdapter surveyAdapter;
    @BindView(R.id.btn_survey)
    Button btn_survey;
    @BindString(R.string.missing)
    String missing;
    @BindView(R.id.reasonlist)
    RecyclerView reasonlist;
    int userStatus;

    @NonNull
    @Override
    protected SurveyPresenter createPresenter(@NonNull Context context) {
        return new SurveyPresenter(this, this);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //   finish();
                break;
        }
        return true;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayListIdsActivity = new ArrayList<>();
        Intent i = getIntent();
        // get 0 of unhappy , 1 of happy
        userStatus = i.getIntExtra("status_key",5);
     //   Toast.makeText(this, userStatus+"", Toast.LENGTH_SHORT).show();



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reasonlist.setLayoutManager(mLayoutManager);
        reasonlist.setItemAnimator(new DefaultItemAnimator());
        reasonlist.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));
        cartList = new ArrayList<>();
        surveyAdapter = new SurveyAdapter(this, cartList, this);
        reasonlist.setAdapter(surveyAdapter);

        // call survey api (survey Presenter ) get data carlist
        mPresenter.GetSurveyApi();


        btn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayListIdsActivity.size()>0){
                    Intent i = new Intent(SurveyActivity.this, UserProfileActivity.class);
                    i.putExtra("status_key", userStatus);
                    i.putIntegerArrayListExtra("arrayListIds", arrayListIdsActivity);
                    startActivity(i);
                }else{

                    Toast.makeText(SurveyActivity.this, ""+missing, Toast.LENGTH_SHORT).show();

                }



            }
        });


    }


    @Override
    public void getEmailPassword() {

    }

    @Override
    public void NavigateToMain() {

    }

    @Override
    public void SetArray(ArrayList<Integer> arrayListIds) {
        arrayListIdsActivity = arrayListIds;
    }
}
