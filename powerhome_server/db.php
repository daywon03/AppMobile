<?php


$host = "127.0.0.1";
$user = "root";  // MAMP utilise "root" par défaut
$password = "root";  // MAMP met "root" aussi
$database = "powerhome_db";
$db_port = 8889;


$conn = new mysqli($host, $user, $password, $database,$db_port);
if ($conn->connect_error) {
    die("Échec de connexion : " . $conn->connect_error);
}

