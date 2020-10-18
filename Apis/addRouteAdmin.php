<?php
require "connection.php";
$routeNo= $_POST["routeNo"]; 
$routeTitle= $_POST["routeTitle"]; 
$routeEndpointLatLng= $_POST["routeEndpointLatLng"]; 
$routeStopsJson= $_POST["routeStopsJson"]; 



// $routeNo= "routeNo"; 
// $routeTitle= "routeTitle"; 
// $routeEndpointLatLng= "routeEndpointLatLng"; 
// $routeStopsJson= "routeStopsJson"; 




$returnValue = array();

$sql_query = "INSERT INTO `Routes`(`routeId`, `routeNo`, `routeTitle`, `routeEndpointLatLng`, `routeStopsJson`, `routeBusNo`, `routeStatusPickupDrop`, `driverId`) VALUES (NULL,'$routeNo','$routeTitle','$routeEndpointLatLng','$routeStopsJson',0,0,0)";


if($result = mysqli_query($connection,$sql_query)) {

     $returnValue["status"] = "success";
     $returnValue["message"] = "Route Successfully Added";
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "Error in adding route. ".mysqli_error($connection);
    
}

echo json_encode($returnValue);

?>