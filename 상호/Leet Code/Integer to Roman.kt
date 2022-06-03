/* https://leetcode.com/problems/integer-to-roman/ */
/* 구현, 문자열 */

import java.util.*

class Solution {
    
    private val nums = mutableListOf<Int>(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    
    fun intToRoman(num: Int): String {
        val numToRoman = hashMapOf<Int, String>(1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C", 500 to "D", 1000 to "M", 4 to "IV", 9 to "IX", 40 to "XL", 90 to "XC", 400 to "CD", 900 to "CM")
        var targetNum = num
        var answer = StringBuilder()
        
        while (targetNum > 0) {
            val smallerEqualNum = getSmallerEqualNum(targetNum)
            answer.append(numToRoman[smallerEqualNum]!!)
            targetNum -= smallerEqualNum
        }
        
        return answer.toString()
    }
    
    // 로마표에 있는 숫자 중 targetNum보다 작은 숫자중 가장 큰 것 or 같은 수 반환
    private fun getSmallerEqualNum(targetNum: Int): Int {
        for (i in nums.indices) {
            val num = nums[i]
            
            if (num == targetNum) {
                return num
            } else if (num > targetNum) {
                return nums[i-1]
            }
        }
        
        return nums.last()
    }
}