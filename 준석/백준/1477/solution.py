import sys
input = sys.stdin.readline

def getCount(mid, restStops):
    current, count = 0, 0
    for position in restStops:
        diff = position - current
        current = position
        if diff > mid:
            count += (diff-1)//mid
    return count

n, m, distance = map(int, input().split())
restStops = list(map(int, input().split()))
restStops.append(distance)
restStops.sort()

left, right = 1, distance-1
answer = 0

while left<=right:
    mid = (left+right)//2
    count = getCount(mid, restStops)

    if count > m:
        left = mid+1
    else:
        right = mid-1
        answer = mid
print(answer)
