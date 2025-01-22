<?php 
require_once("database.php");
$Email = $_POST['email'];
$Firstname = $_POST['firstname'];
$Lastname = $_POST['lastname'];
$Password = $_POST['password'];

$sql = "insert into register_user_details(email, firstname,lastname, password) values ('$Email', '$Firstname', '$Lastname', '$Password')";

$result = mysqli_query($con,$sql);

if ($result > 0) {
	$response["success"] = 1;
}
else {
	$response["success"] = 0;
}
echo json_encode($response);
?>