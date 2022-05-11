import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

val dRow = intArrayOf(0, 0, -1, -1, -1, 0, 1, 1, 1) // 1 ~ 8번 이동
val dCol = intArrayOf(0, -1, -1, 0, 1, 1, 1, 0, -1)
val diagnolDRow = intArrayOf(-1, -1, 1, 1) // 대각선 이동
val diagnolDCol = intArrayOf(-1, 1, 1, -1)

lateinit var graph: Array<IntArray>
lateinit var clouds: MutableList<Cloud>   // 현재 구름 리스트
lateinit var isCloud: Array<BooleanArray>

data class Cloud(var row: Int, var col: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m) = readLine().split(" ").map(String::toInt)
    graph = Array(n) { row ->
        val st = StringTokenizer(readLine())
        IntArray(n) { col ->
            st.nextToken().toInt()
        }
    }
    // 초기 구름 설정
    clouds = mutableListOf(Cloud(n-1, 0), Cloud(n-1, 1), Cloud(n-2, 0), Cloud(n-2, 1))
    // 구름이 이동하기 전에는 어디에 있는지 관심이 없다 
    isCloud = Array(n) { BooleanArray(n) }

    repeat(m) {
        val (dir, dist) = readLine().split(" ").map(String::toInt)
        moveCloudsAndAddWater(dir, dist) // 구름을 움직이고 해당 위치에 구름 표시, 바구니에 물 증가
        addDiagonalWater() // 대각선 방향 체크
        makeNewClouds() // 기존 구름을 제외한 새 구름 생성
    }

    println(graph.sumOf { it.sumOf{ water -> water } })
}

fun moveCloudsAndAddWater(dir: Int, dist: Int) {
    for (cloud in clouds) {
        cloud.row = (cloud.row + dRow[dir] * dist) % graph.size
        cloud.col = (cloud.col + dCol[dir] * dist) % graph.size
        if (cloud.row < 0) cloud.row = graph.size + cloud.row
        if (cloud.col < 0) cloud.col = graph.size + cloud.col
        graph[cloud.row][cloud.col]++
        isCloud[cloud.row][cloud.col] = true
    }
}

fun addDiagonalWater() {
    for (cloud in clouds) {
        var diagonalWaters = 0

        for (i in 0 until 4) {
            val diagonalRow = cloud.row + diagnolDRow[i]
            val diagonalCol = cloud.col + diagnolDCol[i]

            if (diagonalRow !in graph.indices || diagonalCol !in graph.indices) continue
            if (graph[diagonalRow][diagonalCol] > 0) {
                diagonalWaters++
            }
        }

        graph[cloud.row][cloud.col] += diagonalWaters
    }
}

fun makeNewClouds() {
    clouds = mutableListOf()

    for (row in graph.indices) {
        for (col in graph.indices) {
            if (graph[row][col] >= 2 && isCloud[row][col].not()) {
                graph[row][col] -= 2
                clouds.add(Cloud(row, col))
            }
            // 구름은 이동시키고 난 다음에 체크한다
            isCloud[row][col] = false
        }
    }
}
