<?php
require "connection.php";
$driverId= intval($_POST["driverId"]); 
$driverLatLng= $_POST["driverLatLng"];
$routeStatusPickupDrop= intval($_POST["routeStatusPickupDrop"]);


$returnValue = array();

$sql_query1 = "UPDATE `Drivers` SET `driverLatLng`= '$driverLatLng' WHERE `driverId` = '$driverId'";

$sql_query2 = "UPDATE `Routes` SET `routeStatusPickupDrop`= '$routeStatusPickupDrop' WHERE `driverId` = '$driverId'";

if(mysqli_query($connection,$sql_query1))
{
    if(mysqli_query($connection,$sql_query2))
        {

            $returnValue["status"] = "success";
            $returnValue["message"] = "Location updated";
    
    }

        else{
            $returnValue["status"] = "error";
            $returnValue["message"] = "Something went wrong" .mysqli_error($connection);
        }
        
    
}

else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Something went wrong".mysqli_error($connection);
}
    

echo json_encode($returnValue);

?>