<?php
require_once("database.php");
$place_id = $_POST['id'];
$flag = $_POST['flag'];

if ($flag == 0) {
	$sql = "UPDATE tourist_places SET likes = likes - 1, liked_status = 0 WHERE id = $place_id";
}else{
	$sql = "UPDATE tourist_places SET likes = likes + 1, liked_status = 1 WHERE id = $place_id";
}

$result = mysqli_query($con,$sql);

if ($result > 0) {
	$response["success"] = 1;
}
else {
	$response["success"] = 0;
}
echo json_encode($response);
?>