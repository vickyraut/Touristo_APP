<?php
require_once("database.php");

$sql = "select name, image, time, date, members, transaction, price from purchased_packages";
$result = array();

$data = mysqli_query($con, $sql);

while ($row=mysqli_fetch_array($data)) {
	array_push($result,array('name'=>$row[0],'image'=>$row[1],'time'=>$row[2],'date'=>$row[3],'members'=> $row[4], 'transaction' => $row[5], 'price' => $row[6]));
}

echo json_encode(array('getBuyedPackages'=>$result));
?>