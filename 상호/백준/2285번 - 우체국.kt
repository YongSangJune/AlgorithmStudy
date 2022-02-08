import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class Town(val location: Int, val population: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    var rightPopulations = 0L // 탐색하는 곳 기준 오른쪽 마을의 인구수 총합
    var leftPopulations = 0L  // 탐색하는 곳 포함 왼쪽 마을의 인구수 총합

    val towns = Array<Town>(n) {
        val st = StringTokenizer(readLine())
        val location = st.nextToken().toInt()
        val population = st.nextToken().toInt()

        Town(location, population).also { town ->
            rightPopulations += town.population
        }
    }

    towns.sortWith(compareBy{it.location}) // location 오름차순으로 정렬

    towns.forEach { town ->
        leftPopulations += town.population
        rightPopulations -= town.population

        // 인구수가 더 많은 쪽으로 가까이 갈수록 거리 합이 짧아진다
        if (leftPopulations >= rightPopulations) {
            print(town.location)
            return
        }
    }
}