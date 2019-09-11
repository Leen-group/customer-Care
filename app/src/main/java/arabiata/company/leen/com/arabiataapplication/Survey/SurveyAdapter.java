package arabiata.company.leen.com.arabiataapplication.Survey;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import java.util.ArrayList;
import java.util.List;

import arabiata.company.leen.com.arabiataapplication.R;
import arabiata.company.leen.com.arabiataapplication.Survey.Model.SurveyModelItem;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.MyViewHolder> {
    private Context context;
    public List<SurveyModelItem> arraySurveyadapter;
    ArrayList<Integer> selectedids;
    Boolean check;
    SurveyView view;

    public SurveyAdapter(Context context, List<SurveyModelItem> arraySurveyadapter, SurveyView view) {
        this.context = context;
        this.view = view;
        this.arraySurveyadapter = arraySurveyadapter;
        selectedids = new ArrayList<>();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reason_item_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SurveyModelItem recipe = arraySurveyadapter.get(position);
        holder.radioButton.setText(recipe.getTitle());

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedids != null) {
                    check = false;
                    for (int i = 0; i < selectedids.size(); i++) {
                        check = false;
                        if (recipe.getId() == selectedids.get(i)) {
                            holder.radioButton.setChecked(false);
                            selectedids.remove(i);
                            check = true;
                        } else {
                        }
                    }
                    if (!check) {
                        holder.radioButton.setChecked(true);
                        selectedids.add(recipe.getId());
                    }
                }
                view.SetArray(selectedids);
            }
        });

      /*  holder.relativeLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  homeView.onClickItemAds(id_);
            }
        });
*/

              /*  if(recipe.getMainImage()!=null){
            Glide.with(context)
                    .load(recipe.getMainImage())
                    .into(holder.thumbnail);}
*/
    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + arraySurveyadapter.size());
        return arraySurveyadapter.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioButton;

        //  public RelativeLayout relativeLayoutContainer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.rb_list);


            //  relativeLayoutContainer = itemView.findViewById(R.id.list_item_container);
        }
    }


    public interface OpenClick {
        void OnClickItemRow(String id);
    }
}

