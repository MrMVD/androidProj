package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.test.databinding.FragmentEditRecordBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditRecordFragment(val position: Int) : Fragment() {
    private val listHolder: ListHolder by activityViewModels()//наши данные
    //для работы viewbinding для фрагмента
    private var _binding: FragmentEditRecordBinding? = null
    private val binding get() = _binding!!
    private var nullStr =false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        val event= listHolder.items.value?.get(position) //редактируемая запись
        binding.editTextName.setText(event?.name ?: "")
        binding.editTextPlace.setText(event?.place ?: "")
        binding.editTextDate.setText(event?.date ?: "")
        binding.editTextTime.setText(event?.time ?: "")

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

            //для резульата диалога
            val msg: MutableLiveData<Boolean> = MutableLiveData(false)
            msg.observe(viewLifecycleOwner,
                Observer { items -> if(msg.value==true){chengRec(inputName,inputPlace,inputDate,inputTime)} })

            if(nullStr==true){
                AlertInput(msg).show(childFragmentManager,"alertNullAdd")//вызываем диалоговое окно подтверждения
            }else {chengRec(inputName,inputPlace,inputDate,inputTime)}

        }
        return view
    }

    fun backFrag(){
        val fragList =
            parentFragmentManager.findFragmentByTag("listFrag")
        fragList?.let { it1 ->
            parentFragmentManager.beginTransaction()
                .show(it1)
                .remove(this)
                .commit()
        }
    }

    fun chengRec(inputName:String,inputPlace:String,inputDate:String,inputTime:String)
    {
        val inputEvent =ListHolder.Event(inputName,inputPlace,inputDate,inputTime)
        listHolder.changeRecord(inputEvent,position)
        backFrag()}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
