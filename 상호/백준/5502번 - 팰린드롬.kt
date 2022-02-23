import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val string = readLine()
    val dp = Array<IntArray>(n) {
        IntArray(n)
    }

    if (string[0] == string.last()) dp[0][0] = 1

    for (i in 1 until n) {
        dp[0][i] = if (string[string.lastIndex] == string[i]) {
            1
        } else{
            dp[0][i-1]
        }

        dp[i][0] = if (string[0] == string[string.lastIndex - i]) {
            1
        } else {
            dp[i-1][0]
        }
    }

    // 가장 긴 공통 부분 수열의 길이를 구한다
    for (i in 1 until n) {
        for (j in 1 until n) {
            dp[i][j] =
                if (string[string.lastIndex - i] == string[j]) dp[i-1][j-1] + 1
                else max(dp[i-1][j], dp[i][j-1])
        }
    }

    print(n - dp[n-1][n-1])
}