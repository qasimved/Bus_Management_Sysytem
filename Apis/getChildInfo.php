<?php
require "connection.php";
$parentId= intval($_POST["parentId"]);
// $parentId= intval("3");
$returnValue = array();

    $sql_query = "SELECT * FROM `Parents` WHERE `parentId` = '$parentId'";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

    if($count==1) {
        
        $row = mysqli_fetch_array($result);
        
        $studentId = $row["studentId"];
        
         $sql_query1 = "SELECT * FROM `Students` WHERE `studentId` = '$studentId'";

            if($result1 = mysqli_query($connection,$sql_query1)) {
                 $row1 = mysqli_fetch_array($result1);
                 $returnValue["status"] = "success";
                 $returnValue["studentName"] = $row1["studentName"];
                 $returnValue["studentRegNo"] = $row1["studentRegNo"];
                 $returnValue["studentEmail"] = $row1["studentEmail"];
                 $returnValue["studentCnic"] = $row1["studentCnic"];
                 $returnValue["studentPhoneNo"] = $row1["studentPhoneNo"];
            } 
            else{
                $returnValue["status"] = "error";
                $returnValue["message"] = "Error in retriving child data, Try again";
            }
    }
    else{
        $returnValue["status"] = "error";
        $returnValue["message"] = "No student Found";
}

echo json_encode($returnValue);

?>