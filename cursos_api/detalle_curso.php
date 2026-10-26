<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET");

require_once "config/conexion.php";

$id_curso = isset($_GET['id']) ? intval($_GET['id']) : 0;

if ($id_curso <= 0) {
    http_response_code(400);
    echo json_encode(["error" => "ID de curso no proporcionado o inválido"]);
    exit;
}

try {
    // '\\n' = salto de línea que MySQL interpreta dentro del texto
    $sql = "SELECT
                c.curso_id AS id,
                c.nombre AS titulo,
                c.codigo,
                c.descripcion,
                CONCAT(e.edificio, ', Salón ', e.salon) AS espacio,
                CONCAT(c.total_horas, ' Horas') AS horas_totales,
                CONCAT_WS('\\n', c.dias_atencion, CONCAT('(', c.horario, ' hrs)')) AS dias_atencion,
                (SELECT i.url_imagen
                   FROM tblimagenes_curso i
                  WHERE i.curso_id = c.curso_id
                  ORDER BY i.imagenes_id
                  LIMIT 1) AS imagen_url
            FROM tblcursos c
            INNER JOIN tblespacios e ON e.espacio_id = c.espacio_id
            WHERE c.curso_id = ?";

    $stmt = $pdo->prepare($sql);
    $stmt->execute([$id_curso]);
    $curso = $stmt->fetch();

    if (!$curso) {
        http_response_code(404);
        echo json_encode(["mensaje" => "Curso no encontrado"]);
        exit;
    }

    $curso['id'] = (int)$curso['id'];

    echo json_encode($curso);

} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(["error" => "Error al obtener el detalle: " . $e->getMessage()]);
}
?>