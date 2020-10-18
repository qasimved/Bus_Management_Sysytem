<?php
require "connection.php";
$email= $_POST["childEmail"]; 
$password= $_POST["childPassword"];
$parentId= intval($_POST["parentId"]);




$returnValue = array();

    $sql_query = "select * from `Students` where studentEmail = '$email' and BINARY studentPassword = '$password'";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

    if($count==1) {
        
        
        $row = mysqli_fetch_array($result);
        
        $studentId = $row["studentId"];
        
         $sql_query1 = "UPDATE `Parents` SET `studentId`= '$studentId' WHERE `parentId` = '$parentId'";

            if($result1 = mysqli_query($connection,$sql_query1)) {
            
                 $returnValue["status"] = "success";
                 $returnValue["studentId"] = $studentId;
            } 
            else{
                $returnValue["status"] = "error";
                $returnValue["message"] = "Error in adding child, Try again";
            }
    }
    else{
        $returnValue["status"] = "error";
        $returnValue["message"] = "Invalid login or password";
}

echo json_encode($returnValue);

?>