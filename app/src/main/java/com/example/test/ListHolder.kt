package com.example.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    fun changeRecord(value:Event,position: Int){
        val currentItems = items.value ?: emptyList()
        val updatedItems = currentItems.toMutableList()
        updatedItems[position]=value
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
    data class Event(val name: String,val place: String, val date: String,val time: String)
}
