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

class AddRecordFragment : Fragment(), AddRecordContract.View {

    private val listHolder: ListHolder by activityViewModels()
    private var _binding: FragmentAddRecordBinding? = null
    private val binding get() = _binding!!
    private var presenter: AddRecordContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AddRecordPresenter(this)
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

    override fun addRec(
        inputName: String,
        inputPlace: String,
        inputDate: String,
        inputTime: String
    ) {
        listHolder.addRecord(ListHolder.Event(inputName, inputPlace, inputDate, inputTime))
    }

}



