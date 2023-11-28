package com.example.test

import MyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BlankFragmentTest : Fragment() {
    private val listHolder: ListHolder by activityViewModels()//наши данные

    //запускается при создании фрагмента
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //запускается при создании элементов view (текст,кнопки и тд.)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //подучаем созданную view
        val view = inflater.inflate(R.layout.fragment_blank_test, container, false)
        // если view это наш список то задаём layoutManager и adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyAdapter(listHolder, parentFragmentManager)
                //обновление списка при изменении данных
//                listHolder.items.observe(viewLifecycleOwner,
//                    Observer { items -> (adapter as MyAdapter).notifyDataSetChanged() })
            }
        }
        return view
    }
}
