import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// 벽을 부순 상태인지 확인
enum class Break {
    YES, NO
}

data class Node(val breakState: Break, val row: Int, val col: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (rowSize, colSize) = readLine().split(" ").map(String::toInt)
    val graph = Array<CharArray>(rowSize) {
        readLine().toCharArray()
    }

    print(getMinDistance(graph))
}

fun getMinDistance(graph: Array<CharArray>): Int {
    val rowSize = graph.size
    val colSize = graph[0].size

    // [벽을 부수고/안부수고][row][col]에 도착했을 때 지나온 칸의 개수
    val visited = Array<Array<IntArray>>(2) {
        Array(rowSize) {
            IntArray(colSize)
        }
    }

    val q: Queue<Node> = LinkedList()
    val dR = intArrayOf(-1, 1, 0, 0)
    val dC = intArrayOf(0, 0, -1, 1)

    visited[Break.NO.ordinal][0][0] = 1
    q.add(Node(Break.NO, 0, 0))

    while(q.isNotEmpty()) {
        val node = q.poll()

        if (node.row == rowSize - 1 && node.col == colSize - 1) {
            return visited[node.breakState.ordinal][node.row][node.col]
        }

        for (i in 0 until 4) {
            val nextRow = node.row + dR[i]
            val nextCol = node.col + dC[i]

            if (nextRow !in 0 until rowSize || nextCol !in 0 until colSize) continue

            // 다음 지점이 0이고, 아직 벽을 부수지 않았을 때
            if (graph[nextRow][nextCol] == '0' && node.breakState == Break.NO) {
                // 벽을 부수지 않은 상태에서 한번도 지나가보지 않았을 때만 지나간다.
                if (visited[Break.NO.ordinal][nextRow][nextCol] == 0) {
                    visited[Break.NO.ordinal][nextRow][nextCol] = visited[Break.NO.ordinal][node.row][node.col] + 1
                    q.add(Node(Break.NO, nextRow, nextCol))
                }
            }
            // 다음 지점이 1이고 아직 벽을 부수지 않았을 때
            else if (graph[nextRow][nextCol] == '1' && node.breakState == Break.NO) {
                // 한번도 지나가보지 않았을 때만 지나간다
                if (visited[Break.YES.ordinal][nextRow][nextCol] == 0) {
                    visited[Break.YES.ordinal][nextRow][nextCol] = visited[Break.NO.ordinal][node.row][node.col] + 1
                    q.add(Node(Break.YES, nextRow, nextCol))
                }
            }
            // 다음 지점이 0이고 벽을 부수고 지나왔을 때
            else if (graph[nextRow][nextCol] == '0' && node.breakState == Break.YES) {
                // 처음 지나가는 경우에만 지나간다
                if (visited[Break.YES.ordinal][nextRow][nextCol] == 0 && visited[Break.NO.ordinal][nextRow][nextCol] == 0) {
                    visited[Break.YES.ordinal][nextRow][nextCol] = visited[Break.YES.ordinal][node.row][node.col] + 1
                    q.add(Node(Break.YES, nextRow, nextCol))
                }
            }
        }
    }

    return -1
}