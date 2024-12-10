package com.github.ratneshjain40.utils

import java.io.File

class FileManager(private val dataDir: String) {
    fun readFile(fileName: String): String {
        val file = File(fileName)
        val txt = file.readText()
        return txt
    }

    fun writeFile(fileName: String, content: String) {
        val file = File(fileName)
        file.writeText(content)
    }

    fun getFilePath(subDir: String, fileName: String): String {
        return "$dataDir\\$subDir\\$fileName"
    }
}