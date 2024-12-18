package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day13 : Solution() {
    data class Coordinates(var x: Long, var y: Long)

    data class ButtonInfo(val buttonA: Coordinates, val buttonB: Coordinates, val prize: Coordinates)

    fun parseInput(input: String): List<ButtonInfo> {
        val buttonInfoList = mutableListOf<ButtonInfo>()

        val regex = Regex(
            """Button A: X\+(\d+), Y\+(\d+)\s+Button B: X\+(\d+), Y\+(\d+)\s+Prize: X=(\d+), Y=(\d+)"""
        )

        val matches = regex.findAll(input)

        for (match in matches) {
            val (buttonAX, buttonAY, buttonBX, buttonBY, prizeX, prizeY) = match.destructured

            val buttonA = Coordinates(buttonAX.toLong(), buttonAY.toLong())
            val buttonB = Coordinates(buttonBX.toLong(), buttonBY.toLong())
            val prize = Coordinates(prizeX.toLong(), prizeY.toLong())

            buttonInfoList.add(ButtonInfo(buttonA, buttonB, prize))
        }

        return buttonInfoList
    }

    private fun solveEquation(A: Coordinates, B: Coordinates, P: Coordinates): Pair<Double, Double> {
        val i = ((P.x * B.y) - (P.y * B.x)).toDouble() / ((A.x * B.y) - (A.y * B.x))
        val j = (P.x - (A.x * i)).toDouble() / B.x
        return Pair(i, j)
    }


    override fun part_1(input: String, test: Boolean): Number {
        val buttonInfoList = parseInput(input)
        var sum = 0;
        buttonInfoList.forEach { buttonInfo ->
            val buttonA = buttonInfo.buttonA
            val buttonB = buttonInfo.buttonB
            val prize = buttonInfo.prize
            val (a, b) = solveEquation(buttonA, buttonB, prize)
            if (a % 1 == 0.0 && b % 1 == 0.0) {
                sum += ((a.toInt() * 3) + b.toInt())
            }
        }
        return sum
    }

    override fun part_2(input: String, test: Boolean): Number {
        val buttonInfoList = parseInput(input)
        var sum = 0L;
        buttonInfoList.forEach { buttonInfo ->
            val buttonA = buttonInfo.buttonA
            val buttonB = buttonInfo.buttonB
            val prize = buttonInfo.prize
            prize.x += 10000000000000
            prize.y += 10000000000000
            val (a, b) = solveEquation(buttonA, buttonB, prize)
            if (a % 1 == 0.0 && b % 1 == 0.0) {
                sum += ((a.toLong() * 3) + b.toLong())
            }
        }
        return sum
    }
}