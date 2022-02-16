import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.*
import kotlin.math.*

// attackTurn: 성에 도착하는 턴, isActive: 경기장에 남아있는지
data class Enemy(val idx: Int, val row: Int, val col: Int, val attackTurn: Int, var isActive: Boolean = true)

var rowSize = 0
var colSize = 0
var attackRange = 0 // 공격 범위 -> 적들의 거리를 움직이는 대신 공격 범위를 증가시킬 것이다

var enemyIdx = 0
val enemies = mutableListOf<Enemy>()
val rangersPosition = IntArray(3) // 궁수 위치(column)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    rowSize = st.nextToken().toInt()
    colSize = st.nextToken().toInt()
    attackRange = st.nextToken().toInt()

    for (row in 0 until rowSize) {
        val rowLine = StringTokenizer(readLine())
        for (col in 0 until colSize) {
            val status = rowLine.nextToken()
            if (status == "1") {
                enemies.add(Enemy(enemyIdx++, row, col, rowSize - row))
            }
        }
    }

    if (enemyIdx == 0) {
        print(0)
    } else{
        print(getMaxCount())
    }
}

fun getMaxCount(): Int {
    return simulate(0, 0)
}

fun simulate(rangerIdx: Int, startCol: Int): Int {
    if (rangerIdx == 3) {
        val count = simulateAGame()
        initActive()
        return count
    }

    var maxCount = 0

    for (i in startCol until colSize) {
        rangersPosition[rangerIdx] = i
        maxCount = max(maxCount, simulate(rangerIdx + 1, i + 1))
    }

    return maxCount
}

fun simulateAGame(): Int {
    var enemyCount = enemies.size  // 보드판에 남아있는 적의 수
    var removeCount = 0            // 제거한 적의 수
    var turn = 0
    val rangerRow = rowSize        // 궁수가 어느 행에 있는지
    var currentRange = attackRange // 공격 거리 -> 거리는 상대적임을 이용한다

    while (enemyCount > 0 && turn < rowSize) {
        val removeSet = mutableSetOf<Int>()

        for (rangerCol in rangersPosition) {
            var minDistance = Int.MAX_VALUE
            var removeIdx = -1

            for (enemy in enemies) {
                if (!enemy.isActive) continue
                if (turn >= enemy.attackTurn) {
                    enemy.isActive = false
                    enemyCount--
                    continue
                }

                val distance = rangerRow - enemy.row + abs(rangerCol - enemy.col)
                if (distance > currentRange || distance > minDistance) continue

                if (minDistance > distance) removeIdx = enemy.idx
                if (minDistance == distance && enemy.col < enemies[removeIdx].col) {
                    removeIdx = enemy.idx
                }

                minDistance = min(minDistance, distance)
            }

            if (removeIdx > -1) removeSet.add(removeIdx)
        }

        removeCount += removeSet.size
        enemyCount -= removeSet.size
        removeSet.forEach { idx ->
            enemies[idx].isActive = false
        }
        turn++
        currentRange++
    }

    return removeCount
}

fun initActive() {
    enemies.forEach {
        it.isActive = true
    }
}