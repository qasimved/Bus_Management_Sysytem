<?php
require "connection.php";
$email= $_POST["email"]; 
$password= $_POST["password"];
$role= $_POST["role"];


$returnValue = array();
$roleInt = intval($role);
if($roleInt == 1){
    
    $sql_query = "select * from `Students` where studentEmail = '$email' and BINARY studentPassword = '$password'";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count==1) {
    $row = mysqli_fetch_array($result);
    $returnValue["status"] = "success";
    $returnValue["message"] = "User successfully logedIn";
    $returnValue["studentId"] = $row["studentId"];
    $returnValue["studentName"] = $row["studentName"];
    $returnValue["studentRegNo"] = $row["studentRegNo"];
    $returnValue["studentEmail"] = $row["studentEmail"];
    $returnValue["studentPassword"] = $row["studentPassword"];
    $returnValue["studentCnic"] = $row["studentCnic"];
    $returnValue["studentPhoneNo"] = $row["studentPhoneNo"];
    $returnValue["studentStopLatLng"] = $row["studentStopLatLng"];
    $returnValue["routeId"] = $row["routeId"];
    
   
    

}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Invalid login or password";
}
    
    
}
else if ($roleInt == 2){
    
    
    $sql_query = "select * from `Parents` where parentEmail = '$email' and BINARY parentPassword = '$password'";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count==1) {
    $row = mysqli_fetch_array($result);
    $returnValue["status"] = "success";
    $returnValue["message"] = "User successfully logedIn";
    $returnValue["parentId"] = $row["parentId"];
    $returnValue["parentName"] = $row["parentName"];
    $returnValue["parentEmail"] = $row["parentEmail"];
    $returnValue["parentPassword"] = $row["parentPassword"];
    $returnValue["parentCnic"] = $row["parentCnic"];
    $returnValue["parentPhoneNo"] = $row["parentPhoneNo"];
      $returnValue["studentId"] = $row["studentId"];
    
   

}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Invalid login or password";
}
    
    
}
else{
    
    
    $sql_query = "select * from `Drivers` where driverEmail = '$email' and BINARY driverPassword = '$password'";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count==1) {
    $row = mysqli_fetch_array($result);
    $returnValue["status"] = "success";
    $returnValue["message"] ="User successfully logedIn";
    $returnValue["driverId"] = $row["driverId"];
    $returnValue["driverName"] = $row["driverName"];
    $returnValue["driverCnic"] = $row["driverCnic"];
    $returnValue["driverphoneNo"] = $row["driverPhoneNo"];
    $returnValue["driverEmail"] = $row["driverEmail"];
    $returnValue["driverPassword"] = $row["driverPassword"];
    $returnValue["driverLatLng"] = $row["driverLatLng"];
    
    

}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "Invalid login or password";
}
    
    
    
}

echo json_encode($returnValue);

?>