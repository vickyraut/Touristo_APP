<?php
require_once("database.php");
$Email = $_POST['email'];

$sql = "select * from register_user_details where email = '$Email'";

$result = mysqli_query($con, $sql);

if  (mysqli_fetch_array($result)) {
    $responce['success'] = 1;
}
else{
	$responce['success'] = 0;
}

echo json_encode($responce);
?>