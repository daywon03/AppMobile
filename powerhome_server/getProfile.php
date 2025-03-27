<?php
global $conn;
include 'db.php';
header("Content-Type: application/json");

// Vérification du token envoyé
if (!isset($_GET['token'])) {
    echo json_encode(["error" => "Token manquant"]);
    exit;
}

$token = $_GET['token'];
$sql = "SELECT firstname, lastname, email, phone, address FROM user WHERE token='$token'";
$result = $conn->query($sql);
$user = $result->fetch_assoc();

if ($user) {
    echo json_encode($user);
} else {
    echo json_encode(["error" => "Utilisateur non trouvé ou token invalide"]);
}
?>
