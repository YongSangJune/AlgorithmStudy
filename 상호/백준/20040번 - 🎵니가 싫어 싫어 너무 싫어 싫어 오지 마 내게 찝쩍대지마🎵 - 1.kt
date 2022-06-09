/* https://www.acmicpc.net/problem/20440 */
/* 정렬 + 누적합 + 까다로웠던 구현 (나한텐..ㅎㅎ) */
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val timeToChangeMap = HashMap<Int, Int>() // <시간, 변화량>

    repeat(n) {
        val st = StringTokenizer(readLine())
        val enterTime = st.nextToken().toInt()
        val exitTime = st.nextToken().toInt()
        timeToChangeMap[enterTime] = timeToChangeMap.getOrDefault(enterTime, 0) + 1
        timeToChangeMap[exitTime] = timeToChangeMap.getOrDefault(exitTime, 0) - 1
    }

    val timeList = timeToChangeMap.keys.sorted()
    var maxCount = 0 // 최다 모기
    var currentCount = 0
    var enterTimeWhenMax = 0 // 최다 모기 구간 시작
    var exitTimeWhenMax = 0  // 최다 모기 구간 끝
    var isExitTimeUpdated = false // enterTimeWhenMax에 대해 exitTimeWhenMax가 정해졌는가? 
    // 한 번 업데이트 했으면 뒤에 같은 최대 구간이 또 나오더라도 exitTimeWhenMax를 업데이트하지 않는다 

    timeList.forEach { time ->
        val change = timeToChangeMap[time]!!
        currentCount += change
        
        // 모기 입장^^; && 그래서 모기가 역대 제일 많아짐
        if (change > 0 && currentCount > maxCount) {
            maxCount = currentCount
            enterTimeWhenMax = time
            isExitTimeUpdated = false // 구간 끝이 필요하다
        }
        // 모기 퇴장 && 퇴장 직전이 가장 많은 구간이었다 && 구간 끝을 업데이트 하지 않았었다
        else if (change < 0 && currentCount - change == maxCount && isExitTimeUpdated.not()) {
            exitTimeWhenMax = time
            isExitTimeUpdated = true
        }
    }

    println(maxCount)
    print("$enterTimeWhenMax $exitTimeWhenMax")
}