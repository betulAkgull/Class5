package com.example.class5.ui.dailynotes

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.class5.R
import com.example.class5.common.showDatePicker
import com.example.class5.common.showTimePicker
import com.example.class5.common.viewBinding
import com.example.class5.data.model.Note
import com.example.class5.data.source.Database
import com.example.class5.databinding.AddAlertDialogDesignBinding
import com.example.class5.databinding.FragmentDailyNoteBinding
import java.util.Calendar

class DailyNoteFragment : Fragment(R.layout.fragment_daily_note) {

    private val binding by viewBinding(FragmentDailyNoteBinding::bind)

    private val dailyNotesAdapter = DailyNotesAdapter(::onNoteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            setData(Database.getDailyNotes())

            fabAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }

    private fun setData(list: List<Note>){
        binding.rvDailyNotes.adapter = dailyNotesAdapter
        dailyNotesAdapter.updateList(Database.getDailyNotes())
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = AddAlertDialogDesignBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()

        val saveTypeList = listOf("Daily", "Bookmark")

        var selectedSaveType = ""

        var selectedDate = ""

        val saveTypeAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            saveTypeList
        )

        with(binding) {

            val calendar = Calendar.getInstance()

            actSaveType.setAdapter(saveTypeAdapter)

            actSaveType.setOnItemClickListener { _, _, position, _ ->
                selectedSaveType = saveTypeList[position]
            }

            switchDate.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    showDatePicker(calendar) { year, month, day ->
                        showTimePicker(calendar) { hour, minute ->
                            selectedDate = "$day.$month.$year - $hour:$minute"
                            tvDate.text = "$day.$month.$year - $hour:$minute"
                            tvDate.visibility = View.VISIBLE
                        }
                    }
                }
            }



            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()

                if (title.isNotEmpty() && desc.isNotEmpty() && selectedSaveType.isNotEmpty()) {
                    Database.addDailyNote(title, desc, selectedDate, selectedSaveType)
                    setData(Database.getDailyNotes())
                    dialog.dismiss()
                }

            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun onNoteClick(desc: String){
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
}