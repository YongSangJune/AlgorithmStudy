/* https://www.acmicpc.net/problem/1976 */
/* 여행 경로의 도시들이 모두 연결돼있는지 확인하는 문제 -> 유니온 파인드 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// 속한 그룹의 대표 도시를 저장하는 배열
lateinit var parent: IntArray

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val citySize = readLine().toInt()
    val routeSize = readLine().toInt()
    parent = IntArray(citySize) { i -> i } 

    // 연결된 도시끼리 모두 그룹짓는다
    for (city1 in 0 until citySize) {
        val st = StringTokenizer(readLine())

        for (city2 in 0 until citySize) {
            val isLinked = st.nextToken().toInt() == 1

            if (isLinked) {
                val parent1 = find(city1)
                val parent2 = find(city2)
                // 그룹이 다르면 한쪽 그룹을 다른쪽 그룹에 편입시킨다
                if (parent1 != parent2) {
                    parent[parent2] = parent1
                }
            }
        }
    }

    val route = StringTokenizer(readLine())
    val parentSet = HashSet<Int>()
    // 경로의 도시들이 속한 그룹의 대표 도시를 set에 추가한다
    repeat(routeSize) {
        val city = route.nextToken().toInt() - 1
        // 같은 그룹이더라도 대표 도시가 하나로 통일되어 있지 않을 수도 있기 때문에
        // find()로 대표 도시를 찾는다
        parentSet.add(find(city)) 
    }

    // 대표 도시가 하나이다 -> 같은 그룹이다 -> 연결돼있다
    if (parentSet.size == 1) {
        print("YES")
    } else {
        print("NO")
    }
}

// 유니온 '파인드', 도시가 속한 그룹의 대표 도시를 찾는다
fun find(city: Int): Int {
    val parentOfCity = parent[city]
    if (parentOfCity == city) return city
    parent[city] = find(parentOfCity)
    return parent[city]
}