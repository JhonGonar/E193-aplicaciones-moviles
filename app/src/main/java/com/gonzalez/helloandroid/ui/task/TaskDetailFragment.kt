package com.gonzalez.helloandroid.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gonzalez.helloandroid.data.task.Task
import com.gonzalez.helloandroid.data.task.TaskRepository
import com.gonzalez.helloandroid.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: TaskRepository

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

        repository = TaskRepository(requireContext())

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString().trim()
            val description = binding.etTaskDescription.text.toString().trim()
            val hasReminder = binding.switchReminder.isChecked

            if (title.isEmpty()) {
                binding.tilTaskTitle.error = "El título es obligatorio"
                return@setOnClickListener
            }

            binding.tilTaskTitle.error = null

            val newTask = Task(
                id = System.currentTimeMillis().toInt(),
                title = title,
                description = description,
                hasReminder = hasReminder
            )

            repository.addTask(newTask)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}