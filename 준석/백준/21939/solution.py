import sys, heapq, collections
input = sys.stdin.readline

N = int(input())
minHeap, maxHeap = [], []
check = collections.defaultdict(bool)

for _ in range(N):
    problemNum, dif = map(int, input().split())
    heapq.heappush(minHeap, [dif, problemNum])
    heapq.heappush(maxHeap, [-dif, -problemNum])
    check[problemNum] = True

M = int(input())
for _ in range(M):
    command = input().split()
    if command[0] == 'add':
        problem = int(command[1])
        dif = int(command[2])

        while not check[-maxHeap[0][1]]:
            heapq.heappop(maxHeap)

        while not check[minHeap[0][1]]:
            heapq.heappop(minHeap)

        check[problem] = True
        heapq.heappush(minHeap, [dif, problem])
        heapq.heappush(maxHeap, [-dif, -problem])
    elif command[0] == 'recommend':
        if command[1] == '1':
            while not check[-maxHeap[0][1]]:
                heapq.heappop(maxHeap)
            print(-maxHeap[0][1])
        else:
            while not check[minHeap[0][1]]:
                heapq.heappop(minHeap)
            print(minHeap[0][1])
    else: # solved
        check[int(command[1])] = False
