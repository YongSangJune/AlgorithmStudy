import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val t = readLine().toInt()
    val answer = StringBuilder()

    repeat(t) {
        val size = readLine().toInt()
        val st = StringTokenizer(readLine())
        val files = LongArray(size){ st.nextToken().toLong() }
        val pq = PriorityQueue<Long>(size)
        var price = 0L

        files.forEach{
            pq.add(it)
        }

        // 매번 최소값 2개를 더한다
        while(pq.size > 1) {
            val minFile1 = pq.poll()
            val minFile2 = pq.poll()
            val tempFile = minFile1 + minFile2
            price += tempFile
            pq.add(tempFile)
        }

        answer.append(price).appendLine()
    }

    print(answer)
}