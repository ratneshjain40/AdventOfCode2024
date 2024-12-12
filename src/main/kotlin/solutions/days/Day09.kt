package com.github.ratneshjain40.solutions.days

import com.github.ratneshjain40.solutions.framework.Solution

class Day09 : Solution() {
    private fun parseInput(input: String): ArrayList<Long> {
        val lines = input.lines()
        val numbers = ArrayList<Long>()
        for (line in lines) {
            val nums = line.iterator().asSequence().map { it.toString().toLong() }.toList()
            numbers.addAll(nums)
        }
        return numbers
    }

    fun ArrayList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    private fun ArrayList<Int>.findFrom(index: Int, value: Int): Int {
        for (i in index until this.size) {
            if (this[i] == value) {
                return i
            }
        }
        return -1;
    }

    private fun ArrayList<Int>.findValTill(fromIndex: Int, toIndex: Int, value: Int): Int {
        for (i in fromIndex + 1..toIndex) {
            if (this[i] != value) {
                return i - 1;
            }
        }
        return -1;
    }

    private fun ArrayList<Int>.lastPositive(index: Int): Int {
        // Check from index in reverse
        for (i in index downTo 0) {
            if (this[i] >= 0) {
                return i
            }
        }
        return -1;
    }

    private fun ArrayList<Int>.lastValFrom(index: Int, value: Int): Int {
        // Check from index in reverse
        var j = index;
        for (i in index - 1 downTo 0) {
            if (this[i] == value) {
                j -= 1
            } else {
                return j
            }
        }
        return j;
    }

    private fun iterLastFile(fromIndex: Int, arr: ArrayList<Int>): Pair<Int, Int> {
        val j = arr.lastPositive(fromIndex)
        val i = arr.lastValFrom(j, arr[j])
        return Pair(i, j)
    }

    private fun iterFreeSpace(fromIndex: Int, arr: ArrayList<Int>): Pair<Int, Int> {
        val i = arr.findFrom(fromIndex, -1)
        val j = arr.findValTill(i, arr.size - 1, -1)
        return Pair(i, j)
    }

    private fun overwriteFreeSpace(file: Pair<Int, Int>, freeSpace: Pair<Int, Int>, arr: ArrayList<Int>) {
        var j = freeSpace.first
        for (i in file.first..file.second) {
            arr.swap(i, j)
            j += 1
        }
    }

    override fun part_1(input: String): Number {
        val numbers = parseInput(input)
        val arr = ArrayList<Int>()
        var isFile = true;
        numbers.forEachIndexed { index, i ->
            if (isFile) {
                val id = index / 2;
                repeat(i.toInt()) {
                    arr.add(id)
                }
                isFile = false
            } else {
                repeat(i.toInt()) {
                    arr.add(-1)
                }
                isFile = true
            }
        }
        var i = 0;
        var j = arr.size - 1;
        while (i < j) {
            i = arr.findFrom(i, -1)
            j = arr.lastPositive(j)
            arr.swap(i, j);
            i += 1;
            j -= 1;
        }

        val subArr = arr.filter {
            it >= 0
        }
        var sum: Long = 0;
        subArr.forEachIndexed { index, value ->
            sum += (index * value)
        }
        return sum
    }

    override fun part_2(input: String): Number {
        val numbers = parseInput(input)
        val arr = ArrayList<Int>()
        var isFile = true;
        numbers.forEachIndexed { index, i ->
            if (isFile) {
                val id = index / 2;
                repeat(i.toInt()) {
                    arr.add(id)
                }
                isFile = false
            } else {
                repeat(i.toInt()) {
                    arr.add(-1)
                }
                isFile = true
            }
        }

        val shiftedFiles = HashSet<Pair<Int, Int>>()
        var j = arr.size - 1;
        while (true) {
            val file = iterLastFile(j, arr)
            if (file in shiftedFiles) {
                j = file.first - 1
                continue
            }

            var i = 0
            while (true) {
                val freeSpace = iterFreeSpace(i, arr)
                if (freeSpace.first >= file.first) {
                    break
                }
                if (freeSpace.second - freeSpace.first >= file.second - file.first) {
                    overwriteFreeSpace(file, freeSpace, arr)
                    shiftedFiles.add(file)
                    break
                } else {
                    i = freeSpace.second + 1
                }
            }
            j = file.first - 1
            if (j <= 0) {
                break
            }
        }

        var sum: Long = 0;
        arr.forEachIndexed { index, value ->
            if (value != -1) {
                sum += (index * value)
            }
        }
        return sum
    }

}