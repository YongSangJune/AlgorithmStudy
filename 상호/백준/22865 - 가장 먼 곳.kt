/* https://www.acmicpc.net/problem/22865 */
/* 다익스트라, 약간의 역발상 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

data class Edge(val from: Int, val to: Int, val dist: Int)

lateinit var graph: Array<MutableList<Edge>> // 인접 리스트

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val (a,b,c) = readLine().split(" ").map(String::toInt)
    val m = readLine().toInt()
    graph = Array(n+1) { mutableListOf<Edge>() }
    val minDist = Array(3){
        IntArray(n+1) { -1 }
    } // a,b,c에서 모든 지점까지의 최단 거리

    repeat(m) {
        val st = StringTokenizer(readLine())
        val land1 = st.nextToken().toInt()
        val land2 = st.nextToken().toInt()
        val dist = st.nextToken().toInt()
        graph[land1].add(Edge(land1, land2, dist)); graph[land2].add(Edge(land2, land1, dist))
    }

    // a,b,c에서 모든 위치까지의 최단거리를 각각 구한다 (다익스트라)
    calculateMinDist(minDist[0], a)
    calculateMinDist(minDist[1], b)
    calculateMinDist(minDist[2], c)

    var maxOfMinDist = 0 // 최단 거리중 가장 먼 거리
    var answerLand = 0   // 위의 거리에 있는 땅 - 정답
    for (i in 1..n) {
        var tempMinDist = min(minDist[0][i], minDist[1][i])
        tempMinDist = min(tempMinDist, minDist[2][i])

        if (maxOfMinDist < tempMinDist) {
            maxOfMinDist = tempMinDist
            answerLand = i
        }
    }

    println(answerLand)
}

// 다익스트라
fun calculateMinDist(minDist: IntArray, src: Int) {
    val q: Queue<Int> = LinkedList()
    q.offer(src)
    minDist[src] = 0

    while (q.isNotEmpty()) {
        val currentLand = q.poll()

        for (nextEdge in graph[currentLand]) {
            val nextDist = minDist[currentLand] + nextEdge.dist
            // 첫 방문 or 기존 거리보다 짧으면 갱신
            if (minDist[nextEdge.to] == -1 || nextDist < minDist[nextEdge.to]) {
                minDist[nextEdge.to] = nextDist
                q.offer(nextEdge.to)
            }
        }
    }
}