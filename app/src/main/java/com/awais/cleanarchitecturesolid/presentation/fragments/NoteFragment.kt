package com.awais.cleanarchitecturesolid.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.awais.cleanarchitecturesolid.framework.viewmodel.NoteViewModel
import com.awais.cleanarchitecturesolid.presentation.base.BaseFragment
import com.awais.cleanarchitecturesolid.utils.Utils
import com.awais.core.data.Note
import com.example.cleanarchitecturesolid.databinding.FragmentNoteBinding
import com.google.android.material.snackbar.Snackbar

class NoteFragment : BaseFragment<FragmentNoteBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNoteBinding
        get() = FragmentNoteBinding::inflate

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0, 1)
    private var noteId: Long = 0
    private val args: NoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(NoteViewModel::class.java)

        binding.apply {
            checkButton.setOnClickListener {
                val title = titleET.text.toString().trim()
                val content = contentET.text.toString().trim()
                if (title.isNullOrEmpty() && content.isNullOrEmpty()) {
                    Snackbar.make(root, "Please enter note", Snackbar.LENGTH_SHORT).show()
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
                binding.apply {
                    if (saved) {
                        Utils.hideKeyboard(requireContext(), titleET)
                        titleET.setText("")
                        contentET.setText("")
                        currentNote = Note("", "", 0, 0)
                        titleET.requestFocus()
                        Snackbar.make(root, "Note saved successfully!", Snackbar.LENGTH_SHORT)
                            .setAction("Close") { findNavController().popBackStack() }.show()
                    } else {
                        Utils.hideKeyboard(requireContext(), titleET)
                        Snackbar.make(
                            root, "Something went wrong while saving note", Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            viewModel.note.observe(viewLifecycleOwner) { note ->
                binding.apply {
                    if (note != null) {
                        currentNote = note
                        titleET.setText(note.title)
                        contentET.setText(note.content)
                    }
                }
            }

        }
    }

}