/* https://leetcode.com/problems/jump-game/ */

/**
 * 완전 탐색, 그리디, 재귀
 */
class Solution {
    // 탐색해봤다는 건 그 위치에서는 도달할 수 없다는 뜻
    lateinit var visited: BooleanArray

    fun canJump(nums: IntArray): Boolean {
        return when{
            nums.size > 1 && nums[0] == 0 -> false
            
            nums[0] == 0 -> true
            
            else  -> {
                visited = BooleanArray(nums.size)
                isReachable(nums, 0)
            }
        }
    }
    
    private fun isReachable(nums: IntArray, index: Int): Boolean {
        if (index >= nums.lastIndex) return true // 성공 조건 종료
        if (visited[index]) return false
        
        visited[index] = true
        
        // 최대 길이 ~ 1까지 전부 점프해본다
        for (distance in nums[index] downTo 1) {
            if(isReachable(nums, index + distance)) {
                return true
            }
        }
        
        // for문에서 전부 탐색했는데 true가 반환되지 않았으면
        // 지금 위치에서 끝에 도달할 수 없다
        return false
    }
}