<?php
require_once("database.php");
$Email = $_POST['email'];

$sql = "select id,profile_pic, firstname,lastname,email from register_user_details where email = '$Email'";

$result = array();
$data = mysqli_query($con,$sql);

if ($row = mysqli_fetch_array($data)) {
	array_push($result, array('id'=>$row[0],'myimage'=>$row[1],'firstname'=>$row[2],'lastname'=>$row[3],'email'=>$row[4]));
}

echo json_encode(array("getMyDetails" => $result));
?>