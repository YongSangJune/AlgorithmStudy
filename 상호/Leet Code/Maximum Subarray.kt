/* https://leetcode.com/problems/maximum-subarray/ */

/** 첫번째 풀이: 분할 정복
 * Merge Sort랑 유사함
 * 정답이 (1)왼쪽 구간에만 있는 경우, (2)오른쪽 구간에만 있는 경우,
 * (3)왼쪽구간 + 오른쪽 구간에 걸쳐 있는 경우로 나눌 수 있다.
 * (1), (2)는 재귀 함수로 구하고,
 * (3)은 중간지점에서 오른쪽 범위 최대 값 + 중간지점에서 왼쪽 범위 최대 값으로 구한다
 */

import kotlin.math.max

class Solution {
    fun maxSubArray(nums: IntArray): Int {
        return divideConquer(nums, 0, nums.lastIndex)
    }
    
    private fun divideConquer(nums: IntArray, start: Int, end: Int): Int {
        if (start == end) {
            return nums[start]
        }
        
        val mid = (start + end) / 2
        val leftMaxSum = divideConquer(nums, start, mid)
        val rightMaxSum = divideConquer(nums, mid + 1, end)
        var maxSum = max(leftMaxSum, rightMaxSum)
        
        var leftSum = 0  // 중간 지점에서 왼쪽으로의 연속 합
        var rightSum = 0 
        var leftMaxSum2 = Int.MIN_VALUE // leftSum의 최대값
        var rightMaxSum2 = Int.MIN_VALUE
        
        // 중간에서 오른쪽으로 하나씩 더한다
        // 그 중 최대 값을 구한다
        for (i in mid+1..end) {
            leftSum += nums[i]
            leftMaxSum2 = max(leftMaxSum2, leftSum)
        }
        
        for (i in mid downTo start){
            rightSum += nums[i]
            rightMaxSum2 = max(rightMaxSum2, rightSum)
        }
        
        return max(maxSum, leftMaxSum2 + rightMaxSum2)
    }
}


/** 두번째 풀이: DP
 * dp[i]: i를 반드시 포함시킨 가장 큰 subarray
 * dp[i-1] < 0 ->  nums[i]가 가장 크다
 * dp[i-1] >= 0 -> nums[i]가 음수여도 dp[i-1] + nums[i]가 가장 크다
 * 만약 dp[i] > dp[i] + nums[i+1]이라면 즉, dp[i] > dp[i+1]이면 dp[i]가 정답에 더 가깝다.
 * 즉, dp[0 until n]의 최대값이 답이 된다.
 */
class Solution {
    fun maxSubArray(nums: IntArray): Int {
        for (i in 1 until nums.size) {
            if (nums[i-1] >= 0) {
                nums[i] += nums[i-1]
            }
        }
        
        return nums.max()!!
    }
}