package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetReviewsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("january")
    @Expose
    private January[] january;
    @SerializedName("febraury")
    @Expose
    private Febraury febraury;
    @SerializedName("march")
    @Expose
    private March march;
    @SerializedName("april")
    @Expose
    private April april;
    @SerializedName("may")
    @Expose
    private May may;
    @SerializedName("june")
    @Expose
    private June june;
    @SerializedName("july")
    @Expose
    private July july;
    @SerializedName("augest")
    @Expose
    private Augest augest;
    @SerializedName("september")
    @Expose
    private September september;
    @SerializedName("october")
    @Expose
    private October october;
    @SerializedName("november")
    @Expose
    private November november;
    @SerializedName("december")
    @Expose
    private December december;

    public January[] getJanuary() {
        return january;
    }

    public void setJanuary(January[] january) {
        this.january = january;
    }

    public Febraury getFebraury() {
        return febraury;
    }

    public void setFebraury(Febraury febraury) {
        this.febraury = febraury;
    }

    public March getMarch() {
        return march;
    }

    public void setMarch(March march) {
        this.march = march;
    }

    public April getApril() {
        return april;
    }

    public void setApril(April april) {
        this.april = april;
    }

    public May getMay() {
        return may;
    }

    public void setMay(May may) {
        this.may = may;
    }

    public June getJune() {
        return june;
    }

    public void setJune(June june) {
        this.june = june;
    }

    public July getJuly() {
        return july;
    }

    public void setJuly(July july) {
        this.july = july;
    }

    public Augest getAugest() {
        return augest;
    }

    public void setAugest(Augest augest) {
        this.augest = augest;
    }

    public September getSeptember() {
        return september;
    }

    public void setSeptember(September september) {
        this.september = september;
    }

    public October getOctober() {
        return october;
    }

    public void setOctober(October october) {
        this.october = october;
    }

    public November getNovember() {
        return november;
    }

    public void setNovember(November november) {
        this.november = november;
    }

    public December getDecember() {
        return december;
    }

    public void setDecember(December december) {
        this.december = december;
    }
}
