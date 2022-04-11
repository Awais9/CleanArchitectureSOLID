package com.awais.cleanarchitecturesolid.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awais.cleanarchitecturesolid.presentation.interfaces.NoteClickListener
import com.awais.core.data.Note
import com.example.cleanarchitecturesolid.R
import com.example.cleanarchitecturesolid.databinding.NoteItemViewBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteListAdapter(private val listener: NoteClickListener) :
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private var notes = ArrayList<Note>()
    fun setNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[holder.layoutPosition], listener)
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(binding: NoteItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.title
        private val content = binding.content
        private val noteDate = binding.date
        private val layout = binding.noteLayout
        private val delete = binding.deleteIV
        private val wordCount = binding.wordCountTV

        fun bind(note: Note, listener: NoteClickListener) {
            title.text = note.title
            content.text = note.content
            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last update: ${sdf.format(resultDate)}"
            wordCount.text = "Words: ${note.wordCount}"

            layout.setOnClickListener {
                listener.onClick(note)
            }
            delete.setOnClickListener {
                listener.onDelete(note)
            }
        }
    }

}