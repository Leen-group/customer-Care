package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.axes.Linear;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LabelsOverlapMode;
import com.anychart.enums.Orientation;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel.DailyReviewModel;
import arabiata.company.leen.com.arabiataapplication.Base.BasePresenter;
import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import arabiata.company.leen.com.arabiataapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdminHomePresenter extends BasePresenter {
    AdminHomeActivity context;
    AdminHomeView view;
    private final String TAG = "ADMINACTIVITY";

    public AdminHomePresenter(AdminHomeActivity context, AdminHomeView view) {
        this.context = context;
        this.view = view;
    }

    ///.1
    public AdminHomePresenter() {
    }

    List<String> categories;

    public void GetdateReview(String date) {
        seriesData = new ArrayList<>();
        barChart = AnyChart.bar();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.i(TAG, "datedatedate: "+date);
        Call<DailyReviewModel> call = apiInterface.GetDayReview(date);
        call.enqueue(new Callback<DailyReviewModel>() {
            @Override
            public void onResponse(Call<DailyReviewModel> call, Response<DailyReviewModel> response) {
                if (response.body() != null) {


                    Log.i("TAG", "LoginApi: " + response.body().getSuccess());
                    barChart.animation(true);

                    barChart.padding(10d, 20d, 5d, 20d);

                    barChart.yScale().stackMode(ScaleStackMode.VALUE);

                    barChart.yAxis(0).labels().format(
                            "function() {\n" +
                                    "    return Math.abs(this.value).toLocaleString();\n" +
                                    "  }");

                    barChart.yAxis(0d).title(context.getResources().getString(R.string.NumberofUsers));

                    barChart.xAxis(0d).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    Linear xAxis1 = barChart.xAxis(1d);
                    xAxis1.enabled(true);
                    xAxis1.orientation(Orientation.RIGHT);
                    xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    barChart.title(context.getResources().getString(R.string.chartTittle));

                    barChart.interactivity().hoverMode(HoverMode.BY_X);

                    barChart.tooltip()
                            .title(false)
                            .separator(false)
                            .displayMode(TooltipDisplayMode.SEPARATED)
                            .positionMode(TooltipPositionMode.POINT)
                            .useHtml(true)
                            .fontSize(12d)
                            .offsetX(5d)
                            .offsetY(0d)
                            .format(
                                    "function() {\n" +
                                            "      return '<span style=\"color: #D9D9D9\"> </span>' + Math.abs(this.value).toLocaleString();\n" +
                                            "    }");
                    Log.i("TAG", "response.body().getData().size() " + response.body().getData().size());

/*
                    Log.i("TAG", "JanuaryHappy " + -response.body().getData().get(0).getHappy());
                    Log.i("TAG", "JanUnHappy " + response.body().getData().get(0).getUnhappy());*/
                    for (int i = 0; i <response.body().getData().size() ; i++) {
                        seriesData.add(new CustomDataEntry(response.body().getData().get(i).getName(), response.body().getData().get(i).getHappy(), -(response.body().getData().get(i).getUnhappy())));
                        /*seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.feb), response.body().getData().get(1).getHappy(), -response.body().getData().get(1).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.march), response.body().getData().get(2).getHappy(), -response.body().getData().get(2).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.april), response.body().getData().get(3).getHappy(), -response.body().getData().get(3).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.may), response.body().getData().get(4).getHappy(), -response.body().getData().get(4).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.june), response.body().getData().get(5).getHappy(), -response.body().getData().get(5).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.july), response.body().getData().get(6).getHappy(), -response.body().getData().get(6).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.aug), response.body().getData().get(7).getHappy(), -response.body().getData().get(7).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.sept), response.body().getData().get(8).getHappy(), -response.body().getData().get(8).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.oct), response.body().getData().get(9).getHappy(), -response.body().getData().get(9).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.nov), response.body().getData().get(10).getHappy(), -response.body().getData().get(10).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.dec), response.body().getData().get(11).getHappy(), -response.body().getData().get(11).getUnhappy()));*/
                    }

                    //Log.i(TAG, "onResponseseriesData: " + seriesData.get(0).generateJs());
                    for (int i = 0; i < seriesData.size(); i++) {
                        Log.i(TAG, "ArrayListData: " + seriesData.get(i).generateJs());

                    }

                    Log.i(TAG, "onResponseseriesData: " + seriesData.get(0).generateJs());
                    Set set = Set.instantiate();
                    set.data(seriesData);
                    Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
                    Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

                    Bar series1 = barChart.bar(series1Data);
                    series1.name(context.getResources().getString(R.string.happy))
                            .color("#458b00");
                    series1.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER);

                    Bar series2 = barChart.bar(series2Data);
                    series2.name(context.getResources().getString(R.string.unhappy)).color("#900000");
                    series2.tooltip()
                            .position("left")
                            .anchor(Anchor.RIGHT_CENTER);

                    barChart.legend().enabled(true);
                    barChart.legend().inverted(true);
                    barChart.legend().fontSize(13d);
                    barChart.legend().padding(0d, 0d, 20d, 0d);
                    // Log.i(TAG, "onResponse: "+);
                    context.chartView.setChart(barChart);
                  /*  context.chartView.refreshDrawableState();
                    context.chartView.invalidate();*/
/*
                    if (seriesData != null&& flag==true) {
                        Log.i(TAG, "lastseriesdata: " + seriesData);
                       // context.chartView.clear();
                        seriesData.clear();

                    }*/

                }

            }

            @Override
            public void onFailure(Call<DailyReviewModel> call, Throwable t) {

            }
        });

    }

    public void GetBranchesYears() {

  /*      List<String> categoriesyears;
        context.spinneryear.setOnItemSelectedListener(context);
        categoriesyears = new ArrayList<String>();
        categoriesyears.add("2019");
        categoriesyears.add("2020");
        categoriesyears.add("2021");
        categoriesyears.add("2022");
        categoriesyears.add("2023");


        Log.i(TAG, "categoriesyears: " + categoriesyears);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item, categoriesyears);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        // attaching data adapter to spinner
        context.spinneryear.setAdapter(dataAdapter);
        //    SetBranchesReview(categoriesyears.);

*/
    }

    Cartesian barChart;
    static List<DataEntry> seriesData;

    //LoginAPi
    public void GetReviews() {
        // context.chartView.setChart(null);
        seriesData = new ArrayList<>();
        barChart = AnyChart.bar();
        /*Log.i(TAG, "flagflagflagflag: "+flag);
//        Log.i(TAG, "dataoutsidethemethod: " + seriesData.get(0).generateJs());

        if (seriesData != null&& flag==true) {
            Log.i(TAG, "seriesdataflag: " + seriesData);
            context.chartView.clear();
            seriesData.clear();
            context.chartView.invalidate();
            context.chartView.clear();
            barChart.removeAllSeries();
            barChart.autoRedraw();
            /////

        }*/
      /*  if (barChart != null) {
          //  context.chartView.invalidate();
            //    context.chartView.clear();
            barChart.removeAllSeries();

        }*/


        //Log.i(TAG, "GetReviewsbranch_id= " + branch_id + "GetReviewsyear= " + year);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DailyReviewModel> call = apiInterface.GetDailyReviews();


        call.enqueue(new Callback<DailyReviewModel>() {
            @Override
            public void onResponse(Call<DailyReviewModel> call, Response<DailyReviewModel> response) {

                if (response.body() != null) {


                    Log.i("TAG", "LoginApi: " + response.body().getSuccess());
                    barChart.animation(true);

                    barChart.padding(10d, 20d, 5d, 20d);

                    barChart.yScale().stackMode(ScaleStackMode.VALUE);

                    barChart.yAxis(0).labels().format(
                            "function() {\n" +
                                    "    return Math.abs(this.value).toLocaleString();\n" +
                                    "  }");

                    barChart.yAxis(0d).title(context.getResources().getString(R.string.NumberofUsers));

                    barChart.xAxis(0d).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    Linear xAxis1 = barChart.xAxis(1d);
                    xAxis1.enabled(true);
                    xAxis1.orientation(Orientation.RIGHT);
                    xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    barChart.title(context.getResources().getString(R.string.chartTittle));

                    barChart.interactivity().hoverMode(HoverMode.BY_X);

                    barChart.tooltip()
                            .title(false)
                            .separator(false)
                            .displayMode(TooltipDisplayMode.SEPARATED)
                            .positionMode(TooltipPositionMode.POINT)
                            .useHtml(true)
                            .fontSize(12d)
                            .offsetX(5d)
                            .offsetY(0d)
                            .format(
                                    "function() {\n" +
                                            "      return '<span style=\"color: #D9D9D9\"> </span>' + Math.abs(this.value).toLocaleString();\n" +
                                            "    }");

                    Log.i("TAG", "JanuaryHappy " + -response.body().getData().get(0).getHappy());
                    Log.i("TAG", "JanUnHappy " + response.body().getData().get(0).getUnhappy());
                    for (int i = 0; i <response.body().getData().size() ; i++) {
                        seriesData.add(new CustomDataEntry(response.body().getData().get(i).getName(), response.body().getData().get(i).getHappy(), -(response.body().getData().get(i).getUnhappy())));
                        /*seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.feb), response.body().getData().get(1).getHappy(), -response.body().getData().get(1).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.march), response.body().getData().get(2).getHappy(), -response.body().getData().get(2).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.april), response.body().getData().get(3).getHappy(), -response.body().getData().get(3).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.may), response.body().getData().get(4).getHappy(), -response.body().getData().get(4).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.june), response.body().getData().get(5).getHappy(), -response.body().getData().get(5).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.july), response.body().getData().get(6).getHappy(), -response.body().getData().get(6).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.aug), response.body().getData().get(7).getHappy(), -response.body().getData().get(7).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.sept), response.body().getData().get(8).getHappy(), -response.body().getData().get(8).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.oct), response.body().getData().get(9).getHappy(), -response.body().getData().get(9).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.nov), response.body().getData().get(10).getHappy(), -response.body().getData().get(10).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.dec), response.body().getData().get(11).getHappy(), -response.body().getData().get(11).getUnhappy()));*/
                    }

                    //Log.i(TAG, "onResponseseriesData: " + seriesData.get(0).generateJs());
                    for (int i = 0; i < seriesData.size(); i++) {
                        Log.i(TAG, "ArrayListData: " + seriesData.get(i).generateJs());

                    }

                    Log.i(TAG, "onResponseseriesData: " + seriesData.get(0).generateJs());
                    Set set = Set.instantiate();
                    set.data(seriesData);
                    Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
                    Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

                    Bar series1 = barChart.bar(series1Data);
                    series1.name(context.getResources().getString(R.string.happy))
                            .color("#458b00");
                    series1.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER);

                    Bar series2 = barChart.bar(series2Data);
                    series2.name(context.getResources().getString(R.string.unhappy)).color("#900000");
                    series2.tooltip()
                            .position("left")
                            .anchor(Anchor.RIGHT_CENTER);

                    barChart.legend().enabled(true);
                    barChart.legend().inverted(true);
                    barChart.legend().fontSize(13d);
                    barChart.legend().padding(0d, 0d, 20d, 0d);
                    // Log.i(TAG, "onResponse: "+);
                    context.chartView.setChart(barChart);
                  /*  context.chartView.refreshDrawableState();
                    context.chartView.invalidate();*/
/*
                    if (seriesData != null&& flag==true) {
                        Log.i(TAG, "lastseriesdata: " + seriesData);
                       // context.chartView.clear();
                        seriesData.clear();

                    }*/

                }

            }

            @Override
            public void onFailure(Call<DailyReviewModel> call, Throwable t) {

                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("TAG", "t.getMessage() " + t.getMessage());

            }
        });


    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
            Log.i(TAG, "CustomDataEntryvalue: " + value);
            Log.i(TAG, "CustomDataEntryvalue2: " + value2);

        }
    }

  //  List<String> categoriesBranch;
     Cartesian barChartperiod ;
        List<DataEntry> seriesDataperiod;

    public void getReviewPeriod(final String from, final String to) {
        seriesDataperiod = new ArrayList<>();
        barChartperiod = AnyChart.bar();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DailyReviewModel> call = apiInterface.GetReviews(from,to);
        call.enqueue(new Callback<DailyReviewModel>() {
            @Override
            public void onResponse(Call<DailyReviewModel> call, Response<DailyReviewModel> response) {
                if (response.body() != null) {
                    barChartperiod.animation(true);

                    barChartperiod.padding(10d, 20d, 5d, 20d);

                    barChartperiod.yScale().stackMode(ScaleStackMode.VALUE);

                    barChartperiod.yAxis(0).labels().format(
                            "function() {\n" +
                                    "    return Math.abs(this.value).toLocaleString();\n" +
                                    "  }");

                    barChartperiod.yAxis(0d).title(context.getResources().getString(R.string.NumberofUsers));

                    barChartperiod.xAxis(0d).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    Linear xAxis1 = barChartperiod.xAxis(1d);
                    xAxis1.enabled(true);
                    xAxis1.orientation(Orientation.RIGHT);
                    xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                    barChartperiod.title(context.getResources().getString(R.string.chartTittle));

                    barChartperiod.interactivity().hoverMode(HoverMode.BY_X);

                    barChartperiod.tooltip()
                            .title(false)
                            .separator(false)
                            .displayMode(TooltipDisplayMode.SEPARATED)
                            .positionMode(TooltipPositionMode.POINT)
                            .useHtml(true)
                            .fontSize(12d)
                            .offsetX(5d)
                            .offsetY(0d)
                            .format(
                                    "function() {\n" +
                                            "      return '<span style=\"color: #D9D9D9\"> </span>' + Math.abs(this.value).toLocaleString();\n" +
                                            "    }");

                    Log.i("TAG", "JanuaryHappy " + -response.body().getData().get(0).getHappy());
                    Log.i("TAG", "JanUnHappy " + response.body().getData().get(0).getUnhappy());
                    for (int i = 0; i <response.body().getData().size() ; i++) {
                        seriesDataperiod.add(new CustomDataEntry(response.body().getData().get(i).getName(), response.body().getData().get(i).getHappy(), -(response.body().getData().get(i).getUnhappy())));
                        /*seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.feb), response.body().getData().get(1).getHappy(), -response.body().getData().get(1).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.march), response.body().getData().get(2).getHappy(), -response.body().getData().get(2).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.april), response.body().getData().get(3).getHappy(), -response.body().getData().get(3).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.may), response.body().getData().get(4).getHappy(), -response.body().getData().get(4).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.june), response.body().getData().get(5).getHappy(), -response.body().getData().get(5).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.july), response.body().getData().get(6).getHappy(), -response.body().getData().get(6).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.aug), response.body().getData().get(7).getHappy(), -response.body().getData().get(7).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.sept), response.body().getData().get(8).getHappy(), -response.body().getData().get(8).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.oct), response.body().getData().get(9).getHappy(), -response.body().getData().get(9).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.nov), response.body().getData().get(10).getHappy(), -response.body().getData().get(10).getUnhappy()));
                        seriesData.add(new CustomDataEntry(context.getResources().getString(R.string.dec), response.body().getData().get(11).getHappy(), -response.body().getData().get(11).getUnhappy()));*/
                    }
///////////////////////////
                    //Log.i(TAG, "onResponseseriesData: " + seriesData.get(0).generateJs());
                    for (int i = 0; i < seriesDataperiod.size(); i++) {
                        Log.i(TAG, "ArrayListData: " + seriesDataperiod.get(i).generateJs());

                    }

                    Log.i(TAG, "onResponseseriesData: " + seriesDataperiod.get(0).generateJs());
                    Set set = Set.instantiate();
                    set.data(seriesDataperiod);
                    Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
                    Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

                    Bar series1 = barChartperiod.bar(series1Data);
                    series1.name(context.getResources().getString(R.string.happy))
                            .color("#458b00");
                    series1.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER);

                    Bar series2 = barChartperiod.bar(series2Data);
                    series2.name(context.getResources().getString(R.string.unhappy)).color("#900000");
                    series2.tooltip()
                            .position("left")
                            .anchor(Anchor.RIGHT_CENTER);

                    barChartperiod.legend().enabled(true);
                    barChartperiod.legend().inverted(true);
                    barChartperiod.legend().fontSize(13d);
                    barChartperiod.legend().padding(0d, 0d, 20d, 0d);
                    // Log.i(TAG, "onResponse: "+);
                    context.chartView.setChart(barChartperiod);
                }
            }

            @Override
            public void onFailure(Call<DailyReviewModel> call, Throwable t) {

            }
        });
        SharedPreferences pref = context.getSharedPreferences("ChartValues", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.commit();
    }

}
