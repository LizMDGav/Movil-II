package com.example.respuestasautomaticas.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.respuestasautomaticas.ui.Telefono

class ViewModelMensaje ():ViewModel(){
    var Mensaje by mutableStateOf("")
    var Numero by mutableStateOf("")

    fun updateMensaje(value: String){
        Mensaje = value
        Telefono.Telefono.mensaje=value
    }
    fun updateNumero(value: String){
        Numero = value
        Telefono.Telefono.numero=value
    }
}