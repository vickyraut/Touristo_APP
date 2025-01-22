<?php
require_once("database.php");

$sql = "select * from hot_places_country";
$result = array();

$data = mysqli_query($con,$sql);

while ($row=mysqli_fetch_array($data)) {
	array_push($result,array('title'=>$row[0],'location'=>$row[1],'image'=>$row[2],'star_rating'=>$row[3]));
}

echo json_encode(array('getHotPlaces'=>$result));
?>