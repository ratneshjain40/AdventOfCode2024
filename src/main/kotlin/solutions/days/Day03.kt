package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

private enum class Operation(val value: Int) {
    MUL(1),
    DONOT(2),
    DO(3);

    companion object {
        fun fromInt(code: Int): Operation? {
            return entries.find { it.value == code }
        }
    }
}

class Day03 : Solution() {
    private fun parseMul(input: String): ArrayList<Int> {
        val regex2 = Regex("\\d{1,3}");
        val numsMatch = regex2.findAll(input);
        val nums = numsMatch.map { it.value.toInt() };
        return nums.toList() as java.util.ArrayList<Int>;
    }

    private fun parseInput(input: String): ArrayList<ArrayList<Int>> {
        var arr: ArrayList<ArrayList<Int>> = ArrayList();
        val regex1 = Regex("mul\\(\\d{1,3},\\d{1,3}\\)");
        val matches = regex1.findAll(input);
        matches.forEach { match ->
            arr.add(parseMul(match.value));
        }

        return arr
    }

    private fun parseInputPart2(input: String): ArrayList<ArrayList<Int>> {
        var arr: ArrayList<ArrayList<Int>> = ArrayList();
        val regex = Regex("(mul\\(\\d{1,3},\\d{1,3}\\))|(don't\\(\\))|(do\\(\\))");
        val matches = regex.findAll(input);

        var doFlag = true;
        matches.forEach { match ->
            match.groups.forEachIndexed { index, group ->
                if (group != null && index != 0) {
                    when (Operation.fromInt(index)) {
                        Operation.MUL -> {
                            if (doFlag) {
                                val nums = parseMul(match.groupValues[1]);
                                arr.add(nums);
                            }
                        }

                        Operation.DONOT -> {
                            doFlag = false;
                        }

                        Operation.DO -> {
                            doFlag = true;
                        }

                        null -> println("Invalid REGEX group")
                    }
                }
            }

        }

        return arr
    }

    override fun part_1(input: String, test: Boolean): Int {
        val data = parseInput(input);
        return data.sumOf { it[0] * it[1] }
    }

    override fun part_2(input: String, test: Boolean): Int {
        val data = parseInputPart2(input);
        return data.sumOf { it[0] * it[1] }
    }
}