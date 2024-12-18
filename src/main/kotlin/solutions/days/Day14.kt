package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day14 : Solution() {
    private fun getGridSize(test: Boolean): Pair<Int, Int> {
        if (test) {
            return Pair(11, 7)
        }
        return Pair(101, 103)
    }

    private data class Point(val x: Int, val y: Int)

    private fun parseInput(input: String): List<Pair<Point, Point>> {
        return input.lines().map { line ->
            val (p, v) = line.split(" ")
            val (px, py) = p.substringAfter("p=").split(",").map { it.toInt() }
            val (vx, vy) = v.substringAfter("v=").split(",").map { it.toInt() }
            Pair(Point(px, py), Point(vx, vy))
        }
    }

    private fun getFinalPoint(p: Point, v: Point, grid: Pair<Int, Int>, steps: Int): Point {
        val finalPoint = Point(p.x + v.x * steps, p.y + v.y * steps)
        var finalPointX = finalPoint.x % grid.first
        var finalPointY = finalPoint.y % grid.second
        if (finalPointX < 0) {
            finalPointX += grid.first
        }
        if (finalPointY < 0) {
            finalPointY += grid.second
        }
        return Point(finalPointX, finalPointY)
    }

    private fun getQuadrant(p: Point, gridSize: Pair<Int, Int>): Int {
        val (gridWidth, gridHeight) = gridSize
        val centerX = gridWidth / 2
        val centerY = gridHeight / 2

        return when {
            p.x == centerX || p.y == centerY -> 0 // On center axis (either vertical or horizontal)
            p.x > centerX && p.y < centerY -> 1   // Top-right
            p.x <= centerX && p.y < centerY -> 2  // Top-left
            p.x <= centerX && p.y >= centerY -> 3 // Bottom-left
            p.x > centerX && p.y >= centerY -> 4  // Bottom-right
            else -> -1 // Fallback (unlikely)
        }
    }

    private fun printGrid(points: List<Point>, gridSize: Pair<Int, Int>) {
        // if points appears in grid print "#" at that position
        // if not then print an empty space " "
        for (j in 0..<gridSize.second) {
            for (i in 0..<gridSize.first) {
                if (Point(i, j) in points) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }


    private fun floodFill(start: Point, gridSize: Pair<Int, Int>, blockedPoints: Set<Point>): Int {
        val (width, height) = gridSize
        val visited = mutableSetOf<Point>()
        val directions = listOf(
            Point(0, 1),  // Down
            Point(1, 0),  // Right
            Point(0, -1), // Up
            Point(-1, 0)  // Left
        )

        // Check if a point is valid and not visited or blocked
        fun isValid(point: Point): Boolean {
            return point.x in 0 until width &&
                    point.y in 0 until height &&
                    point !in visited &&
                    point !in blockedPoints
        }

        val queue = ArrayDeque<Point>()
        queue.add(start)
        visited.add(start)

        var area = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            area++

            // Explore neighbors
            for (direction in directions) {
                val neighbor = Point(current.x + direction.x, current.y + direction.y)
                if (isValid(neighbor)) {
                    queue.add(neighbor)
                    visited.add(neighbor)
                }
            }
        }

        return area
    }


    override fun part_1(input: String, test: Boolean): Number {
        val points = parseInput(input)
        val gridSize = getGridSize(test);
        println("Grid size $gridSize")
        val finalPointsCounts = arrayListOf(0, 0, 0, 0)
        points.forEach { (p, v) ->
            val fp = getFinalPoint(p, v, gridSize, 100)
            println(fp)
            val q = getQuadrant(fp, gridSize)
            if (q != 0 && q != -1) {
                finalPointsCounts[q - 1] = finalPointsCounts[q - 1] + 1
            }
        }
        println(getQuadrant(Point(4, 5), gridSize))
        var ans = 1;
        for (i in finalPointsCounts) {
            if (i > 0) {
                ans *= i
            }
        }
        println(finalPointsCounts)
        return ans
    }

    private fun moveByStep(points: List<Pair<Point, Point>>, gridSize: Pair<Int, Int>, steps: Int): List<Point> {
         val finalPoints = arrayListOf<Point>()
        points.forEach { (p, v) ->
            val fp = getFinalPoint(p, v, gridSize, steps)
            finalPoints.add(fp)
        }
        return finalPoints
    }

    override fun part_2(input: String, test: Boolean): Number {
        var points = parseInput(input)
        val gridSize = getGridSize(test)
        var steps = 1
        while (true) {
            val finalPoints = moveByStep(points, gridSize, steps)
            // if all points are unique then we are done
            if (finalPoints.distinct().size == finalPoints.size) {
                printGrid(finalPoints, gridSize)
                break
            }
            steps += 1;
        }
        return steps
    }
}