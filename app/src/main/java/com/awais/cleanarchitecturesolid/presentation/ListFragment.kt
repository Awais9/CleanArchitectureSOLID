package com.awais.cleanarchitecturesolid.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awais.cleanarchitecturesolid.framework.viewmodel.NoteListViewModel
import com.awais.cleanarchitecturesolid.presentation.adapters.NoteListAdapter
import com.awais.cleanarchitecturesolid.presentation.interfaces.NoteClickListener
import com.awais.core.data.Note
import com.example.cleanarchitecturesolid.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel
    private lateinit var noteListAdapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(NoteListViewModel::class.java)

        notesRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            noteListAdapter = NoteListAdapter(object : NoteClickListener {
                override fun onClick(note: Note) {
                    gotoNoteDetail(note.id)
                }

                override fun onDelete(note: Note) {
                    viewModel.deleteNote(note)
                }
            })
            adapter = noteListAdapter
        }

        addNoteFB.setOnClickListener { gotoNoteDetail() }
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.loading.observe(viewLifecycleOwner) { loading ->
                if (loading) {
                    loadingBar.visibility = View.VISIBLE
                    notesRV.visibility = View.GONE
                } else {
                    loadingBar.visibility = View.GONE
                    notesRV.visibility = View.VISIBLE
                }
            }
            viewModel.allNotes.observe(viewLifecycleOwner) { list ->
                noteListAdapter.setNotes(list.sortedByDescending { it.updateTime })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    private fun gotoNoteDetail(id: Long = 0L) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(id))
    }
}