<?php
require "connection.php";

$returnValue = array();

    
$sql_query = "SELECT * FROM `Feedback`";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count>0) {
    $returnValue["status"] = "success";
    
    while($row=mysqli_fetch_array($result)){
        
        $routeId = $row["routeId"];
        $parentId = $row["parentId"];
        
        $sql_query1 = "SELECT * FROM `Routes` WHERE `routeId` =  '$routeId'";
        $sql_query2 = "SELECT * FROM `Parents` WHERE `parentId` = '$parentId'";
        
        $result1 = mysqli_query($connection,$sql_query1);
        $result2 = mysqli_query($connection,$sql_query2);
        
        $row1=mysqli_fetch_array($result1);
        $row2=mysqli_fetch_array($result2);
        
        

        $returnValue["Feedback"][] = array("feedbackId"=>$row["feedbackId"],"feedbaackDescription"=>$row["feedbaackDescription"],"parentName"=>$row2["parentName"],"routeNo"=>$row1["routeNo"]);
    }
    
    
 
    
   
    

}
else{
    $returnValue["status"] = "error";
    $returnValue["message"] = "No Routes Found";
}
    

echo json_encode($returnValue);

?>