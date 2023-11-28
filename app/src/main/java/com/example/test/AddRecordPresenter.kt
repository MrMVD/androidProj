package com.example.test

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class AddRecordPresenter(private val view: AddRecordContract.View) : AddRecordContract.Presenter {

    override fun onAddButtonClick(name: String, place: String, date: String, time: String, fragmentManager: FragmentManager) {
        var nullStr =false

        if (name.isBlank()) {
            view.showNameError()
            nullStr=true
        }

        if (place.isBlank()) {
            view.showPlaceError()
            nullStr=true
        }

        if (date.isBlank()) {
            view.showDateError()
            nullStr=true
        }

        if (time.isBlank()) {
            view.showTimeError()
            nullStr=true
        }

        if(nullStr==true){
            val msg: MutableLiveData<Boolean> = MutableLiveData(false)
            val observer: Observer<Boolean> = Observer { items ->
                if (msg.value == true) {
                    view.addRec(name, place, date, time)
                    view.onRecordAdded()
                }
            }

            msg.observeForever(observer)

            AlertInput(msg).show(fragmentManager,"alertNullAdd")//вызываем диалоговое окно подтверждения

        }else { view.addRec(name,place,date,time)
                view.onRecordAdded()}

    }

    override fun onDestroy() {
        // Можно добавить код для очистки ресурсов, если необходимо
    }
}
