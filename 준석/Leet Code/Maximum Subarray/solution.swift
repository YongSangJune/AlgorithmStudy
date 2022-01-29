// 카데인 알고리즘

class Solution {
    func maxSubArray(_ nums: [Int]) -> Int {
        var nums = nums
        for i in 1..<nums.count {
            if nums[i-1] > 0 {
                nums[i] += nums[i-1]
            }
        }
        return nums.max()!
    }
}
