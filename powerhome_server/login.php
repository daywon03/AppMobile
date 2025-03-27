<?php
global $conn;
include 'db.php';
header("Content-Type: application/json");

$data = json_decode(file_get_contents("php://input"));
$email = $data->email;
$password = $data->password;

$sql = "SELECT * FROM user WHERE email='$email'";
$result = $conn->query($sql);
$user = $result->fetch_assoc();

if ($user && password_verify($password, $user['password'])) {
    $token = md5(uniqid().rand(1000, 9999));
    $conn->query("UPDATE user SET token='$token' WHERE email='$email'");
    print json_encode(["token" => $token]);
} else {
    echo json_encode(["error" => "Email ou mot de passe incorrect"]);
}
?>