import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, k) = readLine().split(" ").map(String::toInt)
    val coin = IntArray(n){ readLine().toInt() }
    val dp = IntArray(k+1).apply { this[0] = 1 } // coin[i]원을 사용해서 coin[i]원을 만드는 경우의 수: 1가지

    for (i in coin.indices) {
        for (j in 1..k) {
            if (j >= coin[i]) {
                // dp[j] = coin[i]를 쓰지 않고 j원을 만드는 경우의 수 + coin[i]를 하나 이상 사용해서 만드는 경우의 수
                dp[j] += dp[j - coin[i]]
            }
        }
    }

    print(dp[k])
}