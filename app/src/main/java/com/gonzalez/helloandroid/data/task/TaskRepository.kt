package com.gonzalez.helloandroid.data.task

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class TaskRepository(context: Context) {

    companion object {
        private const val PREFS_NAME = "tasks_prefs"
        private const val KEY_TASK_LIST = "task_list"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private var tasksInMemory: MutableList<Task> = loadTasksFromPrefs()

    fun getAllTasks(): List<Task> = tasksInMemory.toList()

    fun addTask(task: Task) {
        tasksInMemory.add(task)
        saveTasksToPrefs()
    }

    fun updateTask(updated: Task) {
        val index = tasksInMemory.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            tasksInMemory[index] = updated
            saveTasksToPrefs()
        }
    }

    fun deleteTask(taskId: Int) {
        tasksInMemory.removeAll { it.id == taskId }
        saveTasksToPrefs()
    }

    private fun loadTasksFromPrefs(): MutableList<Task> {
        val json = prefs.getString(KEY_TASK_LIST, null) ?: return mutableListOf()
        return try {
            val jsonArray = JSONArray(json)
            val list = mutableListOf<Task>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Task(
                        id = obj.getInt("id"),
                        title = obj.getString("title"),
                        description = obj.getString("description"),
                        hasReminder = obj.getBoolean("hasReminder")
                    )
                )
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun saveTasksToPrefs() {
        val jsonArray = JSONArray()
        for (task in tasksInMemory) {
            val obj = JSONObject()
            obj.put("id", task.id)
            obj.put("title", task.title)
            obj.put("description", task.description)
            obj.put("hasReminder", task.hasReminder)
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_TASK_LIST, jsonArray.toString()).apply()
    }
}