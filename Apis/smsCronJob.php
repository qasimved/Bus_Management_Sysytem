<?php
require "connection.php";
$sql_query1 = "SELECT * FROM `Drivers`";
$result1 = mysqli_query($connection,$sql_query1);
$count1 = mysqli_num_rows($result1);
if($count1>0) {
    while($row1=mysqli_fetch_array($result1)){
        $sql_query2 = "SELECT * FROM `Drivers`";
        $result2 = mysqli_query($connection,$sql_query2);
        $count2 = mysqli_num_rows($result2);
        if($count1>0) {
             while($row1=mysqli_fetch_array($result1)){
             }
        }
    }
}

// SELECT id, ( 3959 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(-122) ) + sin( radians(37) ) * sin( radians( lat ) ) ) ) AS distance FROM markers HAVING distance < 25 ORDER BY distance LIMIT 0 , 20;


?>