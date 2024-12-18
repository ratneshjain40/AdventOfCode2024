package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution;

class Day06 : Solution() {
    private data class Point(val x: Int, val y: Int) {
        fun isValid(xSize: Int, ySize: Int): Boolean {
            return (x in 0..<xSize) && (y in 0..<ySize)
        }
    };

    private enum class Direction(val iX: Int, val iY: Int) {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        fun nextPoint(point: Point): Point {
            return Point(point.x + this.iX, point.y + this.iY)
        }

        override fun toString(): String {
            return when (this) {
                UP -> "UP"
                RIGHT -> "RIGHT"
                DOWN -> "DOWN"
                LEFT -> "LEFT"
            }
        }
    }

    private data class Vector(val point: Point, val direction: Direction) {
        fun turnRight(): Vector {
            val newDirection = when (this.direction) {
                Direction.UP -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
            }
            return Vector(this.point, newDirection)
        }

        fun next(): Vector {
            return Vector(this.direction.nextPoint(this.point), this.direction);
        }

        fun toPoint(): Point {
            return this.point
        }
    }

    private fun findLoops(data: ArrayList<ArrayList<Char>>, startPoint: Point, newWall: Point, xSize: Int, ySize: Int): Boolean {
        val startVec = Vector(startPoint, Direction.UP);
        var loopFound = false;
        val steps = HashSet<Vector>();

        val origVal = data[newWall.x][newWall.y]
        data[newWall.x][newWall.y] = '#'
        simulateRun(data, startVec, xSize, ySize) {
            if (steps.contains(it)) {
                loopFound = true
                return@simulateRun false
            }
            steps.add(it)
        }

        data[newWall.x][newWall.y] = origVal
        return loopFound
    }

    private fun simulateRun(
        data: ArrayList<ArrayList<Char>>,
        start: Vector,
        xSize: Int,
        ySize: Int,
        callBack: (Vector) -> Boolean
    ) {
        var curr = start
        while (true) {
            if (!callBack(curr)) {
                break
            }
            val next = curr.next()
            if (!next.point.isValid(xSize, ySize)) {
                break
            }
            if (data[next.point.x][next.point.y] == '#') {
                curr = curr.turnRight()
                continue
            }
            curr = next
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

    override fun part_1(input: String, test: Boolean): Int {
        val data = parseInput(input);
        var startPoint: Point = Point(0, 0);
        val xSize = data.size;
        val ySize = data[0].size;
        for (i in 0 until xSize) {
            for (j in 0 until ySize) {
                if (data[i][j] == '^') {
                    startPoint = Point(i, j)
                    break
                }
            }
        }

        val curr = Vector(startPoint, Direction.UP);
        val steps = HashSet<Point>();
        simulateRun(data, curr, xSize, ySize) {
            steps.add(it.point)
            return@simulateRun true
        }
        return steps.count();
    }

    override fun part_2(input: String, test: Boolean): Int {
        val data = parseInput(input);
        var startPoint: Point = Point(0, 0);

        val xSize = data.size;
        val ySize = data[0].size;
        for (i in 0 until xSize) {
            for (j in 0 until ySize) {
                if (data[i][j] == '^') {
                    startPoint = Point(i, j)
                    break
                }
            }
        }

        val curr = Vector(startPoint, Direction.UP);
        val steps = HashSet<Point>();
        simulateRun(data, curr, xSize, ySize) {
            steps.add(it.point)
            return@simulateRun true
        }

        val loopCount = steps.count { findLoops(data, startPoint, it, xSize, ySize) };
        return loopCount
    }

}