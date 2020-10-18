<?php
require "connection.php";
$driverId= $_POST["driverId"]; 
$routeId= $_POST["routeId"]; 


// $driverId= 4; 
// $routeId= 14; 

$returnValue = array();

$sql_query = "UPDATE `Routes` SET `driverId`= '$driverId' WHERE `routeId` = '$routeId'";


$sql_query2 = "UPDATE `Drivers` SET `IsRouteAssigned`= 1 WHERE `driverId` = '$driverId'";

if($result = mysqli_query($connection,$sql_query)) {

     if($result = mysqli_query($connection,$sql_query2)) {

     $returnValue["status"] = "success";
     $returnValue["message"] = "Route Successfully Assigned";
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "Error in adding route. ".mysqli_error($connection);
    
}
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "Error in adding route. ".mysqli_error($connection);
    
}

echo json_encode($returnValue);

?>