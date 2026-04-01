package com.gonzalez.helloandroid.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gonzalez.helloandroid.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString().trim()
            val description = binding.etTaskDescription.text.toString().trim()
            val time = binding.etTaskTime.text.toString().trim()
            val hasReminder = binding.switchReminder.isChecked

            if (title.isEmpty()) {
                binding.tilTaskTitle.error = "El título es obligatorio"
                return@setOnClickListener
            }

            binding.tilTaskTitle.error = null
            Toast.makeText(requireContext(), "Tarea guardada: $title", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
