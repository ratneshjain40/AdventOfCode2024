package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day07 : Solution() {
    private fun parseLine(input: String): Pair<Long, ArrayList<Long>> {
        val parts = input.split(":").map { it.trim() }
        val firstInt = parts[0].toLong()
        val intArray = parts[1].split(" ").map { it.toLong() }
        return Pair(firstInt, intArray as ArrayList<Long>)
    }

    private fun parseInput(input: String): ArrayList<Pair<Long, ArrayList<Long>>> {
        val lines = input.lines()
        val parsedLines = ArrayList<Pair<Long, ArrayList<Long>>>()
        lines.forEach { line ->
            val parsedLine = parseLine(line)
            parsedLines.add(parsedLine)
        }
        return parsedLines
    }

    private fun evaluate1(target: Long, currentVal: Long, arr: ArrayList<Long>, index: Int): Boolean {
        val lastIdx = arr.size - 1;
        if (currentVal == target && index > lastIdx) {
            return true;
        }
        if (index > lastIdx) {
            return false
        }

        val newValue1 = currentVal + arr[index];
        val eval1 = evaluate1(target, newValue1, arr, index + 1)

        val newValue2 = currentVal * arr[index];
        val eval2 = evaluate1(target, newValue2, arr, index + 1)

        return eval1 || eval2
    }

    private fun evaluate2(target: Long, currentVal: Long, arr: ArrayList<Long>, index: Int): Boolean {
        val lastIdx = arr.size - 1;
        if (currentVal == target && index > lastIdx) {
            return true;
        }
        if (index > lastIdx) {
            return false
        }

        val newValue1 = currentVal + arr[index];
        val eval1 = evaluate2(target, newValue1, arr, index + 1)

        val newValue2 = currentVal * arr[index];
        val eval2 = evaluate2(target, newValue2, arr, index + 1)

        val newValue3 = currentVal.toString().plus(arr[index].toString()).toLong()
        val eval3 = evaluate2(target, newValue3, arr, index + 1)

        return eval1 || eval2 || eval3
    }

    override fun part_1(input: String, test: Boolean): Long {
        val parsedInput = parseInput(input)
        val matches = parsedInput.filter { evaluate1(it.first, it.second[0], it.second, 1) }.map { it.first }.sum()
        return matches;
    }

    override fun part_2(input: String, test: Boolean): Long {
        val parsedInput = parseInput(input)
        val matches = parsedInput.filter { evaluate2(it.first, it.second[0], it.second, 1) }.map { it.first }.sum()
        return matches;
    }
}