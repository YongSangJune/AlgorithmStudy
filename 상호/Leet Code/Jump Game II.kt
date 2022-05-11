/* https://leetcode.com/problems/jump-game-ii/ */
/* 현재 위치(i)에서 점프 (1 ~ num[i])를 모두 시도해서 최소 횟수로 도착한 경우를 리턴한다.
   재귀 함수, 메모이제이션 활용
*/  

class Solution {
    // 해당 위치에서 도착점에 도달하는 최소 점프 횟수
    lateinit var minJumpCnt: IntArray
    
    fun jump(nums: IntArray): Int {
        minJumpCnt = IntArray(nums.size)
        return makeJump(nums, 0)
    }
    
    // idx에서부터 도착점까지 가는데 필요한 최소 점프를 구한다
    fun makeJump(nums: IntArray, idx: Int): Int {
        // 도착점 or 너머로 도착 -> 더 이상 점프x
        if (idx >= nums.lastIndex) {
            return 0
        }
        // 이미 해당 위치에서 점프해봤다
        if (minJumpCnt[idx] > 0) {
            return minJumpCnt[idx]
        }
        
        // nums[idx] == 0이라면, 최대 가능한 점프 수를 초과해서 리턴하게 된다
        var minJump = nums.size
        for (i in 1..nums[idx]) {
            minJump = makeJump(nums, idx + i).coerceAtMost(minJump)
        }
        
        minJumpCnt[idx] = minJump + 1
        return minJumpCnt[idx]
    }
}