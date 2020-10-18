package android.com.busmanagement.Backend;



public class AllRoutesListModel {
    private String routeId;
    private String routeNo;
    private String routeTitle;
    private String routeEndpointLatLng;
    private String routeStopsJson;


    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getRouteTitle() {
        return routeTitle;
    }

    public void setRouteTitle(String routeTitle) {
        this.routeTitle = routeTitle;
    }

    public String getRouteEndpointLatLng() {
        return routeEndpointLatLng;
    }

    public void setRouteEndpointLatLng(String routeEndpointLatLng) {
        this.routeEndpointLatLng = routeEndpointLatLng;
    }

    public String getRouteStopsJson() {
        return routeStopsJson;
    }

    public void setRouteStopsJson(String routeStopsJson) {
        this.routeStopsJson = routeStopsJson;
    }
}
