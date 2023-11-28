package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.test.databinding.FragmentDetalInfoBinding

class DetalInfoFragment(val position: Int) : Fragment() {
    private val listHolder: ListHolder by activityViewModels()//наши данные

    //для работы viewbinding для фрагмента
    private var _binding: FragmentDetalInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        val event= listHolder.items.value?.get(position)
        binding.editTextName.setText(event?.name ?: "")
        binding.editTextPlace.setText(event?.place ?: "")
        binding.editTextDate.setText(event?.date ?: "")
        binding.editTextTime.setText(event?.time ?: "")

        binding.button.setOnClickListener {
            val fragList =
                parentFragmentManager.findFragmentByTag("listFrag")
            fragList?.let { it1 ->
                parentFragmentManager.beginTransaction()
                    .show(it1)
                    .remove(this)
                    .commit()
            }
        }



        return view
    }


}