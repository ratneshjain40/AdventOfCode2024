package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

private class RuleCmp(val hashSet: HashSet<String>): Comparator<String> {
    override fun compare(o1: String?, o2: String?): Int {
        return if (hashSet.contains("$o1|$o2")) {
            -1
        } else if (hashSet.contains("$o2|$o1")) {
            1
        } else {
            0
        }
    }
}

class Day05: Solution() {
    private fun parseInput(input: String): Pair<HashSet<String>, ArrayList<ArrayList<String>>> {
        val parts = input.trimIndent().split("\n\n")
        val rulesStr = parts[0]
        val pages = parts[1]


        val hashSet = HashSet<String>();
        for (line in rulesStr.lines()) {
            hashSet.add(line.trimIndent())
        }

        val arr = ArrayList<ArrayList<String>>()
        for (line in pages.lines()) {
            val numbers = line.trimIndent().split(',')
            arr.add(numbers as ArrayList<String>)
        }
        return Pair(hashSet, arr)
    }

    override fun part_1(input: String, test: Boolean): Int {
        val data = parseInput(input)
        var sum = 0;
        for (pages in data.second) {
            val sorted = pages.sortedWith(RuleCmp(data.first))
            if (sorted.zip(pages).any({ it.first != it.second})) {
                continue
            }
            sum += pages[(pages.size - 1) / 2].toInt()
        }
        return sum;
    }

    override fun part_2(input: String, test: Boolean): Int {
        val data = parseInput(input)
        var sum = 0;
        for (pages in data.second) {
            val sorted = pages.sortedWith(RuleCmp(data.first))
            if (sorted.zip(pages).any { it.first != it.second }) {
                sum += sorted[(pages.size - 1) / 2].toInt()
            }
        }
        return sum;
    }
}