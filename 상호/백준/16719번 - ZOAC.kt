/* https://www.acmicpc.net/problem/16719 */
/* 퀵 정렬과 비슷한 분할 정복법 사용*/

import java.io.BufferedReader
import java.io.InputStreamReader

private var string = ""
private lateinit var addArray: CharArray
private val answer = StringBuilder()

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    string = readLine()
    addArray = CharArray(string.length){' '}

    addSmallestChar(0, string.lastIndex)
    print(answer)
}

/**
* 퀵정렬과 비슷한 로직
* 가장 작은 문자를 찾아서 새배열에 기존의 위치와 동일하게 추가한다
* 그 문자열을 StringBuilder에 추가한다
* 찾은 문자의 오른쪽 문자열을 대상으로 재귀 함수를 호출한다
* 오른쪽이 다 끝나면 왼쪽 문자열에 재귀 함수를 호출한다
*/
private fun addSmallestChar(start: Int, end: Int) {
    if (start > end) return

    var minChar = string[start]
    var index = start
   
    for (i in start+1..end) {
        if (minChar > string[i]) {
            minChar = string[i]
            index = i
        }
    }

    addArray[index] = minChar
    addLine()
    addSmallestChar(index + 1, end)
    addSmallestChar(start, index - 1)
}

private fun addLine() {
    for (ch in addArray) {
        if (ch == ' ') continue
        answer.append(ch)
    }
    answer.appendLine()
}