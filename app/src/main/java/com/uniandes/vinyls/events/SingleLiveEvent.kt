package com.uniandes.vinyls.events

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/*
SingleLiveEvent es una clase personalizada que a menudo se utiliza en el desarrollo de aplicaciones
Android con arquitectura de ViewModel y LiveData. Esta clase se utiliza para representar un evento
único que debe ser observado una sola vez, lo que significa que se consumirá después de ser observado.

La razón principal para usar SingleLiveEvent en lugar de LiveData o MutableLiveData está en escenarios
en los que deseas comunicar eventos únicos, como la navegación entre pantallas, mostrar mensajes de
estado o cualquier evento que no deba persistir después de ser observado. Evita que el mismo evento
se dispare nuevamente si el usuario gira la pantalla o se producen cambios en el ciclo de vida de la
actividad.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private val TAG = "SingleLiveEvent"
    }

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Solo una observación activa se permitirá
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        if (t != null) {
            pending.set(true)
            super.setValue(t)
        }
    }

    // Método adicional para marcar el evento como no pendiente
    @MainThread
    fun call() {
        value = null
    }
}