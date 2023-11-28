package com.example.test

import androidx.lifecycle.Observer

class BlankPresenter(
    private val listHolder: ListHolder,
    private val view: BlankContract.View
) : BlankContract.Presenter {

    private val observer = Observer<List<ListHolder.Event>> { items ->
        view.updateList()
    }

    override fun onViewCreated() {
        // Обновление списка при изменении данных
        listHolder.items.observeForever(observer)

    }

    override fun onViewDestroyed() {
        // Остановка наблюдения за изменениями данных
        listHolder.items.removeObserver(observer)
    }

    override fun getListHolder(): ListHolder {
        return listHolder
    }
}
