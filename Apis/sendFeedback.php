<?php
require "connection.php";
$parentId= $_POST["parentId"]; 
$routeId= $_POST["routeId"]; 
$routeFeedback= $_POST["feedback"]; 






$returnValue = array();

$sql_query = "INSERT INTO `Feedback`(`feedbackId`, `feedbaackDescription`, `parentId`, `routeId`) VALUES (NULL,'$routeFeedback','$parentId','$routeId');";


if($result = mysqli_query($connection,$sql_query)) {

     $returnValue["status"] = "success";
     $returnValue["message"] = "Feedback sent successfully";
    
}
else{
    
    $returnValue["status"] = "error";
    $returnValue["message"] = "Error in sending feedback. Reason:  ".mysqli_error($connection);
    
}

echo json_encode($returnValue);

?>