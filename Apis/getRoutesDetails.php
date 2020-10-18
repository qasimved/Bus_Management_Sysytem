<?php
require "connection.php";



$returnValue = array();

    
$sql_query = "SELECT * FROM `Routes`";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count>0) {
    $returnValue["status"] = "success";
    
    while($row=mysqli_fetch_array($result)){

        $returnValue["Routes"][] = array("routeId"=>$row["routeId"],"routeNo"=>$row["routeNo"],"routeTitle"=>$row["routeTitle"],"routeEndpointLatLng"=>$row["routeEndpointLatLng"],
            "routeStopsJson"=>$row["routeStopsJson"]);
    }
    
    
    // $returnValue["routeNo"] = $row["routeNo"];
    // $returnValue["routeTitle"] = $row["routeTitle"];
    // $returnValue["routeEndpointLatLng"] = $row["routeEndpointLatLng"];
    // $returnValue["routeStopsJson"] = $row["routeStopsJson"];
  
    
   
    

}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "No Routes Found";
}
    

echo json_encode($returnValue);

?>