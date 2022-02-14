/* 개선한 코드 */
class Solution {
    private lateinit var count: IntArray // 들어오거나 나가는 edge 개수
    private lateinit var visited: Array<BooleanArray> // visited[src][dst]
    private lateinit var winList: Array<MutableList<Int>> // [node]가 이긴 선수들
    
    fun solution(n: Int, results: Array<IntArray>): Int {
        var answer = 0
        count = IntArray(n+1)
        visited = Array(n+1){
            BooleanArray(n+1)
        }
        winList = Array(n+1) {
            mutableListOf<Int>()
        }
        
        results.forEach {
            val winner = it[0]
            val loser = it[1]
            
            winList[winner].add(loser)
        }
        
        // 모든 관계를 확인한다
        for (i in 1..n) {
            countEdgesOf(i, i)
        }
        
        return count.filter{it == n-1}.size
    }
    
    /*
    * src: 이기는 선수 current: 지는 선수, loser: current에게 지는 선수
    * src가 이길 수 있는 모든 선수를 DFS로 확인한다.
    * 이 과정에서 반대로 current들은 src에게 진다는 정보도 알 수 있다.
    */
    private fun countEdgesOf(src: Int, current: Int) {
        for (loser in winList[current]) {
            if (visited[src][loser]) continue
            
            visited[src][loser] = true
            // 관계가 n-1개인지만 확인하면 되기 때문에 이기고 지는 경우 상관없이 관계 개수를 카운팅한다.
            count[src]++   // src가 loser를 이긴다
            count[loser]++ // loser는 src에게 진다
            countEdgesOf(src, loser)
        }
    }
}


/* 처음 코드 */
class Solution {
    val leftMap = HashMap<Int, MutableList<Int>>()
    val rightMap = HashMap<Int, MutableList<Int>>()
    val related = Array<BooleanArray>(101) {
        BooleanArray(101)
    }

    fun solution(n: Int, results: Array<IntArray>): Int {
        var answer = 0

        results.forEach {
            val winner = it[0]
            val loser = it[1]

            val leftList = leftMap.getOrDefault(loser, mutableListOf())
            leftList.add(winner)
            leftMap[loser] = leftList

            val rightList = rightMap.getOrDefault(winner, mutableListOf())
            rightList.add(loser)
            rightMap[winner] = rightList
        }

        for (i in 1..n) {
            if (leftMap.containsKey(i)){
                searchLeft(i, i)
            }

            if (rightMap.containsKey(i)){
                searchRight(i, i)
            }
        }

        related.forEach { relation ->
            val count = relation.filter{ it == true}.size

            if (count == n-1) answer++
        }

        return answer
    }

    private fun searchLeft(src: Int, current: Int) {
        if (leftMap.containsKey(current)) {
            val leftList = leftMap[current]!!

            for(winner in leftList) {
                if(related[src][winner]) continue

                related[src][winner] = true
                searchLeft(src, winner)
            }
        }
    }

    private fun searchRight(src: Int, current: Int) {
        if (rightMap.containsKey(current)) {
            val rightList = rightMap[current]!!

            for(loser in rightList) {
                if(related[src][loser]) continue

                related[src][loser] = true
                searchRight(src, loser)
            }
        }
    }
}