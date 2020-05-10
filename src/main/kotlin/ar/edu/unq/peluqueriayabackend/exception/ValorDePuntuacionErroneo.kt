package ar.edu.unq.peluqueriayabackend.exception

import java.lang.Exception

class ValorDePuntuacionErroneo : Exception("El valor entero de puntuacion no esta en el rango del [1, 5]")