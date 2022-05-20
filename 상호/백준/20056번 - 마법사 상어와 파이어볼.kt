/* https://www.acmicpc.net/problem/20056 */
/* 삼성형 구현 문제 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class FireBall(var row: Int, var col: Int, val mass: Int, val speed: Int, val dir: Int)

val dRow = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
val dCol = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)
val evenDirs = intArrayOf(0, 2, 4, 6) // 파이어볼 나눌 때 사용될 방향 배열
val oddDirs = intArrayOf(1, 3, 5, 7)

lateinit var graph: Array<Array<Queue<FireBall>>> // 이동시킨 파이어볼 저장
val fireBalls: Queue<FireBall> = LinkedList() // 나뉘어진 파이어볼 저장

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, fbSize, moveCnt) = readLine().split(" ").map(String::toInt)
    graph = Array(n) {
        Array(n) { LinkedList() }
    }

    repeat(fbSize) {
        with(StringTokenizer(readLine())) {
            fireBalls.offer(
                FireBall(nextToken().toInt() - 1, nextToken().toInt() - 1,
                    nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
            )
        }
    }

    repeat(moveCnt) {
        simulateOnce() // 움직이고 합치고 나눈다
    }

    println(fireBalls.sumOf{ it.mass })
}

fun simulateOnce() {
    moveFireBalls()

    // 합치고 나누기
    for (row in graph.indices) {
        for (col in graph.indices) {
            // 비어있거나, 파이어볼이 하나인 경우
            if (graph[row][col].isEmpty()) continue
            if (graph[row][col].size == 1) {
                val fb = graph[row][col].poll()
                fireBalls.offer(fb)
                continue
            }

            val size = graph[row][col].size
            // 합치기
            // 리턴 값: (질량 합, 스피드 합, 방향의 홀짝이 같은지)
            var (newMass, newSpeed, dirSame) = joinFbs(row, col)

            // 나누기
            newMass /=  5;
            if (newMass == 0) continue
            newSpeed /= size
            val dirs = if (dirSame) evenDirs else oddDirs

            for (newDir in dirs) {
                fireBalls.offer(FireBall(row, col, newMass, newSpeed, newDir))
            }
        }
    }
}

// 움직이기
fun moveFireBalls() {
    while(fireBalls.isNotEmpty()) {
        val fb = fireBalls.poll()

        fb.row = (fb.row + dRow[fb.dir] * fb.speed) % graph.size
        if (fb.row < 0) {
            fb.row = graph.size + fb.row
        }
        fb.col = (fb.col + dCol[fb.dir] * fb.speed) % graph.size
        if (fb.col < 0) {
            fb.col = graph.size + fb.col
        }

        graph[fb.row][fb.col].add(fb)
    }
}

fun joinFbs(row: Int, col: Int): Triple<Int, Int, Boolean> {
    val size = graph[row][col].size // 파이어볼 개수
    var massSum = 0  // 질량 합 계산 후 나누기까지
    var speedSum = 0
    val oddEven = graph[row][col].peek().dir % 2 // 첫 파이어볼의 dir 홀짝
    var dirSame = true // dir 홀짝이 같은지

    repeat(size) {
        val fb = graph[row][col].poll()
        massSum += fb.mass
        speedSum += fb.speed

        // 하나라도 dir 홀짝이 다른 경우
        if (fb.dir % 2 != oddEven) {
            dirSame = false
        }
    }

    return Triple(massSum, speedSum, dirSame)
}