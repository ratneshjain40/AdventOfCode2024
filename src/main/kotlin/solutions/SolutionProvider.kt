package com.github.ratneshjain40.solutions

import com.github.ratneshjain40.solutions.days.*
import com.github.ratneshjain40.solutions.framework.Solution
import com.github.ratneshjain40.utils.dayFromInt

object SolutionProvider {
    private val map: MutableMap<String, Solution> = mutableMapOf();

    init {
        registerSolution();
    }

    private fun registerSolution() {
        addSolution(1, Day01());
        addSolution(2, Day02());
        addSolution(3, Day03());
        addSolution(4, Day04());
        addSolution(5, Day05());
        addSolution(6, Day06());
        addSolution(7, Day07());
        addSolution(8, Day08());
        addSolution(9, Day09());
        addSolution(10, Day10());
        addSolution(11, Day11());
        addSolution(12, Day12());
        addSolution(13, Day13());
    }

    private fun addSolution(day: Int, solution: Solution) {
        map["day_${dayFromInt(day)}"] = solution
    }

    fun getSolution(day: Int): Solution? {
        return map["day_${dayFromInt(day)}"]
    }
}