import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class Problem(val number: Int, val level: Int)

val levelOf = IntArray(100_001) // 갖고 있는 문제의 난이도 - 나중에 풀었던 번호가 들어왔을 때 난이도를 구별하기 위함
val maxPQ = PriorityQueue(compareByDescending<Problem>{it.level}.thenByDescending{it.number})
val minPQ = PriorityQueue(compareBy<Problem>{it.level}.thenBy{it.number})

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()

    repeat(n) {
        val st = StringTokenizer(readLine())
        val number = st.nextToken().toInt()
        val level = st.nextToken().toInt()
        val problem = Problem(number, level)

        maxPQ.add(problem)
        minPQ.add(problem)
        levelOf[number] = level
    }

    val cmdArray = Array<String>(readLine().toInt()) {
        readLine()
    }
    print(getAnswer(cmdArray))
}

fun getAnswer(cmdArray: Array<String>): StringBuilder {
    val answer = StringBuilder()

    cmdArray.forEach { cmd ->
        val st = StringTokenizer(cmd)

        when(st.nextToken()) {
            "recommend" -> {
                val pq =
                    if (st.nextToken().toInt() == 1) maxPQ
                    else minPQ

                var problem = pq.peek()

                // 이미 푼 문제면 큐에서 제거하고 다음 문제를 추천한다
                while (levelOf[problem.number] != problem.level) {
                    pq.poll()
                    problem = pq.peek()
                }

                answer.append(problem.number).appendLine()
            }

            "add" -> {
                val number = st.nextToken().toInt()
                val level = st.nextToken().toInt()
                val problem = Problem(number, level)

                maxPQ.add(problem)
                minPQ.add(problem)
                levelOf[number] = level
            }

            "solved" -> {
                val number = st.nextToken().toInt()
                levelOf[number] = 0 // 문제 해결
            }
        }
    }

    return answer
}