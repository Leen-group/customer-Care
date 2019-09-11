package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetBranchesModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Branches> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Branches> getData() {
        return data;
    }

    public void setData(List<Branches> data) {
        this.data = data;
    }
}
