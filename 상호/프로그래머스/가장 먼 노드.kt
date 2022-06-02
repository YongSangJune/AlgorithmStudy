/* https://programmers.co.kr/learn/courses/30/lessons/49189 */
/* 최단 거리 */

import java.util.*

class Solution {
    fun solution(n: Int, edges: Array<IntArray>): Int {
        val graph = Array(n + 1) { mutableListOf<Int>() }
        
        edges.forEach { (node1 ,node2) -> 
            graph[node1].add(node2)
            graph[node2].add(node1)
        }
        
        val minDist: IntArray = getMinDistArray(graph, 1)
        return getLongestNodeCount(minDist)
    }
    
    // 다익스트라
    private fun getMinDistArray(graph: Array<MutableList<Int>>, srcNode: Int): IntArray {
        val minDist = IntArray(graph.size) { -1 }
        val q: Queue<Int> = LinkedList()
        minDist[1] = 0
        q.offer(1)
        
        while (q.isNotEmpty()) {
            val midNode = q.poll()
            
            for (toNode in graph[midNode]) {
                val nextDist = minDist[midNode] + 1
                
                if (minDist[toNode] == -1 || minDist[toNode] > nextDist) {
                    minDist[toNode] = nextDist
                    q.offer(toNode)
                }
            }
        }
        
        return minDist
    }
    
    // 가장 먼 노드 개수 계산
    private fun getLongestNodeCount(minDist: IntArray): Int {
        var longestDist = 0
        var count = 0
        
        minDist.forEach { dist ->
            if (longestDist < dist) {
                longestDist = dist
                count = 1
            } else if (longestDist == dist) {
                count++
            }
        }
        
        return count
    }
}