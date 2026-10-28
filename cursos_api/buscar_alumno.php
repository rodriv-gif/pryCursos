<?php
// Este es el api para buscar al alumno con su matricula
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

// Decodificar el cuerpo JSON enviado por Retrofit desde Android
$jsonInput = file_get_contents("php://input");
$data = json_decode($jsonInput, true);
 
//Se recibe la matricula que manda Android
$matricula = isset($data['matricula']) ? trim($data['matricula']) : '';
//Se valida que la matricula no llegue vacia
if (empty($matricula)) {
    header("HTTP/1.1 400 Bad Request");
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe ingresar la matrícula"
    ]);
    exit;
}

try {
    $consulta = $pdo->prepare("SELECT * FROM tblalumnos WHERE matricula = ?");
    $consulta->execute([$matricula]);
    $alumno = $consulta->fetch();

    if ($alumno) {
        //Si existe, se manda el correo a la aplicación Android
        echo json_encode([
            "exito" => true,
            "mensaje" => "Alumno encontrado",
            "correo" => $alumno['correo']
        ]);
    } else {
        echo json_encode([
            "exito" => false,
            "mensaje" => "No se encontró la matrícula"
        ]);
    }

//Si marca un error, se captura y manda un mensaje de error
} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error en el servidor: " . $e->getMessage()
    ]);
}
?>