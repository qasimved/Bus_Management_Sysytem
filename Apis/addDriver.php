<?php
require "connection.php";
$driverName= $_POST["driverName"]; 
$driverEmail= $_POST["driverEmail"]; 
$driverPassword= $_POST["driverPassword"]; 
$driverCnic= $_POST["driverCnic"]; 
$driverPhoneNo= $_POST["driverPhoneNo"]; 
$driverLatLng= "";
$isRouteAssigned = 0;



// $driverName= "driverName2"; 
// $driverEmail= "driverEmail2"; 
// $driverPassword= "driverPassword2"; 
// $driverCnic= "driverCnic2"; 
// $driverPhoneNo= "driverPhoneNo2"; 
// $driverLatLng= "";
// $isRouteAssigned = 0;




$returnValue = array();

$sql_query = "INSERT INTO `Drivers`(`driverId`, `driverName`, `driverEmail`, `driverPassword`, `driverCnic`, `driverPhoneNo`, `driverLatLng`,`IsRouteAssigned`) VALUES (NULL,'$driverName','$driverEmail','$driverPassword','$driverCnic','$driverPhoneNo','$driverLatLng','$isRouteAssigned');";


if($result = mysqli_query($connection,$sql_query)) {

     $returnValue["status"] = "success";
     $returnValue["message"] = "Driver Successfully Registered";
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "User already exist. ".mysqli_error($connection);
    
}

echo json_encode($returnValue);

?>