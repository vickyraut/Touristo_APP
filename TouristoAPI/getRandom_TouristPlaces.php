<?php
require_once("database.php");

$sql = "select name, city, country, image, description, latitude, longitude, price, days, likes, rating, liked_status, id from tourist_places order by rand()";
$result = array();

$data = mysqli_query($con,$sql);

while ($row=mysqli_fetch_array($data)) {
	array_push($result,array('name'=>$row[0],'city'=>$row[1],'country'=>$row[2],'image'=>$row[3],'description'=> $row[4], 'latitude' => $row[5], 'longitude' => $row[6], 'price' => $row[7],'days' => $row[8], 'likes' => $row[9], 'rating' => $row[10], 'liked_status' => $row[11], 'id' => $row[12]));
}

echo json_encode(array('getTouristPlaces'=>$result));
?>