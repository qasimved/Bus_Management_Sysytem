<?php
require "connection.php";



$returnValue = array();

    
$sql_query = "SELECT * FROM `Routes` WHERE `driverId` = 0";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count>0) {
    $returnValue["status"] = "success";
    
    
    
    while($row=mysqli_fetch_array($result)){

        $returnValue["Routes"][] = array("routeId"=>$row["routeId"],"routeNo"=>$row["routeNo"],"routeTitle"=>$row["routeTitle"],"routeEndpointLatLng"=>$row["routeEndpointLatLng"],
            "routeStopsJson"=>$row["routeStopsJson"]);
    }
}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "No Routes Found";
    $returnValue["Routes"] = array();
}


$sql_query = "SELECT * FROM `Drivers` WHERE `IsRouteAssigned` = 0";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count>0) {
    $returnValue["status"] = "success";
    
    while($row=mysqli_fetch_array($result)){

        $returnValue["Drivers"][] = array("driverId"=>$row["driverId"],"driverName"=>$row["driverName"]);
    }
}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "No Routes Found";
    $returnValue["Drivers"] = array();
}
    

echo json_encode($returnValue);

?>