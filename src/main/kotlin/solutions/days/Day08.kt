package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day08 : Solution() {
    data class Point(val x: Int, val y: Int) {
        fun isValid(xSize: Int, ySize: Int): Boolean {
            return (x in 0..<xSize) && (y in 0..<ySize)
        }

        operator fun minus(point: Point): Point {
            return Point(this.x - point.x, this.y - point.y)
        }

        operator fun plus(point: Point): Point {
            return Point(this.x + point.x, this.y + point.y)
        }
    };

    private fun parseInput(input: String): ArrayList<ArrayList<Char>> {
        val arr = ArrayList<ArrayList<Char>>()
        input.lines().forEach { line ->
            val arrChar = ArrayList<Char>()
            line.forEach { char ->
                arrChar.add(char)
            }
            arr.add(arrChar)
        }
        return arr
    }

    private fun forEachChar(data: ArrayList<ArrayList<Char>>, callBack: (Point, Char) -> Unit) {
        data.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char != '.') {
                    val point = Point(x, y)
                    callBack(point, char)
                }
            }
        }
    }


    override fun part_1(input: String): Number {
        val data = parseInput(input)
        val xSize = data.size;
        val ySize = data[0].size;

        val nodes = HashSet<Point>()
        forEachChar(data) { point1, char1 ->
            forEachChar(data) { point2, char2 ->
                if (char2 == char1 && point1 != point2) {
                    val diff = point1 - point2
                    val antiNode = point1 + diff
                    if (antiNode.isValid(xSize, ySize)) {
                        nodes.add(antiNode)
                    }
                }
            }
        }
        return nodes.size
    }

    override fun part_2(input: String): Number {
        val data = parseInput(input)
        val xSize = data.size;
        val ySize = data[0].size;

        val nodes = HashSet<Point>()
        forEachChar(data) { point1, char1 ->
            nodes.add(point1)
            forEachChar(data) { point2, char2 ->
                if (char2 == char1 && point1 != point2) {
                    val diff = point1 - point2
                    var antiNode = point1 + diff
                    while (antiNode.isValid(xSize, ySize)) {
                        nodes.add(antiNode)
                        antiNode += diff
                    }
                }
            }
        }
        return nodes.size
    }
}
