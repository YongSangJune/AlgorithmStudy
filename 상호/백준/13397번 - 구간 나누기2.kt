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