import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class Coord(val row: Int, val col: Int) // 좌표

lateinit var graph: Array<IntArray>
lateinit var prevGraph: Array<IntArray>
val dR = intArrayOf(-1, 1, 0, 0)
val dC = intArrayOf(0, 0, -1, 1)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (rowSize, colSize) = readLine().split(" ").map(String::toInt)
    val q: Queue<Coord> = LinkedList()
    prevGraph = Array(rowSize){ IntArray(colSize) } // 매해마다 빙하를 녹이기 전 상태를 저장
    graph = Array(rowSize) { row ->
        val st = StringTokenizer(readLine())

        IntArray(colSize) { col ->
            st.nextToken().toInt().also {
                if (it > 0) {
                    q.add(Coord(row, col))
                }

                prevGraph[row][col] = it
            }
        }
    }

    print(getPassedYear(q))
}

fun getPassedYear(q: Queue<Coord>): Int {
    var year = 0

    // 빙산이 중간에 끊어지지 않고 하나만 남게 되는 경우엔 빙산이 분리되지 않는 상황이다
    while(q.size > 1) {
        melt(q)
        year++

        if (isSeparated()) {
            return year
        }

        copyGraph(prevGraph, graph) // 다음 반복에서 빙하 녹이기 전에 상태 저장
    }

    return 0
}

// 빙산을 녹인다. 한 빙산의 4면이 모두 바다라면 true를 반환한다.
// 4면이 모두 바다라면, 이전 isSeparated()에서 분리된 것이다
fun melt(q: Queue<Coord>): Boolean {
    val size = q.size

    repeat(size) {
        val coord = q.poll()

        for (i in 0 until 4){
            val newRow = coord.row + dR[i]
            val newCol = coord.col + dC[i]

            if (newRow !in graph.indices || newCol !in graph[0].indices) continue
            if (prevGraph[newRow][newCol] > 0) continue // 옆이 빙산이면

            graph[coord.row][coord.col] -= 1 // 옆이 바다일 경우
        }

        // 아직 빙산이면 다시 큐에 추가
        if (graph[coord.row][coord.col] > 0)
            q.add(coord)
    }

    return false
}

fun isSeparated(): Boolean {
    var count = 0
    val visited = Array(graph.size) {
        BooleanArray(graph[0].size)
    }

    for (row in graph.indices) {
        for (col in graph[0].indices) {
            if (graph[row][col] > 0 && visited[row][col].not()) {
                // 다른 덩어리가 또 있을 경우
                if (count == 1) {
                    return true
                }

                visited[row][col] = true
                dfs(row, col, visited)
                count = 1
            }
        }
    }

    // 중간에 리턴이 안됐으면 덩어리가 한개라는 의미
    return false
}

fun dfs(row: Int, col: Int, visited: Array<BooleanArray>) {
    for (i in 0 until 4) {
        val newRow = row + dR[i]
        val newCol = col + dC[i]

        if (newRow !in graph.indices || newCol !in graph[0].indices) continue
        if (visited[newRow][newCol] || graph[newRow][newCol] <= 0) continue

        visited[newRow][newCol] = true
        dfs(newRow, newCol, visited)
    }
}

fun copyGraph(prevGraph: Array<IntArray>, currentGraph: Array<IntArray>) {
    for (i in prevGraph.indices) {
        for (j in prevGraph[0].indices){
            prevGraph[i][j] = currentGraph[i][j]
        }
    }
}