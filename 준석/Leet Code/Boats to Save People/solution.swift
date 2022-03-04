class Solution {
    func numRescueBoats(_ people: [Int], _ limit: Int) -> Int {
        let people = people.sorted(by: <)
        var answer = 0
        var left = 0, right = people.count - 1
        
        while left <= right {
            if people[left] + people[right] <= limit {
                left += 1
            }
            right -= 1
            answer += 1    
        }
        return answer
    }
}
