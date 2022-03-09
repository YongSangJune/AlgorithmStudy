/* https://www.acmicpc.net/problem/20058 */

/* 그래프 탐색 기반의 구현 문제 
   시작 위치와 상대적 거리를 활용해서 정사각행렬을 회전시키는 것이 핵심
*/

import java.io.*
import java.util.*
import kotlin.math.max

lateinit var firedGraph: Array<IntArray>   // 파이어볼을 시전한 후의 그래프
lateinit var rotatedGraph: Array<IntArray> // 토네이도 후의 그래프
val q: Queue<Coord> = LinkedList()         // 사각형의 시작지점(맨 왼쪽 위)을 담는 큐
var iceSum = 0
var maxMassCount = 0

val dR = intArrayOf(-1, 1, 0, 0)
val dC = intArrayOf(0, 0, -1, 1)

data class Coord(val row: Int, val col: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, _) = readLine().split(" ").map(String::toInt)
    val graphSize = 1.shl(n)

    firedGraph = Array(graphSize) {
        readLine().split(" ").map(String::toInt).toIntArray()
    }
    rotatedGraph = Array(graphSize) { IntArray(graphSize) }
    val cmdArray = readLine().split(" ").map(String::toInt).toIntArray()

    for (cmd in cmdArray) {
        val squareSize = 1.shl(cmd)
        addSquareStartCoord(squareSize) // 모든 사각형의 시작 지점을 큐에 추가
        rotate(squareSize)              // 토네이도
        fire()                          // 파이어볼
    }

    countAnswers()
    println(iceSum)
    println(maxMassCount)
}

fun addSquareStartCoord(squareSize: Int) {
    for (row in firedGraph.indices step squareSize) {
        for (col in firedGraph.indices step squareSize) {
            q.add(Coord(row, col))
        }
    }
}

fun rotate(size: Int) {
    while(q.isNotEmpty()) {
        val startCoord = q.poll()
        val lastCol = startCoord.col + size - 1

        // i, j: 시작 지점으로부터 상대적인 거리
        for (i in 0 until size) {
            for (j in 0 until size) {
                // 90도 회전
                rotatedGraph[startCoord.row + j][lastCol - i] = firedGraph[startCoord.row + i][startCoord.col + j]
            }
        }
    }
}

fun fire() {
    for (row in rotatedGraph.indices) {
        for (col in rotatedGraph.indices) {
            var count = 0

            for (i in 0 until 4) {
                val adjacentRow = row + dR[i]
                val adjacentCol = col + dC[i]

                if (adjacentRow in rotatedGraph.indices && adjacentCol in rotatedGraph.indices && rotatedGraph[adjacentRow][adjacentCol] > 0) {
                    count++
                }
            }

            firedGraph[row][col] = if (count < 3) {
                rotatedGraph[row][col] - 1
            } else{
                rotatedGraph[row][col]
            }
        }
    }
}

fun countAnswers() {
    val visited = Array(firedGraph.size) { BooleanArray(firedGraph.size) }

    for (row in firedGraph.indices) {
        for (col in firedGraph.indices) {
            if (firedGraph[row][col] > 0 && visited[row][col].not()) {

                visited[row][col] = true
                maxMassCount = max(maxMassCount, countIceAndMass(row, col, visited))
            }
        }
    }
}

fun countIceAndMass(row: Int, col: Int, visited: Array<BooleanArray>): Int {
    iceSum += firedGraph[row][col] // 얼음량 추가

    var count = 1 // 덩어리 개수, 시작은 자기 자신만 카운트

    for (i in 0 until 4) {
        val newRow = row + dR[i]
        val newCol = col + dC[i]

        if (newRow in firedGraph.indices && newCol in firedGraph.indices &&
            firedGraph[newRow][newCol] > 0 && visited[newRow][newCol].not()) {

            visited[newRow][newCol] = true
            count += countIceAndMass(newRow, newCol, visited) // 각 방향에서 카운트한 모든 덩어리 개수 추가
        }
    }

    return count
}