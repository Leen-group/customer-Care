package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyReviewModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<DailyReviewModel> data = null;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("happy")
    @Expose
    private Integer happy;
    @SerializedName("unhappy")
    @Expose
    private Integer unhappy;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DailyReviewModel> getData() {
        return data;
    }

    public void setData(List<DailyReviewModel> data) {
        this.data = data;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHappy() {
        return happy;
    }

    public void setHappy(Integer happy) {
        this.happy = happy;
    }

    public Integer getUnhappy() {
        return unhappy;
    }

    public void setUnhappy(Integer unhappy) {
        this.unhappy = unhappy;
    }

}
