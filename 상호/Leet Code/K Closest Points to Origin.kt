/* 코드1: 단순 정렬 */
class Solution {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        return points.sortedWith(compareBy{
            it[0]*it[0] + it[1]*it[1]
        }).subList(0, k).toTypedArray()
    }
}

/* 코드2: maxHeap 사용 */
class Solution {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val maxPQ = PriorityQueue<IntArray>(k, compareByDescending{ it: IntArray ->
            it[0]*it[0] + it[1]*it[1]
        })

        for (i in points.indices) {
            maxPQ.add(points[i])
        
            if (maxPQ.size > k) {
                maxPQ.poll()
            }
        }

        return maxPQ.toTypedArray()
    }
}

/* 코드3: 퀵소트 + 이진 탐색 */
class Solution {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        var start = 0
        var end = points.lastIndex

        while(start < end) {
            val mid = quickSortAndGetPivot(points, start, end)

            if (mid == k) break
            else if (mid < k) start = mid + 1
            else end = mid - 1
        }

        return Arrays.copyOf(points, k)
    }

    private fun quickSortAndGetPivot(points: Array<IntArray>, s: Int, e: Int): Int {
        var start = s
        var end = e
        val pivot = points[start]

        while(start < end) {
            while (start < end && compare(pivot, points[end]) <= 0) end--
            points[start] = points[end]

            while (start < end && compare(pivot, points[start]) >= 0) start++
            points[end] = points[start]
        }

        points[start] = pivot
        return start
    }

    private fun compare(p1: IntArray, p2: IntArray): Int {
        return p1[0]*p1[0] + p1[1]*p1[1] - p2[0]*p2[0] - p2[1]*p2[1]
    }
}