/* https://www.acmicpc.net/problem/17836 */
/* BFS or 다익스트라 응용  */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class State(val row: Int, val col: Int, val moveCount: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (rowSize, colSize, moveLimit) = readLine().split(" ").map(String::toInt)
    val graph = Array(rowSize) {
        val st = StringTokenizer(readLine())
        IntArray(colSize) { st.nextToken().toInt() }
    }
    val visited = Array(rowSize) { BooleanArray(colSize) }
    val dRow = intArrayOf(-1, 1, 0, 0)
    val dCol = intArrayOf(0, 0, -1, 1)

    val q: Queue<State> = LinkedList()
    var moveWithGram = moveLimit + 1
    var moveWithoutGram = moveLimit + 1

    q.offer(State(0, 0, 0))
    visited[0][0] = true

    while (q.isNotEmpty()) {
        val (row, col, moveCount) = q.poll()

        // 그람 발견 -> 바로 최단시간 계산 but 한계시간을 넘어설 경우를 대비해서 bfs는 끝까지 진행
        if (graph[row][col] == 2) {
            moveWithGram = moveCount + (rowSize - row - 1) + (colSize - col - 1)
        }

        if (row == rowSize - 1 && col == colSize - 1) {
            moveWithoutGram = moveCount
            break
        }

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            // 그래프를 벗어나거나 제한 시간을 넘을 경우
            if (nextRow !in 0 until rowSize || nextCol !in 0 until colSize || moveCount + 1 > moveLimit) continue
            if (graph[nextRow][nextCol] == 1) continue // 벽일 경우
            if (visited[nextRow][nextCol]) continue // 이전에 해당 상태에서 해당 위치를 방문했었을 경우

            visited[nextRow][nextCol] = true
            q.offer(State(nextRow, nextCol, moveCount + 1))
        }
    }

    // 그램 있든 없든 최단 시간을 초과했을 경우 Fail
    val minMove = moveWithGram.coerceAtMost(moveWithoutGram)
    if (minMove > moveLimit) {
        print("Fail")
    } else {
        print(minMove)
    }
}