/* https://leetcode.com/problems/fruit-into-baskets */
/* 투 포인터 + Map 자료구조 */

class Solution {
    fun totalFruit(fruits: IntArray): Int {
        var start = 0
        var end = 0
        var maxCount = 0
        val baskets: MutableMap<Int, Int> = HashMap()
        
        while (end < fruits.size) {
            // Map에 해당 과일의 count를 +1 해서 일단 담고, 종류가 2가지 이하라면 다음 반복문으로 넘어간다
            val currentFruit = fruits[end++]
            baskets[currentFruit] = baskets.getOrDefault(currentFruit, 0) + 1
            if (baskets.size <= 2) continue
            
            // 3가지 이상이 되면, 직전 연속 길이를 최대 길이와 비교한다
            maxCount = (end - start - 1).coerceAtLeast(maxCount)
            
            // 종류가 2가지가 될 때까지 왼쪽 과일부터 하나씩 Map에서 뺀다
            while (baskets.size == 3) {
                val fruit = fruits[start++]
                baskets[fruit] = baskets[fruit]!! - 1
                
                if (baskets[fruit]!! == 0) {
                    baskets.remove(fruit)
                }
            }
        }
        
        // 마지막 연속 길이가 정답에 반영되지 않을 수 있기 때문에 비교해서 반환한다
        return (end - start).coerceAtLeast(maxCount)
    }
}