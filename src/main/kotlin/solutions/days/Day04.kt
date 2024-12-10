package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day04 : Solution() {
    private data class Point(val x: Int, val y: Int) {
        fun isValid(xSize: Int, ySize: Int): Boolean {
            return (x in 0..<xSize) && (y in 0..<ySize)
        }
    };

    private enum class Direction(val iX: Int, val iY: Int) {
        UP(0, 1),
        RIGHT(1, 0),
        DOWN(0, -1),
        LEFT(-1, 0),
        DIAG_UP_LEFT(-1, 1),
        DIAG_UP_RIGHT(1, 1),
        DIAG_DOWN_LEFT(-1, -1),
        DIAG_DOWN_RIGHT(1, -1);

        fun nextPoint(point: Point): Point {
            return Point(point.x + this.iX, point.y + this.iY)
        }
    }

    private class Vector(val point: Point, val direction: Direction) {
        companion object {
            fun fromPoint(point: Point): ArrayList<Vector> {
                val vectors = ArrayList<Vector>()
                for (direction in Direction.entries) {
                    vectors.add(Vector(point, direction))
                }
                return vectors
            }
        }

        fun next(): Vector {
            return Vector(this.direction.nextPoint(this.point), this.direction);
        }
    }

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

    override fun part_1(input: String): Int {
        val data = parseInput(input);
        var count = 0;
        for (i in 0 until data.size) {
            for (j in 0 until data[i].size) {
                if (data[i][j] == 'X') {
                    val point = Point(i, j)
                    var allNeighbors = Vector.fromPoint(point).filter { it.point.isValid(data[i].size, data.size) }
                    val lettersToCheck = listOf('M', 'A', 'S');
                    for (l in lettersToCheck) {
                        allNeighbors =
                            allNeighbors.map { it.next() }.filter { it.point.isValid(data[i].size, data.size) }
                        allNeighbors = allNeighbors.filter {
                            data[it.point.x][it.point.y] == l;
                        }
                    }
                    count += allNeighbors.size;
                }
            }
        }
        return count;
    }

    override fun part_2(input: String): Int {
        val data = parseInput(input);
        var count = 0;
        val xSize = data[0].size;
        val ySize = data.size;
        for (i in 0 until xSize) {
            col@ for (j in 0 until ySize) {
                if (data[i][j] == 'A') {
                    val point = Point(i, j)
                    val patterns =
                        listOf(
                            Pair(
                                listOf(Direction.DIAG_UP_LEFT, Direction.DIAG_DOWN_RIGHT), mutableSetOf('M', 'S')
                            ),
                            Pair(
                                listOf(Direction.DIAG_DOWN_LEFT, Direction.DIAG_UP_RIGHT), mutableSetOf('M', 'S')
                            )
                        );

                    for (pattern in patterns) {
                        val lettersToCheck = pattern.second;

                        for (direction in pattern.first) {
                            val nextPoint = direction.nextPoint(point)
                            if (nextPoint.isValid(xSize, ySize) && data[nextPoint.x][nextPoint.y] in lettersToCheck) {
                                lettersToCheck.remove(data[nextPoint.x][nextPoint.y])
                            }
                        }
                        if (!lettersToCheck.isEmpty()) {
                            continue@col
                        }
                    }
                    count += 1;
                }
            }
        }

        return count;
    }
}