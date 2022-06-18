/* https://app.codility.com/programmers/trainings/4/disappearing_pairs/ */
/* 스택 문제 */

import java.util.*

fun solution(S: String): String {
    val sb = StringBuilder() // 스택으로 사용

    S.forEach { ch -> 
        // 같은 문자가 2개 연달아 출현 -> 삭제
        if (sb.isNotEmpty() && sb[sb.lastIndex] == ch) {
            sb.deleteCharAt(sb.lastIndex)
        } else {
            sb.append(ch)
        }
    }

    return sb.toString()
}