/* https://leetcode.com/problems/boats-to-save-people/ */

/* 1. people[i] == limit -> 그 사람밖에 못태움
   2-1. people[i] < limit이고, 몸무게가 적게 나가는 사람과
       묶어서 태울 수 있으면 함께 태우는 게 항상 이득임
   2-2. 최대한 많이 묶으려면 남은 사람 중 몸무게가 가장 많이
        나가는 사람과 적게 나가는 사람부터 확인해봐야 한다   
   2-3. 적게 나가는 사람들부터 묶으면 몸무게가 애매한 사람들도
        한 명씩 태워 보내는 상황이 생길 수 있음
 */
class Solution {
    fun numRescueBoats(people: IntArray, limit: Int): Int {
        var count = 0
        var start = 0
        var end = people.lastIndex
        
        people.sort()
        
        while (start <= end) {
            if (people[end] >= limit || people[start] + people[end] > limit) {
                end--
            } else { // people[start] + people[end] <= limit
                start++; end--;
            } 
            
            count++
        }
        
        return count
    }
}