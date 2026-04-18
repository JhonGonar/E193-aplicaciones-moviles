package com.gonzalez.helloandroid.ui.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gonzalez.helloandroid.data.task.Task
import com.gonzalez.helloandroid.data.task.TaskRepository
import com.gonzalez.helloandroid.databinding.FragmentTaskDetailBinding
import com.gonzalez.helloandroid.receiver.TaskReminderReceiver
import java.util.Calendar

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: TaskRepository
    private var selectedTime: String? = null
    private var editingTask: Task? = null

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

        val taskId = arguments?.getInt("task_id")
        if (taskId != null) {
            editingTask = repository.getAllTasks().find { it.id == taskId }
            editingTask?.let { loadTaskForEditing(it) }
            binding.tvDetailTitle.text = "Editar Tarea"
            binding.btnSaveTask.text = "Actualizar"
        } else {
            binding.tvDetailTitle.text = "Nueva Tarea"
            binding.btnSaveTask.text = "Guardar"
        }

        binding.btnSelectTime.setOnClickListener {
            openTimePicker()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString().trim()
            val description = binding.etTaskDescription.text.toString().trim()
            val hasReminder = binding.switchReminder.isChecked

            if (title.isEmpty()) {
                binding.tilTaskTitle.error = "El título es obligatorio"
                return@setOnClickListener
            }

            binding.tilTaskTitle.error = null

            if (editingTask != null) {
                // Update existing task
                val updatedTask = editingTask!!.copy(
                    title = title,
                    description = description,
                    hasReminder = hasReminder,
                    reminderTime = selectedTime
                )
                repository.updateTask(updatedTask)
            } else {
                // Create new task
                val newTask = Task(
                    id = System.currentTimeMillis().toInt(),
                    title = title,
                    description = description,
                    hasReminder = hasReminder,
                    reminderTime = selectedTime
                )
                repository.addTask(newTask)

                if (hasReminder && selectedTime != null) {
                    scheduleReminder(newTask)
                }
            }

            findNavController().navigateUp()
        }
    }

    private fun loadTaskForEditing(task: Task) {
        binding.etTaskTitle.setText(task.title)
        binding.etTaskDescription.setText(task.description)
        binding.switchReminder.isChecked = task.hasReminder
        selectedTime = task.reminderTime
        if (selectedTime != null) {
            binding.btnSelectTime.text = selectedTime
        }
    }

    private fun openTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.btnSelectTime.text = selectedTime
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun scheduleReminder(task: Task) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), TaskReminderReceiver::class.java).apply {
            putExtra("task_title", task.title)
            putExtra("task_id", task.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + 30000 // 30 segundos de delay

        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}