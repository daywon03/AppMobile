<?php
global $conn;
include 'db.php';
header("Content-Type: application/json");

// Récupérer le JSON envoyé par le client
$data = json_decode(file_get_contents("php://input"));

if (!isset($data->email) || !isset($data->tempPassword) || !isset($data->newPassword)) {
    echo json_encode(["error" => "Données manquantes"]);
    exit;
}

$email = $data->email;
$tempPassword = $data->tempPassword;
$newPassword = $data->newPassword;

// Récupérer l'utilisateur via son email
$sql = "SELECT password FROM user WHERE email='$email'";
$result = $conn->query($sql);

if (!$result || $result->num_rows == 0) {
    echo json_encode(["error" => "Utilisateur non trouvé"]);
    exit;
}

$user = $result->fetch_assoc();

// Vérifier que le mot de passe temporaire saisi correspond au mot de passe actuellement stocké (qui est celui temporaire)
if (password_verify($tempPassword, $user['password'])) {
    // Hacher le nouveau mot de passe
    $hashedNewPassword = password_hash($newPassword, PASSWORD_BCRYPT);
    $updateSql = "UPDATE user SET password='$hashedNewPassword' WHERE email='$email'";
    if ($conn->query($updateSql) === TRUE) {
        echo json_encode(["message" => "Mot de passe mis à jour avec succès"]);
    } else {
        echo json_encode(["error" => "Erreur lors de la mise à jour du mot de passe"]);
    }
} else {
    echo json_encode(["error" => "Le mot de passe temporaire ne correspond pas"]);
}
?>
