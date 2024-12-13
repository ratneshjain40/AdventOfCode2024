package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution
import kotlin.math.abs

class Day12 : Solution() {
    private fun ArrayList<ArrayList<Char>>.atPoint(point: Point): Char {
        return this[point.x][point.y]
    }

    private data class Point(val x: Int, val y: Int) {
        fun isInBounds(xSize: Int, ySize: Int): Boolean {
            return (x in 0..<xSize) && (y in 0..<ySize)
        }

        fun isValid(data: ArrayList<ArrayList<Char>>, xSize: Int, ySize: Int, char: Char): Boolean {
            return isInBounds(xSize, ySize) && data[this.x][this.y] == char;
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

        fun isNeighbouring(vector: Vector): Boolean {
            if (this.direction == vector.direction) {
                if (abs(this.point.x - vector.point.x) == 1 && this.point.y == vector.point.y) {
                    return true
                }
                if (abs(this.point.y - vector.point.y) == 1 && this.point.x == vector.point.x) {
                    return true
                }
            }
            return false
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

    override fun part_1(input: String): Number {
        val data = parseInput(input)
        val xSize = data.size;
        val ySize = data[0].size;

        val visited = HashSet<Point>()
        var sum = 0;
        for (x in 0..<xSize) {
            for (y in 0..<ySize) {
                if (Point(x, y) in visited) {
                    continue
                }

                var area = 0;
                var peremeter = 0;
                val queue = ArrayList<Point>()
                queue.add(Point(x, y))
                while (queue.isNotEmpty()) {
                    val curr = queue.removeAt(0)
                    val vecs = Vector.fromPoint(curr)
                    visited.add(curr)
                    for (vec in vecs) {
                        val vecPoint = vec.next().point
                        if (!vecPoint.isInBounds(xSize, ySize) || data.atPoint(vecPoint) != data.atPoint(curr)) {
                            peremeter += 1;
                        } else {
                            if (!visited.contains(vecPoint)) {
                                queue.add(vecPoint)
                                visited.add(vecPoint)
                            }
                        }
                    }
                    area += 1
                }
                sum += area * peremeter
            }
        }
        return sum
    }

    private fun scanLayer(
        data: ArrayList<ArrayList<Char>>,
        layer: List<Point>,
        xSize: Int,
        ySize: Int,
        direction: Direction
    ): Int {
        var count = 0;
        val char = data.atPoint(layer[0])

        for (curr in layer) {
            val check = Vector(curr, direction).next().point
            if (!check.isValid(data, xSize, ySize, char)) {
                val prev = if (direction == Direction.UP || direction == Direction.DOWN) {
                    Point(curr.x, curr.y - 1)
                } else {
                    Point(curr.x - 1, curr.y)
                }

                val checkPrev = Vector(prev, direction).next().point
                if (layer.contains(prev) && !checkPrev.isValid(data, xSize, ySize, char)) {
                    continue
                } else {
                    count += 1;
                }
            }
        }

        return count
    }

    private fun printRegion(data: ArrayList<ArrayList<Char>>, region: ArrayList<Point>) {
        val xSize = data.size;
        val ySize = data[0].size;
        // Print whole grid with points other than origin points as "."
        println("\nRegion:")
        for (x in 0..<xSize) {
            for (y in 0..<ySize) {
                if (Point(x, y) in region) {
                    print(data.atPoint(Point(x, y)))
                } else {
                    print(".")
                }
            }
            print("\n")
        }
    }


    override fun part_2(input: String): Number {
        val data = parseInput(input)
        val xSize = data.size;
        val ySize = data[0].size;

        val visited = HashSet<Point>()
        val regions = ArrayList<ArrayList<Point>>()
        for (x in 0..<xSize) {
            for (y in 0..<ySize) {
                if (Point(x, y) in visited) {
                    continue
                }
                val region = ArrayList<Point>()
                val queue = ArrayList<Point>()
                queue.add(Point(x, y))
                visited.add(Point(x, y))
                while (queue.isNotEmpty()) {
                    val curr = queue.removeAt(0)
                    region.add(curr)
                    for (vec in Vector.fromPoint(curr)) {
                        val nbr = vec.next().point
                        if (nbr.isInBounds(xSize, ySize) && data.atPoint(nbr) == data.atPoint(curr)) {
                            if (!visited.contains(nbr)) {
                                queue.add(nbr)
                                visited.add(nbr)
                            }
                        }
                    }
                }
                region.sortWith(compareBy({ it.x }, { it.y }))
                regions.add(region)
            }
        }

        var cost = 0;
        regions.forEach { region ->
            val area = region.size
            var sides = 0;
            val minx = region.minOf { it.x }
            val maxx = region.maxOf { it.x }
            for (x in minx..maxx) {
                val topSides = scanLayer(data, region.filter { it.x == x }, xSize, ySize, Direction.UP)
                val bottomSides = scanLayer(data, region.filter { it.x == x }, xSize, ySize, Direction.DOWN)
                sides += topSides + bottomSides
            }

            val miny = region.minOf { it.y }
            val maxy = region.maxOf { it.y }
            for (y in miny..maxy) {
                val leftSides = scanLayer(data, region.filter { it.y == y }, xSize, ySize, Direction.LEFT)
                val rightSides = scanLayer(data, region.filter { it.y == y }, xSize, ySize, Direction.RIGHT)
                sides += leftSides + rightSides
            }
            cost += area * sides
        }
        return cost
    }
}