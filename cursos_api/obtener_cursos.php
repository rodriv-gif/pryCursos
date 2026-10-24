<?php
// Le indicamos al cliente (Android / Retrofit) que la respuesta es un JSON estructurado
header("Content-Type: application/json; charset=UTF-8");

// Importamos el archivo de conexion
require_once "config/conexion.php";

// Recibimos el filtro de carrera (0 = traer todos los cursos)
$carrera_id = isset($_GET['carrera_id']) ? intval($_GET['carrera_id']) : 0;

try {
    // Consulta base: nombre, salon y la primera imagen del curso
    $sql = "SELECT c.curso_id, c.carrera_id, c.nombre, c.salon,
                   (SELECT i.url_imagen
                      FROM tblimagenes_curso i
                     WHERE i.curso_id = c.curso_id
                     ORDER BY i.imagenes_id
                     LIMIT 1) AS url_imagen
              FROM tblcursos c";

    if ($carrera_id > 0) {
        $stmt = $pdo->prepare($sql . " WHERE c.carrera_id = ? ORDER BY c.nombre");
        $stmt->execute([$carrera_id]);
    } else {
        $stmt = $pdo->query($sql . " ORDER BY c.nombre");
    }
    $cursos = $stmt->fetchAll();

    // Casteamos los tipos de datos para que coincidan con la data class de Kotlin
    foreach ($cursos as &$c) {
        $c['curso_id']   = (int)$c['curso_id'];
        $c['carrera_id'] = (int)$c['carrera_id'];
    }

    echo json_encode($cursos);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al obtener cursos: " . $e->getMessage()]);
}
?>