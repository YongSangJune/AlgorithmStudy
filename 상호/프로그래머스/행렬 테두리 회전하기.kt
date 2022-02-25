/* https://programmers.co.kr/learn/courses/30/lessons/77485 */

/* 이번 풀이 */
import kotlin.math.min

class Solution {
    fun solution(rows: Int, columns: Int, queries: Array<IntArray>): IntArray {
        val matrix = Array(rows) { i ->
            IntArray(columns) { j ->
                i * columns + j + 1
            }
        }

        return queries.map {
            getMinValue(matrix, intArrayOf(it[0]-1, it[1]-1, it[2]-1, it[3]-1))
        }.toIntArray()
    }

    /**
     * (r1, c1)을 따로 빼고 반시계 방향으로 한 칸씩 당긴다
     */
    private fun getMinValue(matrix: Array<IntArray>, query: IntArray): Int {
        val (r1, c1, r2, c2) = query
        
        var minValue = matrix[r1][c1] 
        val temp = matrix[r1][c1]

        for (r in r1 until r2) {
            minValue = min(minValue, matrix[r+1][c1])
            matrix[r][c1] = matrix[r+1][c1]
        }

        for (c in c1 until c2) {
            minValue = min(minValue, matrix[r2][c+1])
            matrix[r2][c] = matrix[r2][c+1]
        }

        for (r in r2 downTo r1+1) {
            minValue = min(minValue, matrix[r-1][c2])
            matrix[r][c2] = matrix[r-1][c2]
        }

        for (c in c2 downTo c1+2) {
            minValue = min(minValue, matrix[r1][c-1])
            matrix[r1][c] = matrix[r1][c-1]
        }
        matrix[r1][c1+1] = temp

        return minValue
    }
}

/* 예전 풀이 */
import kotlin.math.min

class Solution {
    fun solution(rows: Int, columns: Int, queries: Array<IntArray>): IntArray {
        var answer = IntArray(queries.size)
        val graph = Array(rows + 1) { i ->
            IntArray(columns + 1) { j ->
                columns * (i - 1) + j
            }
        }

        for (i in queries.indices) {
            val query = queries[i]
            answer[i] = rotateGraph(graph, query)
        }

        return answer
    }

    private fun rotateGraph(graph: Array<IntArray>, query: IntArray): Int {
        val x1 = query[0]
        val y1 = query[1]
        val x2 = query[2]
        val y2 = query[3]

        var prevValue = graph[x1][y1]
        var minValue = prevValue

        for (i in 1..y2 - y1){
            val y = y1 + i
            val temp = graph[x1][y]
            graph[x1][y] = prevValue
            prevValue = temp
            minValue = min(minValue, prevValue)
        }
        for (i in 1..x2 - x1) {
            val x = x1 + i
            val temp = graph[x][y2]
            graph[x][y2] = prevValue
            prevValue = temp
            minValue = min(minValue, prevValue)
        }
        for (i in 1..y2 - y1) {
            val y = y2 - i
            val temp = graph[x2][y]
            graph[x2][y] = prevValue
            prevValue = temp
            minValue = min(minValue, prevValue)
        }
        for (i in 1..x2 - x1) {
            val x = x2 - i
            val temp = graph[x][y1]
            graph[x][y1] = prevValue
            prevValue = temp
            minValue = min(minValue, prevValue)
        }

        return minValue
    }
}