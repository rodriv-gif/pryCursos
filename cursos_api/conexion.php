<?php
$host = "localhost";
$db = "catalogo_cursos";
$user = "root"; // Cambia esto si tu MySQL tiene otro usuario
$pass = "";     // Cambia esto si tu MySQL tiene contrasenia

try {
    // Nos conectamos definiendo el charset utf8mb4 para que no haya problemas con tildes o la 'ñ'
    $pdo = new PDO("mysql:host=$host;dbname=$db;charset=utf8mb4", $user, $pass);
    $pdo->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    // Configuramos PDO para que lance excepciones si ocurre un error en los queries
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Configuramos para que por defecto devuelva los datos como un array asociativo
    $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

} catch (PDOException $e) {
    // Si la conexion falla, devolvemos un codigo de error HTTP 500 y un JSON explicando el problema
    header("Content-Type: application/json; charset=UTF-8", true, 500);
    echo json_encode([
        "error" => "Error critico de conexion al servidor",
        "detalle" => $e->getMessage()
    ]);
    exit;
}
?>