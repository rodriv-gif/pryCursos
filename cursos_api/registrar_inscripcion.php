<?php

header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

// Se utiliza file_get_contents para recibir la información enviada desde Android en formato json
$json=file_get_contents("php://input");
//Lo convierte a un arreglo de php
$datos=json_decode($json, true);

//Se obtienen los datos de matrícula, correo y curso del arreglo($datos), si no llegan se dejan vacíos.
$matricula = isset($datos['matricula']) ? trim($datos['matricula']) : '';
$correo = isset($datos['correo']) ? trim($datos['correo']) : '';
$curso_id = isset($datos['curso_id']) ? intval($datos['curso_id']) : 0;

//Se validan los datos para comprobar que sean correctos antes de guardarlos.
if (empty($matricula) || empty($correo) || $curso_id<=0) {
    header("HTTP/1.1 400 Bad Request");
    //Se envía un mensaje de error en formato json a la aplicación Android.
    echo json_encode([
        "exito" => false,
        "mensaje" => "La información proporcionada no es valida"
    ]);
    exit;
}

try {
    //Se revisa si el alumno ya esta inscrito en ese curso
    $consulta=$pdo->prepare("SELECT inscripcion_id FROM tblinscripciones WHERE matricula = ? AND curso_id = ?");
    $consulta->execute([$matricula,$curso_id]);
    $inscrito=$consulta->fetch();

    if ($inscrito) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "El alumno ya esta inscrito en este curso"
        ]);
    } else {
        //Se insertan los datos del usuario que aparece en la pantalla de registro
        $insertar=$pdo->prepare("INSERT INTO tblinscripciones(matricula,correo,curso_id) VALUES(?,?,?)" );
        //se ejecuta y guarda en la bd
        $insertar->execute([$matricula,$correo,$curso_id]);
        //Manda un mensaje de que se cumplio el proceso
        echo json_encode([
            "exito" => true,
            "mensaje" => "Inscripción exitosa"
        ]);
    }
//Si marca un error, se captura y manda un mensaje de error
} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error al insertar los datos ".$e->getMessage()
    ]);

}

?>