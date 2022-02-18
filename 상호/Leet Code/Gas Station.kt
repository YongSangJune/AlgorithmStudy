class Solution {
    fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
        if (gas.sum() - cost.sum() < 0) {
            return -1
        }
        
        var tank = 0 // 현재 가스량
        var startIndex = 0
        
        for (i in gas.indices) {
            tank += gas[i] - cost[i] // 다음 station으로 이동
            
            // 불가능하면 다음 station을 시작위치로 변경
            if (tank < 0) {
                tank = 0
                startIndex = i+1
            }
        }
        
        return startIndex
    }
}