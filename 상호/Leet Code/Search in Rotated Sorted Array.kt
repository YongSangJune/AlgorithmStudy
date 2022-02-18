/* 코드1: 최악의 경우 O(n)이며, O(logn) 풀이가 아닌데 통과됨*/
class Solution {
    fun search(nums: IntArray, target: Int): Int {
        if (nums[nums.lastIndex] < target && target < nums[0]) {
            return -1
        }
        
        var start = 0
        var end = nums.lastIndex
        var answer = -1
        
        // 0번째 값보다 크면 왼쪽에서 찾고
        // 작으면, 오른쪽에서 찾는다
        if (target <= nums.last()) {
            // 
            while (start < end && target < nums[end]) {
                end--
            }
            
            if (nums[end] == target) answer = end
            
        } else {
            while (start < end && target > nums[start]) {
                start++
            }
            
            if (nums[start] == target) answer = start
        }
        
        return answer
    }
}

/* 코드2: 이분 탐색*/
class Solution {
    fun search(nums: IntArray, target: Int): Int {
        if (nums.last() < target && target < nums[0]) {
            return -1
        }
        if (nums[0] == target) return 0
        
        val minValueIndex = findMinIndex(nums)
        var start = 0
        var end = nums.lastIndex
        
        if (minValueIndex == 0){} // 회전이 안됐을 수도 있다
        else if (target > nums[0]) {
            end = minValueIndex - 1
        } else {
            start = minValueIndex
        }
        
        return findAnswer(nums, start, end, target)
    }
    
    // 가장 작은 값의 인덱스를 찾는다
    private fun findMinIndex(nums: IntArray): Int {
        var start = 0
        var end = nums.lastIndex
        
        while(start <= end) {
            val mid = (start + end) / 2
            
            if (nums[mid] < nums.last()) {
                end = mid - 1
            } else if (nums[mid] > nums.last()) {
                start = mid + 1
            } else{
                break
            }
        }
        
        return start
    }
    
    // 이분 탐색: 분할된 배열 중 한 영역에서만 진행한다
    private fun findAnswer(nums: IntArray, _start: Int, _end: Int, target: Int): Int {
        var start = _start
        var end = _end
        
        while (start <= end) {
            val mid = (start + end) / 2
            
            if (target == nums[mid]) return mid
            else if (target < nums[mid]) end = mid - 1
            else start = mid + 1
        }
        
        return -1
    }
}