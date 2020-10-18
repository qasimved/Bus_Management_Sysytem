<?php
require "connection.php";
$parentName= $_POST["parentName"]; 
$parentEmail= $_POST["parentEmail"]; 
$parentPassword= $_POST["parentPassword"]; 
$parentCnic= $_POST["parentCnic"]; 
$parentPhoneNo= $_POST["parentPhoneNo"]; 
$studentId= 0;



// $parentName="parentName"; 
// $parentEmail= "parentEmail"; 
// $parentPassword= "parentPassword"; 
// $parentCnic= "parentCnic"; 
// $parentPhoneNo= "parentPhoneNo"; 
// $studentId= 0;




$returnValue = array();

$sql_query = "INSERT INTO `Parents`(`parentId`, `parentName`, `parentEmail`, `parentPassword`, `parentCnic`, `parentPhoneNo`, `studentId`) VALUES (NULL,'$parentName','$parentEmail','$parentPassword','$parentCnic','$parentPhoneNo','$studentId');";


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