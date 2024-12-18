package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day11 : Solution() {

    fun parseInput(input: String): ArrayList<Long> {
        val lines = input.lines()
        val numbers = ArrayList<Long>()
        for (line in lines) {
            val nums = line.split(" ").map { it.toLong() }
            numbers.addAll(nums)
        }
        return numbers
    }

    private fun splitStone(value: String): Pair<String, String> {
        val middle = value.length / 2
        val firstHalf = value.substring(0, middle)
        val secondHalf = value.substring(middle)
        return Pair(firstHalf, secondHalf)
    }


    fun memoizeScoreStones(): (ArrayList<Long>, Int) -> Long {
        val cache = mutableMapOf<Pair<List<Long>, Int>, Long>()

        return { stones, times ->
            val key = stones.toList() to times
            cache.getOrPut(key) {
                if (times < 0) {
                    1
                } else if (stones.size == 1) {
                    val stone = stones[0]
                    if (stone.toInt() == 0) {
                        scoreStonesMemoized(arrayListOf(1), times - 1)
                    } else if (stone.toString().length % 2 == 0) {
                        val pair = splitStone(stone.toString())
                        val firstHalf = pair.first.toLong()
                        val secondHalf = pair.second.toLong()
                        scoreStonesMemoized(arrayListOf(firstHalf), times - 1) +
                                scoreStonesMemoized(arrayListOf(secondHalf), times - 1)
                    } else {
                        scoreStonesMemoized(arrayListOf(stone * 2024), times - 1)
                    }
                } else {
                    stones.sumOf { scoreStonesMemoized(arrayListOf(it), times - 1) }
                }
            }
        }
    }

    // Global memoized instance
    val scoreStonesMemoized = memoizeScoreStones()

    override fun part_1(input: String, test: Boolean): Number {
        val stones = parseInput(input)
        return scoreStonesMemoized(stones.map { it.toInt() }.toList() as ArrayList<Long>, 25)
    }

    private fun applyRules(uniqueStones: HashMap<Long, Long>): HashMap<Long, Long> {
        val map = HashMap<Long, Long>()
        uniqueStones.forEach { (key, value) ->
            if (key.toInt() == 0) {
                map[1] = map.getOrPut(key) { 0 }.plus(value)
            } else if (key.toString().length % 2 == 0) {
                val pair = splitStone(key.toString())
                val firstHalf = pair.first.toLong()
                val secondHalf = pair.second.toLong()
                map[firstHalf] = map.getOrPut(firstHalf) { 0 }.plus(value)
                map[secondHalf] = map.getOrPut(secondHalf) { 0 }.plus(value)
            } else {
                val newStone = key * 2024;
                map[newStone] = map.getOrPut(newStone) { 0 }.plus(value)
            }
        }
        return map
    }

    override fun part_2(input: String, test: Boolean): Number {
        val stones = parseInput(input)
        var uniqueStones = HashMap<Long, Long>()
        stones.forEach { uniqueStones[it] = 1 }

        repeat(75) {
            uniqueStones = applyRules(uniqueStones)
        }

        return uniqueStones.filter { it.value > 0 }.values.sum()
    }
}
