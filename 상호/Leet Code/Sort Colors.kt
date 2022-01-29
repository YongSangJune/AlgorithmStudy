class Solution {
    
    lateinit var tempArray: IntArray
    
    fun sortColors(nums: IntArray): Unit {
        tempArray = IntArray(nums.size)
        
        mergeSort(nums, 0, nums.lastIndex)
    }
    
    private fun mergeSort(nums: IntArray, start: Int, end: Int) {
        if (start == end) {
            return
        }
        
        val mid = (start + end) / 2
        
        mergeSort(nums, start, mid)
        mergeSort(nums, mid + 1, end)
        merge(nums, start, mid, end)
    }
    
    private fun merge(nums: IntArray, start: Int, mid: Int, end: Int) {
        var leftIdx = start
        var rightIdx = mid + 1
        var tempIdx = start
        
        while(leftIdx <= mid && rightIdx <= end) {
            if (nums[leftIdx] <= nums[rightIdx]) {
                tempArray[tempIdx++] = nums[leftIdx++]
            } else {
                tempArray[tempIdx++] = nums[rightIdx++]
            }
        }
        
        while (leftIdx <= mid) {
            tempArray[tempIdx++] = nums[leftIdx++]
        }
        
        while (rightIdx <= end) {
            tempArray[tempIdx++] = nums[rightIdx++]
        }
        
        for (i in start..end) {
            nums[i] = tempArray[i]
        }
    }
}