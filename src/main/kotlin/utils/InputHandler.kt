package com.github.ratneshjain40.utils

import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking
import java.io.File

// Constants
private val DATA_DIR = dotenv()["DATA_DIR"]

// Input handler class to manage input fetching and retrieval
object InputHandler {
    private val fileManager: FileManager = FileManager(DATA_DIR)

    fun getInput(day: Int): String {
        val dayStr = dayFromInt(day)
        val inputFilePath = fileManager.getFilePath("input", "day_${dayStr}.txt")

        if (!File(inputFilePath).exists()) {
            runBlocking {
                val data = InputFetcher.fetch(day).trimEnd('\n')
                fileManager.writeFile(inputFilePath, data)
            }
        }
        return fileManager.readFile(inputFilePath)
    }

    fun getTestInput(day: Int): String {
        val dayStr = dayFromInt(day)
        val testFilePath = fileManager.getFilePath("test", "day_${dayStr}.txt")
        return fileManager.readFile(testFilePath)
    }
}