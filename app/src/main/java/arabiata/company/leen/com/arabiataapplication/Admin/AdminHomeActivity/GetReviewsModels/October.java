package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetReviewsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class October {

    @SerializedName("happy")
    @Expose
    private Integer happy;
    @SerializedName("unhappy")
    @Expose
    private Integer unhappy;

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
