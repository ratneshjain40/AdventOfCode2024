package com.github.ratneshjain40.solutions

import com.github.ratneshjain40.utils.InputHandler
import com.github.ratneshjain40.solutions.framework.Part

object SolutionRunner {
    private fun fetchInput(day: Int, test: Boolean): String {
        return if (test) {
            InputHandler.getTestInput(day)
        } else {
            InputHandler.getInput(day)
        }
    }

    fun runSolution(day: Int, part: Part, test: Boolean = false) {
        val input = fetchInput(day, test);
        when (val solution = SolutionProvider.getSolution(day)) {
            null -> println("Solution not found for day $day")
            else -> {
                when (part) {
                    Part.PART_1 -> {
                        val startTime = System.nanoTime()

                        val ans = solution.part_1(input)

                        val endTime = System.nanoTime()
                        val duration = endTime - startTime
                        println("Part 1 for day $day is $ans")
                        println("Execution time: ${duration / 1_000_000} ms")
                    }

                    Part.PART_2 -> {
                        val startTime = System.nanoTime()

                        val ans = solution.part_2(input)

                        val endTime = System.nanoTime()
                        val duration = endTime - startTime
                        println("Part 2 for day $day is $ans")
                        println("Execution time: ${duration / 1_000_000} ms")
                    }
                }
            }
        }
    }
}