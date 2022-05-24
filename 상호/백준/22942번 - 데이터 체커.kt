/* https://www.acmicpc.net/problem/22942 */

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.abs
import java.util.*

data class Circle(val x: Int, val r: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var result = "YES"
    val n = readLine().toInt()
    val circleList = mutableListOf<Circle>()

    repeat(n) {
        with(StringTokenizer(readLine())) {
            circleList.add(Circle(nextToken().toInt(), nextToken().toInt()))
        }
    }

    circleList.sortWith(compareBy{ it.x - it.r })

    for (i in 0 until n-1) {
        val prevCircle = circleList[i]
        val newCircle = circleList[i+1]
        // 동심원 & 같은 반지름
        if (prevCircle.x == newCircle.x && prevCircle.r == newCircle.r) {
            result = "NO"
            break
        }
        // 원 내부
        else if (newCircle.x in (prevCircle.x - prevCircle.r)..(prevCircle.x + prevCircle.r)) {
            val diffC = abs(prevCircle.x - newCircle.x)
            val diffR = abs(prevCircle.r - newCircle.r)
            if (diffC >= diffR) {
                result = "NO"
                break
            }
        }
        // 원 외부
        else if (prevCircle.x + prevCircle.r < newCircle.x){
            val diffC = abs(prevCircle.x - newCircle.x)
            if (diffC <= prevCircle.r + newCircle.r) {
                result = "NO"
                break
            }
        }
    }

    print(result)
}