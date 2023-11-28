import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.test.AddRecordFragment
import com.example.test.AlertDelFrag
import com.example.test.DetalInfoFragment
import com.example.test.EditRecordFragment
import com.example.test.ListHolder
import com.example.test.R
import com.example.test.databinding.AddRecordButtonBinding
import com.example.test.databinding.ViewHolderBinding

class MyAdapter(
    private val list: ListHolder,
    private val myFragmentManager: FragmentManager,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // константы для типов view в списке
    companion object {
        private const val TYPE_RECORD = 0
        private const val TYPE_BUTTON = 1
    }
    //вызывается при создании ячейки для view
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_RECORD -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ViewHolderBinding.inflate(inflater, parent, false)
                RecordViewHolder(binding)
            }

            TYPE_BUTTON -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AddRecordButtonBinding.inflate(inflater, parent, false)
                ButtonViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    //вызывается при проверке размер списка
    override fun getItemCount(): Int {
        return (list.items.value?.size ?: 0) + 1
    }
    //вызывается после onCreateViewHolder для привязки данных к ячейке
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is RecordViewHolder) {
            holder.bind(position)
        } else if (holder is ButtonViewHolder) {
            holder.bind()
        }
    }
    //Определение типа контейнира
    override fun getItemViewType(position: Int): Int {
        return if (position < (list.items.value?.size ?: 0)) {
            TYPE_RECORD
        } else {
            TYPE_BUTTON
        }
    }

    //клас для показа записей
    inner class RecordViewHolder(private val binding: ViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {


            val item = list.items.value?.get(position)
            binding.textView.text = item?.name
            //запуск просмотра
            binding.root.setOnClickListener {
                val fragList = myFragmentManager.findFragmentByTag("listFrag")
                val fragInfo = DetalInfoFragment(position)
                fragList?.let { it1 ->
                    myFragmentManager.beginTransaction()
                        .hide(it1)
                        .add(R.id.fragHolder, fragInfo)
                        .commit()
                }

            }
            //запуск редактирования
            binding.editButton.setOnClickListener {
                val fragList = myFragmentManager.findFragmentByTag("listFrag")
                val fragAdd = EditRecordFragment(position)
                fragList?.let { it1 ->
                    myFragmentManager.beginTransaction()
                        .hide(it1)
                        .add(R.id.fragHolder, fragAdd)
                        .commit()
                }
            }
            //запуск удаления элемента
            binding.delButton.setOnClickListener {
                val msg: MutableLiveData<Boolean> = MutableLiveData(false)
                AlertDelFrag(msg).show(myFragmentManager,"alertDel")
                myFragmentManager.findFragmentByTag("listFrag")?.let { it1 ->
                    msg.observe(
                        it1,
                        Observer { items ->
                            if (msg.value == true) {
                                list.delRecord(position)
                                notifyDataSetChanged()
                            }
                        })
                }
            }
        }
    }
    //класс для показа кнопки добавления записей
    inner class ButtonViewHolder(private val binding: AddRecordButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.addButton.setOnClickListener {
                val fragList = myFragmentManager.findFragmentByTag("listFrag")
                val fragAdd = AddRecordFragment()
                fragList?.let { it1 ->
                    myFragmentManager.beginTransaction()
                        .hide(it1)
                        .add(R.id.fragHolder, fragAdd)
                        .commit()
                }
            }
        }
    }
}