/* https://leetcode.com/problems/largest-rectangle-in-histogram */
/* 스택 응용 문제, 어렵다!! */

import java.util.*

class Solution {
    data class Bar(var position: Int, val height: Int)
    
    fun largestRectangleArea(heights: IntArray): Int {
        var maxArea = 0
        val stack = Stack<Bar>()
        
        for (i in heights.indices) {
            val h = heights[i]
            
            // 스택과 h가 오름차순 관계인 경우
            if (stack.isEmpty() || (stack.isNotEmpty() && stack.peek().height <= h)) {
                stack.push(Bar(i, h))
                continue
            }
            
            // 새 히스토그램이 더 작은 경우
            var removedPosition = 0
            
            while (stack.isNotEmpty() && stack.peek().height > h) {
                val prevBar = stack.pop()
                val area = prevBar.height * (i - prevBar.position)
                maxArea = area.coerceAtLeast(maxArea)
                removedPosition = prevBar.position
            }
            
            // h로 제거한 막대기의 마지막 위치로 position을 설정한다.
            // -> 제거된 막대기들도 너비에 포함시킬 수 있다.
            stack.push(Bar(removedPosition, h))
        }
        
        // 남아있는 히스토그램
        while(stack.isNotEmpty()) {
            val bar = stack.pop()
            val area = bar.height * (heights.size - bar.position)
            maxArea = area.coerceAtLeast(maxArea)
        }
        
        return maxArea
    }
}