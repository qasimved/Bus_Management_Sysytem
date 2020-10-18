package android.com.busmanagement.Backend;

public class AllFeedbackListModel {
    private String feedbackId,feedbaackDescription,parentName,routeNo;

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbaackDescription() {
        return feedbaackDescription;
    }

    public void setFeedbaackDescription(String feedbaackDescription) {
        this.feedbaackDescription = feedbaackDescription;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }
}
