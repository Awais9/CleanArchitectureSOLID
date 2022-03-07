package com.awais.cleanarchitecturesolid.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.awais.cleanarchitecturesolid.framework.viewmodel.NoteViewModel
import com.awais.cleanarchitecturesolid.utils.Utils
import com.awais.core.data.Note
import com.example.cleanarchitecturesolid.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0, 1)
    private var noteId: Long = 0
    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(NoteViewModel::class.java)

        checkButton.setOnClickListener {
            val title = titleET.text.toString().trim()
            val content = contentET.text.toString().trim()
            if (title.isNullOrEmpty() && content.isNullOrEmpty()) {
                Snackbar.make(mainView, "Please enter note", Snackbar.LENGTH_SHORT).show()
            } else {
                val time = System.currentTimeMillis()
                currentNote.title = title
                currentNote.content = content
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            }
        }
        observeViewModel()
        noteId = args.noteId
        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.saved.observe(viewLifecycleOwner) { saved ->
                if (saved) {
                    Utils.hideKeyboard(requireContext(), titleET)
                    titleET.setText("")
                    contentET.setText("")
                    currentNote = Note("", "", 0, 0)
                    titleET.requestFocus()
                    Snackbar.make(mainView, "Note saved successfully!", Snackbar.LENGTH_SHORT)
                        .setAction("Close") { findNavController().popBackStack() }.show()
                } else {
                    Utils.hideKeyboard(requireContext(), titleET)
                    Snackbar.make(
                        mainView, "Something went wrong while saving note", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            viewModel.note.observe(viewLifecycleOwner) { note ->
                if (note != null) {
                    currentNote = note
                    titleET.setText(note.title)
                    contentET.setText(note.content)
                }
            }

        }
    }

}