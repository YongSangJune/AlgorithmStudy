import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val t = readLine().toInt()
    val answer = StringBuilder()

    repeat(t) {
        val (houseSize, robSize, limit) = readLine().split(" ").map(String::toInt)
        val st = StringTokenizer(readLine())
        val houses = IntArray(houseSize) { st.nextToken().toInt() }

        val count = getCaseCount(houses, robSize, limit)
        answer.append(count).appendLine()
    }

    print(answer)
}

fun getCaseCount(houses: IntArray, robSize: Int, limit: Int): Int {
    var start = 0
    var end = robSize
    var sum = (0 until robSize).sumOf{ i -> houses[i] }
    var count = 0

    if (robSize == houses.size) {
        return if (sum < limit) {
            1
        }else {
            0
        }
    }

    // sum = [start, end) 합
    while(start < houses.size) {
        if (sum < limit) {
            count++
        }

        sum = sum - houses[start++] + houses[end++]
        if (end == houses.size) end = 0
    }

    return count
}