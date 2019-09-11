package arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyNotificationsModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("notifications")
    @Expose
    private List<MyNotificationsModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    /////////////////////////////////
    @SerializedName("id")
    @Expose
    private Integer id;



    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("happy")
    @Expose
    private String happy;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("battery_branch")
    @Expose
    private String batteryBranch;
    @SerializedName("battery_level")
    @Expose
    private String batteryLevel;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<MyNotificationsModel> getData() {
        return data;
    }

    public void setData(List<MyNotificationsModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
//////////////////////////////////////////////
public Integer getId() {
    return id;
}

    public void setId(Integer id) {
        this.id = id;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBatteryBranch() {
        return batteryBranch;
    }

    public void setBatteryBranch(String batteryBranch) {
        this.batteryBranch = batteryBranch;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

}
