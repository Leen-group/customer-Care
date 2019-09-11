package arabiata.company.leen.com.arabiataapplication.Survey.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class SurveyModel {
////

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<SurveyModelItem> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<SurveyModelItem> getData() {
        return data;
    }

    public void setData(List<SurveyModelItem> data) {
        this.data = data;
    }
}
