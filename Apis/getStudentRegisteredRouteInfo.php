<?php
require "connection.php";

$studentid= intval($_POST["studentId"]); 

// $studentid= intval("16"); 


$returnValue = array();

    
$sql_query = "SELECT * FROM `Students` WHERE `studentId` = $studentid";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

if($count>0){
            $row=mysqli_fetch_array($result);
            $routeId =  $row['routeId'];
            $returnValue["studentStopLatLng"] = $row['studentStopLatLng'];
            
            $sql_query = "SELECT * FROM `Routes` WHERE `routeId` = '$routeId'";
            
            $result = mysqli_query($connection,$sql_query);
            $count = mysqli_num_rows($result);
            
                    if($count>0) {
                        $returnValue["status"] = "success";
                        $row=mysqli_fetch_array($result);
                        $returnValue["routeNo"] = $row["routeNo"];
                        $returnValue["routeTitle"] = $row["routeTitle"];
                        $returnValue["routeEndpointLatLng"] = $row["routeEndpointLatLng"];
                        $returnValue["routeStopsJson"] = $row["routeStopsJson"];
                    }
                    else{
                        $returnValue["status"] = "error";
                        $returnValue["message"] = "No Record Found";
                    }
    }
    else{
         $returnValue["status"] = "error";
         $returnValue["message"] = "No Record Found";
    }
    

echo json_encode($returnValue);

?>