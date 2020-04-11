package ar.edu.unq.peluqueriayabackend.service

import java.util.*

interface GenericService<T> {

    fun get(id:Int): Optional<T>
    fun getAll():Collection<T>
    fun save(t:T):T
    fun update(t:T):T
}