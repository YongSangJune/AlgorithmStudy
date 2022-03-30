/* https://www.acmicpc.net/problem/13397 */

/**
 * 첫 풀이
 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, divideLimit) = readLine().split(" ").map(String::toInt)
    val array = with(StringTokenizer(readLine())){
        IntArray(n){
            this.nextToken().toInt()
        }
    }
    var lower = 0
    var upper = array.maxOrNull()!! - array.minOrNull()!!

    if (n == 1) { // 배열 크기가 하나일 때
        print(0)
        return
    } else if (divideLimit == 1) { // 구간 1개 -> 배열을 나눌 수 없을 때
        print(upper - lower)
        return
    }

    // 점수의 최대값을 기준으로 이분 탐색
    // 점수의 최댓값이 mid가 되도록 구간을 가장 길게 나눠본다
    // 나눌 수 있으면 최소값을 찾기 위해 더 작은 최대값을 찾아본다
    // 나눌 수 없으면 더 큰 최대값을 찾는다
    // 구간의 점수가 커질수록 구간이 길어지고 구간 개수가 작아질 확률이 높아진다 
    while (lower <= upper) {
        val mid = (lower + upper) / 2

        if(isValid(array, mid, divideLimit)) {
            upper = mid - 1
        } else {
            lower = mid + 1
        }
    }

    print(lower)
}

fun isValid(array: IntArray, standard: Int, divideLimit: Int): Boolean {
    var count = 0
    var max = 0
    var min = 10_001

    for (i in array.indices) {
        max = max(max, array[i])
        min = min(min, array[i])

        // 최대값 or 최소값이 더 크/작아져서 더 길게 나눌 수 없을 때
        if (max - min > standard) {
            count++
            max = array[i]
            min = array[i]
        }
    }
    count++ // 마지막 구간

    return count <= divideLimit
}

/**
 * 2022-03-31: 복습
 */
 
 fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (size, limitOfSection) = readLine().split(" ").map(String::toInt)
    val numbers = with(StringTokenizer(readLine())){
        IntArray(size){ this.nextToken().toInt() }
    }

    var start = 0
    var end = 9999

    // 1. 구간 점수의 최대값을 가지고 이분 탐색을 한다
    // 2. 점수의 최대값을 기준으로 구간을 나눠본다
    // 3-1) if 구간 수 <= limitOfSection: 구간 점수 최대값의 최소값을 찾기 위해 구간 점수를 더 낮춰서 확인
    // 3-2) else: 지금 구간 점수의 최대값이 너무 작아서 구간 수가 너무 많다 -> 구간 점수를 올려서 확인 
    while(start <= end) {
        val maxSectionScore = (start + end) / 2
        val sectionCount = getNumOfSection(numbers, maxSectionScore)
        
        // limitOfSection보다
        if (sectionCount <= limitOfSection) {
            end = maxSectionScore - 1
        } else {
            start = maxSectionScore + 1
        }
    }

    println(start)
}

fun getNumOfSection(numbers: IntArray, maxScore: Int): Int {
    var count = 1 // 마지막에 구간이 바뀌지 않고 끝나는 것까지 포함시킬 수 있음
    var maxNum = Int.MIN_VALUE
    var minNum = Int.MAX_VALUE
    var currentScore = 0

    // 점수마다 현재 구간에 포함될 수 있는지 확인
    for (num in numbers) {
        maxNum = max(maxNum, num)
        minNum = min(minNum, num)
        currentScore = maxNum - minNum

        // 지금 점수가 현재 구간에 포함될 수 없을 때
        if (currentScore > maxScore) {
            count++
            maxNum = num
            minNum = num
        }
    }

    return count
}
