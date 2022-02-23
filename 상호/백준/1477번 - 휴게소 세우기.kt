import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m, l) = readLine().split(" ").map(String::toInt)
    val stops = IntArray(n + 2)
    stops[0] = 0; stops[n + 1] = l // 고속도로 양 끝점 추가

    if (n > 0) {
        val st = StringTokenizer(readLine())
        for (i in 1..n) {
            stops[i] = st.nextToken().toInt()
        }

        stops.sort()
    }

    var start = 1
    var end = l

    // 구간의 길이를 기준으로 이분 탐색
    while (start <= end) {
        val mid = (start + end) / 2

        // 적게 지었으면 휴게소간 간격을 더 줄일 수 있다
        if (getHowManyBuilt(stops, mid) <= m) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    print(start)
}

fun getHowManyBuilt(stops: IntArray, limit: Int): Int {
    var prevStop = 0
    var count = 0

    for (i in 1 until stops.size) {
        // 휴게소 세우는 부분
        while (stops[i] - prevStop > limit) {
            prevStop += limit
            count++
        }

        prevStop = stops[i]
    }

    return count
}