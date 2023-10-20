package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.databinding.FragmentAddRecordBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class AddRecordFragment() : Fragment() {
    private val listHolder: ListHolder by activityViewModels()//наши данные

    //для работы viewbinding для фрагмента
    private var _binding: FragmentAddRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        //маска ввода даты
        val slots = UnderscoreDigitSlotsParser().parseSlots("____-__-__")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.editTextDate)
        //маска ввода времени
        val slotsTime = UnderscoreDigitSlotsParser().parseSlots("__:__")
        val maskTime = MaskImpl.createTerminated(slotsTime)
        val watcherTime: FormatWatcher = MaskFormatWatcher(maskTime)
        watcherTime.installOn(binding.editTextTime)


        binding.addButton.setOnClickListener {





            listHolder.addRecord(ListHolder.Event("1",null,null,null))
            //находим фрагмент в менеджере по его тегу
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}