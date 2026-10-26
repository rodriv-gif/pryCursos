<?php
// obtener_cursos.php
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

$carrera_id = isset($_GET['carrera_id']) ? intval($_GET['carrera_id']) : 0;

try {
    // 1. Incluimos TODOS los campos exactos de tu tabla tblcursos + la imagen principal
    $sql = "SELECT c.curso_id, c.carrera_id, c.nombre, c.codigo, c.descripcion, 
                   c.edificio, c.salon, c.total_horas, c.dias_atencion, c.horario, c.cupos,
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

    // 2. Casteamos los tipos enteros para que coincidan con Kotlin
    foreach ($cursos as &$c) {
        $c['curso_id']   = (int)$c['curso_id'];
        $c['carrera_id'] = (int)$c['carrera_id'];
        $c['cupos']      = (int)$c['cupos'];
    }

    echo json_encode($cursos);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al obtener catálogo de cursos: " . $e->getMessage()]);
}
?>
