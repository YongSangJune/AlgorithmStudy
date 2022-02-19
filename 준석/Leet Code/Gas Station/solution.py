class Solution:
    def canCompleteCircuit(self, gas: List[int], cost: List[int]) -> int:
        if sum(gas) < sum(cost):
            return -1
        answer = fuel = 0
        for i in range(len(gas)):
            if gas[i] + fuel < cost[i]:
                answer = i + 1
                fuel = 0
            else:
                fuel += gas[i] - cost[i]
        return answer 
