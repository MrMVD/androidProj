package com.example.test

import androidx.fragment.app.FragmentManager

interface EditRecordContract {
    interface View {
        fun showNameError()
        fun showPlaceError()
        fun showDateError()
        fun showTimeError()
        fun onRecordAdded()
        fun editRec(inputName:String,inputPlace:String,inputDate:String,inputTime:String)
    }

    interface Presenter {
        fun onAddButtonClick(name: String, place: String, date: String, time: String,fragmentManager: FragmentManager)
        fun onDestroy()
    }
}