package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day10: Solution() {
    private data class Point(val x: Int, val y: Int) {
        fun isValid(xSize: Int, ySize: Int): Boolean {
            return (x in 0..<xSize) && (y in 0..<ySize)
        }
    };

    private fun findNeighbours(data: ArrayList<ArrayList<Int>>, fromPoint: Point): ArrayList<Point> {
        var neighbours = ArrayList<Point>()
        val xSize = data.size;
        val ySize = data[0].size;
        val x = fromPoint.x
        val y = fromPoint.y

        var point = Point(x + 1, y)
        if (point.isValid(xSize, ySize)) {
            neighbours.add(point)
        }
        point = Point(x - 1, y)
        if (point.isValid(xSize, ySize)) {
            neighbours.add(point)
        }
        point = Point(x, y + 1)
        if (point.isValid(xSize, ySize)) {
            neighbours.add(point)
        }
        point = Point(x, y - 1)
        if (point.isValid(xSize, ySize)) {
            neighbours.add(point)
        }

        neighbours = neighbours.filter { neighbour ->
            data.atPoint(neighbour) - data.atPoint(fromPoint) == 1
        } as ArrayList<Point>
        return neighbours
    }

    private fun ArrayList<ArrayList<Int>>.atPoint(point: Point): Int {
        return this[point.x][point.y]
    }

    private fun parseInput(input: String): ArrayList<ArrayList<Int>> {
        val arr = ArrayList<ArrayList<Int>>()
        input.lines().forEach { line ->
            val arrChar = ArrayList<Int>()
            line.forEach { char ->
                arrChar.add(char.digitToInt())
            }
            arr.add(arrChar)
        }
        return arr
    }

    override fun part_1(input: String, test: Boolean): Number {
        val data = parseInput(input)
        var score = HashMap<Point, HashSet<Point>>()
        for (x in 0..<data.size) {
            for (y in 0..<data[0].size) {
                if (data.atPoint(Point(x, y)) == 0) {
                    val origin = Point(x, y)
                    val queue = ArrayList<Point>()
                    queue.add(Point(x, y))
                    while (queue.isNotEmpty()) {
                        val point = queue.removeAt(0)
                        val neighbours = findNeighbours(data, point)

                        neighbours.forEach { it ->
                            if (data.atPoint(it) == 9) {
                                if (score.contains(origin)) {
                                    score[origin]?.add(it);
                                } else {
                                    score[origin] = HashSet<Point>()
                                    score[origin]?.add(it)
                                }
                            }
                        }
                        if (neighbours.isNotEmpty()) {
                            queue.addAll(neighbours)
                        }
                    }
                }
            }
        }

        var count = 0;
        score.forEach { (_, endpoints) ->
            count += endpoints.size
        }
        return count;
    }

    override fun part_2(input: String, test: Boolean): Number {
        val data = parseInput(input)
        var score = 0;
        for (x in 0..<data.size) {
            for (y in 0..<data[0].size) {
                if (data.atPoint(Point(x, y)) == 0) {
                    var count = 0;
                    val queue = ArrayList<Point>()
                    queue.add(Point(x, y))
                    while (queue.isNotEmpty()) {
                        val point = queue.removeAt(0)
                        val neighbours = findNeighbours(data, point)

                        neighbours.forEach { it ->
                            if (data.atPoint(it) == 9) {
                                count += 1;
                            }
                        }
                        if (neighbours.isNotEmpty()) {
                            queue.addAll(neighbours)
                        }
                    }
                    score += count;
                }
            }
        }
        return score;
    }
}