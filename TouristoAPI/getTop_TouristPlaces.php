<?php
require_once("database.php");

$sql = "select id, name, city, country, image, description, latitude, longitude, price, days, likes, rating, liked_status from tourist_places order by likes DESC";
$result = array();

$data = mysqli_query($con,$sql);

while ($row=mysqli_fetch_array($data)) {
	array_push($result,array('id' => $row[0],'name'=>$row[1],'city'=>$row[2],'country'=>$row[3],'image'=>$row[4],'description'=> $row[5], 'latitude' => $row[6], 'longitude' => $row[7], 'price' => $row[8],'days' => $row[9], 'likes' => $row[10], 'rating' => $row[11], 'liked_status' => $row[12]));
}

echo json_encode(array('getTouristPlaces'=>$result));
?>