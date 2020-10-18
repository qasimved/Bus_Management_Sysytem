<?php
require "connection.php";
$studentRegNo= $_POST["studentRegNo"]; 
$studentName= $_POST["studentName"]; 
$studentEmail= $_POST["studentEmail"]; 
$studentPassword= $_POST["studentPassword"]; 
$studentCnic= $_POST["studentCnic"]; 
$studentPhoneNo= $_POST["studentPhoneNo"];
$studentStopLatLng= "";
$studentCurrentLatLng= "";
$routeId= 0;


// $studentRegNo= "studentRegNo"; 
// $studentName= "studentName"; 
// $studentEmail= "studentEmail"; 
// $studentPassword= "studentPassword"; 
// $studentCnic= "studentCnic"; 
// $studentPhoneNo= "studentPhoneNo";
// $studentStopLatLng= "";
// $studentCurrentLatLng= "";
// $routeId= 0;

$returnValue = array();

$sql_query = "INSERT INTO `Students`(`studentId`, `studentName`, `studentRegNo`, `studentEmail`, `studentCnic`, `studentPhoneNo`, `studentPassword`, `studentStopLatLng`, `studentCurrentLatLng`, `routeId`) VALUES (NULL,'$studentName','$studentRegNo','$studentEmail','$studentCnic','$studentPhoneNo','$studentPassword','$studentStopLatLng','$studentCurrentLatLng','$routeId');";


if($result = mysqli_query($connection,$sql_query)) {

     $returnValue["status"] = "success";
     $returnValue["message"] = "";
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "User already exist. ".mysqli_error($connection);
    
}

echo json_encode($returnValue);

?>