import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var (_, removeCount) = readLine().split(" ").map(String::toInt)
    val strNumber = readLine()
    val answer = StringBuilder() // 스택으로 활용

    for (num in strNumber) {
        if (removeCount == 0) {
            answer.append(num)
            continue
        }

        // 감히 나보다 작은 녀석이 앞에 있어!? -> 삭제로 응징한다
        // 나보다 큰 행님 or 같은 애가 앞에 있다 -> ㅇㅋ 인정
        while (answer.isNotEmpty() && answer.last() < num && removeCount > 0) {
            answer.deleteAt(answer.lastIndex)
            removeCount--
        }
        answer.append(num)
    }

    // 숫자들이 이미 내림차순으로 잘 정렬돼있으면,
    // 남아있는 removeCount만큼 뒤에서부터 삭제한다
    while (removeCount-- > 0) {
        answer.deleteAt(answer.lastIndex)
    }
    print(answer)
}