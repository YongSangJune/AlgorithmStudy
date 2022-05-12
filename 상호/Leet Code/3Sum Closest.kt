/* https://leetcode.com/problems/3sum-closest/ */
/* 투 포인터
   기준: target과의 차이가 가장 작은 것
   답: 기준을 충족하는 세 수의 합
   --> 기준을 충족하는지 확인하기 위한 변수와
       정답을 위한 변수가 하나씩 필요하다.
 */
 
import kotlin.math.abs

class Solution {
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        var answerDiffer = Int.MAX_VALUE
        var answer = 0
        nums.sort()
        
        for (i in 0 until nums.size - 2) {
            var start = i + 1
            var end = nums.lastIndex
            
            while (start < end) {
                val tempSum = nums[i] + nums[start] + nums[end]
                val tempDiffer = abs(target - tempSum)
                
                if (tempDiffer < answerDiffer) {
                    answerDiffer = tempDiffer
                    answer = tempSum
                }
                
                if (tempSum > target) {
                    end--
                } else if (tempSum < target) {
                    start++
                } else {
                    return tempSum
                }
            }
        }
        
        return answer
    }
}