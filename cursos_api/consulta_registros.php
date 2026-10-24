<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

// Filtro de carrera (0 = todas)
$carrera_id = isset($_GET['carrera_id']) ? intval($_GET['carrera_id']) : 0;

// El mismo filtro se reutiliza en las dos consultas
$where  = $carrera_id > 0 ? " WHERE c.carrera_id = ?" : "";
$params = $carrera_id > 0 ? [$carrera_id] : [];

try {
    // 1. Cursos con su número de inscritos (barras de progreso)
    $sql = "SELECT c.curso_id, c.nombre, c.cupos,
                   COUNT(i.inscripcion_id) AS inscritos,
                   (SELECT img.url_imagen
                      FROM tblimagenes_curso img
                     WHERE img.curso_id = c.curso_id
                     ORDER BY img.imagenes_id
                     LIMIT 1) AS url_imagen
              FROM tblcursos c
              LEFT JOIN tblinscripciones i ON i.curso_id = c.curso_id"
          . $where .
          " GROUP BY c.curso_id, c.nombre, c.cupos
            ORDER BY c.nombre";

    $stmt = $pdo->prepare($sql);
    $stmt->execute($params);
    $cursos = $stmt->fetchAll();

    // 2. Total de inscritos
    $sqlTotal = "SELECT COUNT(i.inscripcion_id) AS total
                   FROM tblinscripciones i
             INNER JOIN tblcursos c ON c.curso_id = i.curso_id"
              . $where;

    $stmt = $pdo->prepare($sqlTotal);
    $stmt->execute($params);
    $total_inscritos = (int)$stmt->fetchColumn();

    // Casteo de tipos y datos ya calculados para la barra de progreso
    foreach ($cursos as &$c) {
        $c['curso_id']    = (int)$c['curso_id'];
        $c['cupos']       = (int)$c['cupos'];
        $c['inscritos']   = (int)$c['inscritos'];
        $c['disponibles'] = max($c['cupos'] - $c['inscritos'], 0);
        $c['porcentaje']  = $c['cupos'] > 0
                            ? min(100, (int)round($c['inscritos'] * 100 / $c['cupos']))
                            : 0;
        $c['lleno']       = $c['inscritos'] >= $c['cupos'];
        $c['color_barra'] = $c['lleno'] ? "#2E9E4F" : "#1F5FA8";   // verde si está lleno, azul si no
    }
    unset($c);

    echo json_encode([
        "total_cursos"    => count($cursos),
        "total_inscritos" => $total_inscritos,
        "cursos"          => $cursos
    ]);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al consultar registros: " . $e->getMessage()]);
}
?>