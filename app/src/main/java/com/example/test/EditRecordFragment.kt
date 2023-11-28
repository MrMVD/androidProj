package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.test.databinding.FragmentEditRecordBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditRecordFragment(val position: Int) : Fragment(), EditRecordContract.View  {
    private val listHolder: ListHolder by activityViewModels()//наши данные
    //для работы viewbinding для фрагмента
    private var _binding: FragmentEditRecordBinding? = null
    private val binding get() = _binding!!
    private var presenter: EditRecordContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditRecordBinding.inflate(inflater, container, false)
        val event= listHolder.items.value?.get(position) //редактируемая запись
        binding.editTextName.setText(event?.name ?: "")
        binding.editTextPlace.setText(event?.place ?: "")
        binding.editTextDate.setText(event?.date ?: "")
        binding.editTextTime.setText(event?.time ?: "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EditRecordPresenter(this)
        setupUI()
    }

    private fun setupUI() {
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

        binding.addButton.setOnClickListener {
            //считывание данных и проверка их наличия
            val inputName = binding.editTextName.text.toString()
            val inputPlace = binding.editTextPlace.text.toString()
            val inputDate = binding.editTextDate.text.toString()
            val inputTime = binding.editTextTime.text.toString()

            presenter?.onAddButtonClick(
                inputName,
                inputPlace,
                inputDate,
                inputTime,
                childFragmentManager
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter?.onDestroy()
        presenter = null
    }

    // Реализация методов AddRecordContract.View
    override fun showNameError() {
        binding.editTextName.error = getString(R.string.null_str)
    }

    override fun showPlaceError() {
        binding.editTextPlace.error = getString(R.string.null_str)
    }

    override fun showDateError() {
        binding.editTextDate.error = getString(R.string.null_str)
    }

    override fun showTimeError() {
        binding.editTextTime.error = getString(R.string.null_str)
    }

    override fun onRecordAdded() {
        val fragList =
            parentFragmentManager.findFragmentByTag("listFrag")
        fragList?.let { it1 ->
            parentFragmentManager.beginTransaction()
                .show(it1)
                .remove(this)
                .commit()
        }
    }

    override fun editRec(
        inputName: String,
        inputPlace: String,
        inputDate: String,
        inputTime: String
    ) {
        val inputEvent =ListHolder.Event(inputName,inputPlace,inputDate,inputTime)
        listHolder.changeRecord(inputEvent,position)
    }

}







