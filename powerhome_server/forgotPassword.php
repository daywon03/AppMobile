<?php
global $conn;
include 'db.php';
header("Content-Type: application/json");

// Récupérer le JSON envoyé par le client
$data = json_decode(file_get_contents("php://input"));

if (!isset($data->email)) {
    echo json_encode(["error" => "Email manquant"]);
    exit;
}

$email = $data->email;

// Vérifier si l'email existe dans la base
$sql = "SELECT * FROM user WHERE email='$email'";
$result = $conn->query($sql);

if (!$result || $result->num_rows == 0) {
    echo json_encode(["error" => "Email non trouvé"]);
    exit;
}

// Générer un mot de passe temporaire (8 caractères)
$tempPassword = substr(md5(uniqid(rand(), true)), 0, 8);

// Hacher le mot de passe temporaire
$hashedPassword = password_hash($tempPassword, PASSWORD_BCRYPT);

// Mettre à jour le mot de passe de l'utilisateur
$updateSql = "UPDATE user SET password='$hashedPassword' WHERE email='$email'";
if ($conn->query($updateSql) === TRUE) {
    // Pour la démo, on renvoie le mot de passe temporaire dans la réponse
    echo json_encode([
        "message" => "Mot de passe temporaire généré",
        "tempPassword" => $tempPassword
    ]);
} else {
    echo json_encode(["error" => "Erreur lors de la mise à jour du mot de passe"]);
}
?>
