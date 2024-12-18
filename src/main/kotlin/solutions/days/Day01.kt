package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution
import kotlin.math.abs

class Day01 : Solution() {
    private fun parseInput(input: String): ArrayList<ArrayList<Int>> {
        val arr1: ArrayList<Int> = ArrayList();
        val arr2: ArrayList<Int> = ArrayList();

        input.lines().forEach { line ->
            val numbers = line.split(Regex("\\s+")).map { it.toInt() }
            arr1.add(numbers[0])
            arr2.add(numbers[1])
        }

        return arrayListOf(arr1, arr2)
    }

    override fun part_1(input: String, test: Boolean): Int {
        val arrPair = parseInput(input);
        val (arr1, arr2) = arrPair;
        arr1.sort();
        arr2.sort();

        var sum = 0;
        for (i in 0 until arr1.size) {
            sum += abs(arr1[i] - arr2[i]);
        }

        return sum;
    }

    override fun part_2(input: String, test: Boolean): Int {
        val arrPair = parseInput(input);
        val (arr1, arr2) = arrPair;

        var sum = 0;
        for (i in 0 until arr1.size) {
            val count = arr2.count { it == arr1[i] };
            sum += (arr1[i] * count);
        }

        return sum;
    }
}
