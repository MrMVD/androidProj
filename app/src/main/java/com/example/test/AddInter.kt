package com.example.test

import androidx.fragment.app.FragmentManager

interface AddRecordContract {
    interface View {
        fun showNameError()
        fun showPlaceError()
        fun showDateError()
        fun showTimeError()
        fun onRecordAdded()
        fun addRec(inputName:String,inputPlace:String,inputDate:String,inputTime:String)
    }

    interface Presenter {
        fun onAddButtonClick(name: String, place: String, date: String, time: String,fragmentManager: FragmentManager)
        fun onDestroy()
    }
}