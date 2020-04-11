package ar.edu.unq.peluqueriayabackend.persistence

import java.util.Optional

interface GenericDAO<T> {
    fun get(id:Int): Optional<T>
    fun getAll():Collection<T>
    fun save(t:T):T
    fun update(t:T):T
    fun delete(t:T)
}