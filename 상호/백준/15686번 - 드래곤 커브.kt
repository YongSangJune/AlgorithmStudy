import java.io.BufferedReader
import java.io.InputStreamReader

data class Coord(val x: Int, val y: Int) // 좌표

const val MAX_SIZE = 100

lateinit var graph: Array<CharArray>
val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, -1, 0, 1)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var n = readLine().toInt()
    graph = Array(MAX_SIZE + 1) {
        CharArray(MAX_SIZE + 1)
    }

    while(n --> 0) {
        val (x, y, d, g) = readLine().split(" ").map(String::toInt)
        val lastX = x + dx[d] // 0세대 끝 점
        val lastY = y + dy[d]
        graph[y][x] = '.'
        graph[lastY][lastX] = '.'

        if (g == 0) continue

        // 좌표를 담는 리스트
        val dragonList = mutableListOf<Coord>()
        dragonList.add(Coord(x, y))
        dragonList.add(Coord(lastX, lastY))

        // 다음 세대 드래곤 커브 좌표들을 추가한다
        repeat(g) {
            addNextGeneration(dragonList)
        }
    }

    print(getDragonSquare())
}

// 좌표 회전
fun getrotatedCoord(coord: Coord, standardCoord: Coord): Coord {
    val x = coord.x; val y = coord.y
    val sx = standardCoord.x; val sy = standardCoord.y

    return when{
        x < sx && y == sy -> Coord(sx, sy - (sx - x)) // -> 끝점
        x > sx && y == sy -> Coord(sx, sy + (x - sx)) // 끝점 <-
        x == sx && y < sy -> Coord(sx + (sy - y), sy) // 아래 방향
        x == sx && y > sy -> Coord(sx - (y - sy), sy) // 윗 방향
        x < sx && y > sy -> Coord(sx - (y - sy), sy - (sx - x)) // 왼 아래-> 오 위 대각선
        x < sx && y < sy -> Coord(sx + (sy - y), sy - (sx - x)) // 왼 위 -> 오 아래 대각선
        x > sx && y < sy -> Coord(sx + (sy - y), sy + (x - sx)) // 오 위 -> 왼 아래 대각선
        else -> Coord(sx - (y - sy), sy + (x - sx)) // 오 아래 -> 왼 위 대각선
    }
}

fun getDragonSquare(): Int{
    var count = 0

    for (i in 0 until MAX_SIZE) {
        for (j in 0 until MAX_SIZE){
            // 꼭지점이 모두 드래곤 커브
            if (graph[i][j] == '.' && graph[i+1][j] == '.' && graph[i][j+1] == '.' && graph[i+1][j+1] == '.') {
                count++
            }
        }
    }

    return count
}

fun addNextGeneration(dragonList: MutableList<Coord>) {
    val size = dragonList.size
    val standardCoord = dragonList.last() // 끝점

    // 이전 세대에서 늦게 추가된 순서대로 진행한다
    for (i in size-2 downTo 0) {
        val coord = dragonList[i]
        val rotatedCoord = getrotatedCoord(coord, standardCoord) // 좌표를 회전시킨다
        if (rotatedCoord.x in 0..MAX_SIZE && rotatedCoord.y in 0..MAX_SIZE) {
            graph[rotatedCoord.y][rotatedCoord.x] = '.'
            dragonList.add(rotatedCoord)
        }
    }
}