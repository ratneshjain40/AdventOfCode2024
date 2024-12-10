package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution
import kotlin.math.abs

class Day02 : Solution() {
    private fun parseInput(input: String): ArrayList<ArrayList<Int>> {
        val arr: ArrayList<ArrayList<Int>> = ArrayList();

        input.lines().forEach { line ->
            val numbers = line.split(Regex("\\s+")).map { it.toInt() }
            arr.add(ArrayList(numbers))
        }

        return arr
    }

    private fun checkArray(arr: ArrayList<Int>): Boolean {;
        val inc: Boolean = arr[1] > arr[0]

        for (i in 0 until arr.size - 1) {
            val diff = abs(arr[i] - arr[i + 1])

            return if (diff in 1..3) {
                if ((inc && arr[i + 1] > arr[i]) || (!inc && arr[i + 1] < arr[i])) {
                    continue
                } else {
                    false
                }
            } else {
                false
            }
        }

        return true
    }

    private fun skipOneAndCheck(arr: ArrayList<Int>): Boolean {
        var currIdx = 0;
        while (currIdx < arr.size) {
            val subArr1 = arr.subList(0, currIdx)
            val subArr2 = arr.subList(currIdx + 1, arr.size)
            val check = checkArray(ArrayList(subArr1.plus(subArr2)));
            if (check) {
                return true;
            }
            currIdx += 1;
        }
        return false;
    }
    
    override fun part_1(input: String): Int {
        val data = parseInput(input);
        val safeCount = data.count { checkArray(it) }
        return safeCount;
    }

    override fun part_2(input: String): Int {
        val data = parseInput(input);
        val safeCount = data.count { checkArray(it) || skipOneAndCheck(it) }
        return safeCount;
    }
}
