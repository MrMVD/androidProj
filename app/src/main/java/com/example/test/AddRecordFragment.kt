package com.example.test

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var nullStr =false

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
        val slots = UnderscoreDigitSlotsParser().parseSlots(getString(R.string.date_hint))
        val formatWatcher: FormatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(binding.editTextDate)
        //маска ввода времени
        val slotsTime = UnderscoreDigitSlotsParser().parseSlots(getString(R.string.time_hint))
        val maskTime = MaskImpl.createTerminated(slotsTime)
        val watcherTime: FormatWatcher = MaskFormatWatcher(maskTime)
        watcherTime.installOn(binding.editTextTime)
        //ввод данных
        binding.addButton.setOnClickListener {
           //считывание данных и проверка их наличия
            val inputName =binding.editTextName.text.toString()
            if (inputName.length == 0) {
                binding.editTextName.setError(getString(R.string.null_str))
                nullStr=true
            } else {
                binding.editTextName.setError(null)
            }

            val inputPlace =binding.editTextPlace.text.toString()
            if (inputPlace.length == 0) {
                binding.editTextPlace.setError(getString(R.string.null_str))
                nullStr=true
            } else {
                binding.editTextPlace.setError(null)
            }

            val inputDate =binding.editTextDate.text.toString()
            if (inputDate.length == 0) {
                binding.editTextDate.setError(getString(R.string.null_str))
                nullStr=true
            } else {
                binding.editTextDate.setError(null)
            }

            val inputTime =binding.editTextTime.text.toString()
            if (inputTime.length == 0) {
                binding.editTextTime.setError(getString(R.string.null_str))
                nullStr=true
            } else {
                binding.editTextTime.setError(null)
            }


            fun addRec(){listHolder.addRecord(ListHolder.Event(inputName,inputPlace,inputDate,inputTime))
                val fragList =
                    parentFragmentManager.findFragmentByTag("listFrag")
                fragList?.let { it1 ->
                    parentFragmentManager.beginTransaction()
                        .show(it1)
                        .remove(this)
                        .commit()
                }}

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}