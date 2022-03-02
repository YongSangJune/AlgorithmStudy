/* https://programmers.co.kr/learn/courses/30/lessons/42895 */

/*  N을 n개 사용해서 만들 수 있는 숫자 조합 = 
    N..N(n개), (N i개로 만든 숫자 조합) * (n-i개로 만든 숫자 조합) 
    n = 1 ~ 8까지 순서대로 찾아나간다.
*/

class Solution {
    fun solution(N: Int, number: Int): Int {
        var answer = 0
        // dp[i]: N을 i번 사용해서 만들 수 있는 모든 수들의 집합
        val dp = Array<MutableSet<Int>>(9) {
            mutableSetOf()
        }
        var sequentN = 0
        
        // i: N 사용 횟수
        for (i in 1..8) {
            sequentN = sequentN * 10 + N // 연속 N
            if (sequentN == number) return i
            dp[i].add(sequentN)
            
            // dp[j]와 dp[i-j]의 수를 조합하는 것이므로
            // i/2까지만 확인한다
            for (j in 1..i/2) {
                for (num1 in dp[j]) {
                    for (num2 in dp[i - j]) {
                        val newNumbers = getNewNumbers(num1, num2)
                        
                        if (number in newNumbers) return i
                        
                        dp[i].addAll(newNumbers)
                    }
                }
            }
        }
        
        return -1
    }
    
    /**
     * 각 숫자의 사칙연산으로 만들 수 있는 수의 집합을 반환한다.
     * ※뺄셈과 나눗셈은 순서에 따라 값이 달라진다.
     */
    private fun getNewNumbers(num1: Int, num2: Int): Set<Int> {
        val result = mutableSetOf<Int>(num1+num2, num1*num2, num1-num2, num2-num1)
        if (num1 != 0) result.add(num2/num1)
        if (num2 != 0) result.add(num1/num2)
        return result
    }
}