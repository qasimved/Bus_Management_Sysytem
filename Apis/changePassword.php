<?php
require "connection.php";
$userId= $_POST["userId"]; 
$password= $_POST["password"];
$role= $_POST["role"];




$returnValue = array();
$roleInt = intval($role);


if($roleInt == 1){
    
    $sql_query = "UPDATE `Students` SET `studentPassword`= '$password'  WHERE `studentId`= '$userId'";

if(mysqli_query($connection,$sql_query))
{
    $returnValue["status"] = "success";
    $returnValue["message"] = "Password successfully chnaged";
    
}

else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Something went wrong";
}
    
}
else if ($roleInt == 2){
       
        $sql_query = "UPDATE `Parents` SET `parentPassword`= '$password' WHERE `parentId`= '$userId'";

if(mysqli_query($connection,$sql_query))
{
    $returnValue["status"] = "success";
    $returnValue["message"] = "Password successfully chnaged";
    
}

else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Something went wrong";
}
    
}
else{
    
    
    $sql_query = "UPDATE `Drivers` SET `driverPassword`= '$password'  WHERE `driverId`= '$userId'";

if(mysqli_query($connection,$sql_query))
{
    $returnValue["status"] = "success";
    $returnValue["message"] = "Password successfully chnaged";
    
}

else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Something went wrong";
}
    
    
}

echo json_encode($returnValue);

?>