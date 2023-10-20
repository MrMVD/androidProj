package com.example.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime

//класс для листа
class ListHolder : ViewModel() {
    val items: MutableLiveData<MutableList<Event>> = MutableLiveData() // создание livedata

    //добавление записи
    fun addRecord(value: Event) {
        val currentItems = items.value ?: emptyList()
        val updatedItems = currentItems.toMutableList()
        updatedItems.add(value)
        items.value = updatedItems
    }

    //удаление записи
    fun delRecord(position: Int) {
        val currentItems = items.value ?: emptyList()
        val updatedItems = currentItems.toMutableList()
        updatedItems.removeAt(position)
        items.value = updatedItems
    }

    //класс с данными
    data class Event(val name: String?,val place: String?, val date: LocalDate?,val time: LocalTime?)
}
