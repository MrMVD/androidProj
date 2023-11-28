package com.example.test

import androidx.lifecycle.LifecycleOwner

interface BlankContract {
    interface View {
        fun getViewLifecycleOwner(): LifecycleOwner
        fun updateList()
    }

    interface Presenter {
        fun onViewCreated()
        fun onViewDestroyed()
        fun getListHolder(): ListHolder
    }
}

