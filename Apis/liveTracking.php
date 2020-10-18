<?php
require "connection.php";

$studentid= intval($_POST["studentId"]); 

// $studentid= intval("16"); 


$returnValue = array();

    
$sql_query = "SELECT * FROM `Students` WHERE `studentId` = $studentid";

$result = mysqli_query($connection,$sql_query);
$count = mysqli_num_rows($result);

$returnValue["status"] = "error";

if($count>0){
            $row=mysqli_fetch_array($result);
            $routeId =  $row['routeId'];
            $returnValue["studentCurrentLatLng"] = $row['studentCurrentLatLng'];
            $sql_query1 = "SELECT * FROM `Routes` WHERE `routeId` = '$routeId'";
            $result1 = mysqli_query($connection,$sql_query1);
            $count1 = mysqli_num_rows($result1);
            
                    if($count1>0) {
                            $row1=mysqli_fetch_array($result1);
                            $driverId =  $row1['driverId'];
                            $returnValue["routeStatus"] =  $row1['routeStatusPickupDrop'];
                            $sql_query2 = "SELECT * FROM `Drivers` WHERE `driverId` = '$driverId'";
                            $result2 = mysqli_query($connection,$sql_query2);
                            $count2 = mysqli_num_rows($result2);
            
                                        if($count2>0) {
                                            $row2=mysqli_fetch_array($result2);
                                            $returnValue["driverLatLng"] =  $row2['driverLatLng'];
                                            $returnValue["status"] = "success";
                                            
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
    }
    else{
         $returnValue["status"] = "error";
         $returnValue["message"] = "No Record Found";
    }
    

echo json_encode($returnValue);

?>