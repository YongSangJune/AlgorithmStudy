/* https://app.codility.com/programmers/trainings/2/rectangle_builder_greater_area/ */
/* 이분 탐색: Lower Bound */

import kotlin.math.ceil
import kotlin.math.sqrt
import java.util.*

fun solution(A: IntArray, X: Int): Int {
    var answer = 0
    val penCountMap: Map<Int, Int> = getPenCount(A) // 길이 to 개수
    val pens = LinkedList<Int>().apply{ addAll(getPensMoreThanOne(penCountMap)) }
    val MAX_COUNT = 1_000_000_000
    val sqrtOfX = sqrt(X.toDouble())

    while (pens.isNotEmpty()) {
        val length = pens.poll()

        if (answer > MAX_COUNT) break
        // 4개 이상 있으면서 제곱값 >= X인 경우 
        if (length >= sqrtOfX && penCountMap[length]!! >= 4) answer++

        // target * length >= X
        // target: length와 곱했을 때 X보다 크거나 같은 lower bound
        val target = ceil(X / length.toFloat()).toInt()
        val lowerBoundIdx = lowerBound(pens, target)
        answer += pens.size - lowerBoundIdx
    }

    return if (answer >= MAX_COUNT) -1 else answer
}

fun getPenCount(pens: IntArray): Map<Int, Int> {
    val penCountMap = HashMap<Int, Int>()

    pens.forEach { length ->
        penCountMap[length] = penCountMap.getOrDefault(length, 0) + 1
    }

    return penCountMap
}

fun getPensMoreThanOne(penCountMap: Map<Int, Int>): List<Int> {
    return penCountMap.toList()
        .filter{ (_, count) -> count >= 2}
        .map{ (length, _) -> length }
        .sorted()
}

fun lowerBound(pens: List<Int>, target: Int): Int{
    var start = 0
    var end = pens.lastIndex

    while (start <= end) {
        val mid = (start + end) / 2
        if (pens[mid] >= target) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    return end + 1
}