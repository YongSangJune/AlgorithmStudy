/* https://www.acmicpc.net/problem/22942 */
/** 스택 사용
 *  원과 x축과의 각 접점을 괄호로 생각해서 풀 수 있다.
 *  1. 원을 두 접점(Circle)으로 변환한다
 *  2. x좌표의 오름차순으로 정렬한다
 *  3. Circle들 중 x좌표가 같은 것들이 있는지 확인한다.
 *  4. 없다면 괄호를 순서대로 확인한다.
 *   4-1) 여는 괄호 -> stack.push()
 *   4-2) 닫는 괄호 -> stack.peek()와 비교한다
 *    4-2-1) type 일치 -> stack.pop()
 *    4-2-2) type 일치X -> 한 원의 영역에 다른 원이 침범했다는 뜻,
 *    "NO" 반환
 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// id: 원 식별자, type('L': 왼쪽 접(여는 괄호), 'R': 오른쪽 접접), x: x축과의 접점
data class Circle(val id: Int, val type: Char, val x: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val circleList = mutableListOf<Circle>()
    val stack = Stack<Circle>()

    repeat(n) { i ->
        with(StringTokenizer(readLine())) {
            val cx = nextToken().toInt()
            val r = nextToken().toInt()
            circleList.add(Circle(i, 'L', cx - r)) // x축과 원의 왼쪽 접점
            circleList.add(Circle(i, 'R', cx + r)) // 오른쪽 접점
        }
    }

    // x좌표 오름차순 정렬
    circleList.sortWith(compareBy{ it.x })

    // x축 접점이 일치하는 원(접하는 원)이 있는 경우
    for (i in 0 until circleList.lastIndex) {
        if (circleList[i].x == circleList[i+1].x) {
            print("NO")
            return
        }
    }

    for (circle in circleList) {
        if (stack.isEmpty()) {
            stack.push(circle)
            continue
        }

        // 여는 괄호는 모두 스택에 넣는다
        if (circle.type == 'L') {
            stack.push(circle)
        }
        // 닫는 괄호
        else {
            // stack에 있는 여는 괄호와 새로 들어오는 닫는 괄호의 id(원의 종류)가 다르다 -> 접점이 생긴다
            if (stack.peek().id != circle.id){
                print("NO")
                return
            }
            stack.pop() // 종류가 같다 -> 짝 맞춰서 삭제
        }
    }

    print("YES")
}