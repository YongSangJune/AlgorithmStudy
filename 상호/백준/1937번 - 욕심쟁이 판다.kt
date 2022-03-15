/* https://www.acmicpc.net/problem/1937 */

/* 재귀 + 메모이제이션
   구조는 금방 떠올렸는데, 재귀함수를 짤 때 애를 먹었다.
   메모장에 쓰면서 하니까 금방 풀렸다. 
   머릿속이 복잡할 땐 적으면서 풀어야겠다.
   풀 수 있을 것 같아서 고집 부리다간 멘탈 바사삭 + 시간 낭비
 */

import java.io.*
import java.util.*
import kotlin.math.max

// 해당 칸을 포함해서 최대 몇 번을 움직일 수 있는지 저장
// -->방문 한적 없으면 0, 있으면 최소 1
lateinit var memo: Array<IntArray> 
val dR = intArrayOf(-1, 1, 0, 0)
val dC = intArrayOf(0, 0, -1, 1)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var answer = 1
    val n = readLine()!!.toInt()
    val forest = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }
    memo = Array(n){ IntArray(n) }

    for (row in forest.indices) {
        for (col in forest.indices) {
            if (memo[row][col] == 0) {
                answer = max(answer, findMaxMove(forest, row, col))
            }
        }
    }

    print(answer)
}

fun findMaxMove(forest: Array<IntArray>, row: Int, col: Int): Int {
    if (memo[row][col] > 0) {
        return memo[row][col]
    }

    memo[row][col] = 1

    for (i in 0 until 4) {
        val newRow = row + dR[i]
        val newCol = col + dC[i]

        if (newRow !in forest.indices || newCol !in forest.indices || forest[row][col] >= forest[newRow][newCol]) continue
        memo[row][col] = max(memo[row][col], findMaxMove(forest, newRow, newCol) + 1) // +1은 지금 위치를 방문한 걸 반영한 것이다
    }

    return memo[row][col]
}