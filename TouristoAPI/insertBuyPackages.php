<?php
require_once("database.php");
$name = $_POST['name'];
$image = $_POST['image'];
$time = $_POST['time'];
$date = $_POST['date'];
$member = $_POST['members'];
$trac = $_POST['trac'];
$price = $_POST['price'];

$sql = "insert into purchased_packages(name, image,time, date, members, transaction, price) values ('$name', '$image', '$time', '$date', '$member', '$trac', '$price')";

$result = mysqli_query($con, $sql);

if ($result > 0) {
	$response["success"] = 1;
}
else {
	$response["success"] = 0;
}
echo json_encode($response);
?>