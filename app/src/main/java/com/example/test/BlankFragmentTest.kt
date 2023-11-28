package com.example.test

import MyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BlankFragmentTest : Fragment(), BlankContract.View {

    private val presenter: BlankContract.Presenter by lazy {
        BlankPresenter(activityViewModels<ListHolder>().value, this)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank_test, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MyAdapter(presenter.getListHolder(), parentFragmentManager)

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View уведомляет презентер, что он создан
        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun updateList() {
        (recyclerView.adapter as MyAdapter).notifyDataSetChanged()
    }

    override fun getViewLifecycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }
}

