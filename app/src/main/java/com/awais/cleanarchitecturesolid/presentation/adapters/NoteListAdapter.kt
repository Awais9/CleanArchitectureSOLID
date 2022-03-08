package com.awais.cleanarchitecturesolid.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.awais.cleanarchitecturesolid.presentation.interfaces.NoteClickListener
import com.awais.core.data.Note
import com.example.cleanarchitecturesolid.R
import kotlinx.android.synthetic.main.note_item_view.view.*
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_view, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[holder.layoutPosition], listener)
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.title
        private val content = view.content
        private val noteDate = view.date
        private val layout = view.noteLayout
        private val delete = view.deleteIV
        private val wordCount = view.wordCountTV

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