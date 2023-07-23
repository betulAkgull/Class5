package com.example.class5.ui.dailynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.class5.data.model.Note
import com.example.class5.databinding.ItemDailyNoteBinding

class DailyNotesAdapter(
    private val onNoteClick: (String) -> Unit
) :
    RecyclerView.Adapter<DailyNotesAdapter.DailyNoteViewHolder>() {

    private val noteList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DailyNoteViewHolder(
            ItemDailyNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DailyNoteViewHolder, position: Int) =
        holder.bind(noteList[position])

    inner class DailyNoteViewHolder(private val binding: ItemDailyNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding) {
                tvTitle.text = note.title
                tvDate.text = note.date


                root.setOnClickListener {
                    onNoteClick(note.desc)
                }
            }
        }
    }

    fun updateList(list: List<Note>) {
        noteList.clear()
        noteList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount() = noteList.size
}