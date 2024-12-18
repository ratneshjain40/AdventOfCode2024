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

    private fun timedRunner(block: () -> Number): Number {
        val startTime = System.nanoTime()
        val ans = block()
        val endTime = System.nanoTime()
        val duration = endTime - startTime
        println("Execution time: ${duration / 1_000_000} ms")
        return ans
    }

    fun runSolution(day: Int, part: Part, test: Boolean = false) {
        val input = fetchInput(day, test);
        when (val solution = SolutionProvider.getSolution(day)) {
            null -> println("Solution not found for day $day")
            else -> {
                when (part) {
                    Part.PART_1 -> {
                        val ans = timedRunner { solution.part_1(input, test) }
                        println("Part 1 for day $day is $ans")
                    }

                    Part.PART_2 -> {
                        val ans = timedRunner { solution.part_2(input, test) }
                        println("Part 2 for day $day is $ans")
                    }
                }
            }
        }
    }
}