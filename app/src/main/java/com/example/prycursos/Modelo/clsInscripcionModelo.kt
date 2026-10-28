package com.example.prycursos.Modelo

class clsInscripcionModelo {
    suspend fun registrarInscripcion(matriculaUsuario:String,correoUsuario:String,idCurso:Int): InscripcionResponse{
        val request = InscripcionRequest(matricula=matriculaUsuario,correo=correoUsuario,cursoId=idCurso)
        return RetrofitCliente.apiService.registrarInscripcion(request)
    }
    suspend fun buscarCorreo(matriculaUsuario:String): CorreoResponse{
        return RetrofitCliente.apiService.obtenerCorreo(matriculaUsuario)
    }
}