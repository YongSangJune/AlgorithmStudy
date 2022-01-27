import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

const val MAX_HEIGHT = 1_000_000

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val balloons = IntArray(n) {
        st.nextToken().toInt()
    }
    var count = 0
    val arrows = IntArray(MAX_HEIGHT + 2) // 날아가고 있는 화살

    balloons.forEach { height ->
        if (arrows[height] == 0) {  // h 높이에 화살이 없으면
            count += 1              // 화살을 쏜다
        } else if (arrows[height] > 0) { // h 높이로 날아오는 화살이 있으면
            arrows[height] -= 1          // 날아오던 화살에 맞는다
        }

        arrows[height - 1] += 1 // 풍선에 맞고 화살 높이 감소
    }

    print(count)
}
