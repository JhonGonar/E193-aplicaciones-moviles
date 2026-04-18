package com.gonzalez.helloandroid.data.task

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val hasReminder: Boolean,
    val reminderTime: String? = null
)