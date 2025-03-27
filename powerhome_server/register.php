<?php
global $conn;
include 'db.php';
header("Content-Type: application/json");

$data = json_decode(file_get_contents("php://input"));

$firstname = $data->firstname;
$lastname = $data->lastname;
$email = $data->email;
$password = password_hash($data->password, PASSWORD_BCRYPT);
$phone = isset($data->phone) ? $data->phone : null;
$address = isset($data->address) ? $data->address : null;

$sql = "INSERT INTO user (firstname, lastname, email, password, phone, address) 
        VALUES ('$firstname', '$lastname', '$email', '$password', '$phone', '$address')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["message" => "Inscription réussie"]);
} else {
    echo json_encode(["error" => "Erreur lors de l'inscription"]);
}
?>
