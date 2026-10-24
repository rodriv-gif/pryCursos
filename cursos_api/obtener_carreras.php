<?php
// Le indicamos al cliente (Android / Retrofit) que la respuesta es un JSON estructurado
header("Content-Type: application/json; charset=UTF-8");

// Importamos el archivo de conexion
require_once "config/conexion.php";

try {
    // Preparamos y ejecutamos la consulta ordenada alfabeticamente
    $stmt = $pdo->query("SELECT carrera_id, carrera FROM tblcarreras ORDER BY carrera ASC");
    $carreras = $stmt->fetchAll();

    // Convertimos el id a entero para que Kotlin lo reciba como Int (sin comillas)
    foreach ($carreras as &$c) {
        $c['carrera_id'] = (int)$c['carrera_id'];
    }

    // Convertimos el array de PHP en un formato JSON legible para Android
    echo json_encode($carreras);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al obtener carreras: " . $e->getMessage()]);
}
?>