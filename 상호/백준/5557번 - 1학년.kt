/* https://www.acmicpc.net/problem/5557 */
/* DP */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val nums = with(StringTokenizer(readLine())){
        IntArray(n) {
            this@with.nextToken().toInt()
        }
    }
    // dp[i][j]: i번째 숫자를 사용해서 j를 만드는 경우의 수 =
    // (i-1)번째 숫자를 사용해서 j-nums[i]를 만드는 경우의 수 +
    // (i-1)번째 숫자를 사용해서 j+nums[i]를 만드는 경우의 수
    val dp = Array<LongArray>(n) {
        LongArray(21)
    }
    dp[0][nums[0]] = 1

    for (i in 1 until n-1) {
        for (j in 0..20) {
            if (j - nums[i] >= 0) dp[i][j] = dp[i - 1][j - nums[i]]
            if (j + nums[i] <= 20) dp[i][j] += dp[i - 1][j + nums[i]]
        }
    }

    print(dp[n - 2][nums.last()])
}