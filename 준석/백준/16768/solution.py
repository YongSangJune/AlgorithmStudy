N, K = map(int, input().split())
arr = [list(input()) for _ in range(N)]
visited = [[False for i in range(10)] for _ in range(N)]
visited2 = [[False for i in range(10)] for _ in range(N)]
dx, dy = [-1, 0, 1, 0], [0, -1, 0, 1]

def countDFS(x, y):
    visited[x][y] = True
    count = 1
    for i in range(4):
        nx, ny = x + dx[i], y + dy[i]
        if nx < 0 or nx >= N or ny < 0 or ny >= 10:
            continue
        if visited[nx][ny] or arr[x][y] != arr[nx][ny]:
            continue
        count += countDFS(nx, ny)
    return count

def deleteDFS(x, y, val):
    visited2[x][y] = True
    arr[x][y] = '0'
    for i in range(4):
        nx, ny = x + dx[i], y + dy[i]
        if nx < 0 or nx >= N or ny < 0 or ny >= 10:
            continue
        if visited2[nx][ny] or arr[nx][ny] != val:
            continue
        deleteDFS(nx, ny, val)

def gravity():
    for i in range(10):
        temp = []
        for j in range(N):
            if arr[j][i] != '0':
                temp.append(arr[j][i])
                arr[j][i] = '0'
        for k in range(N-1, 0, -1):
            if temp:
                arr[k][i] = temp.pop()

while True:
    exist = False
    visited = [[False for i in range(10)] for _ in range(N)]
    visited2 = [[False for i in range(10)] for _ in range(N)]
    for i in range(N):
        for j in range(10):
            if arr[i][j] == '0' or visited[i][j]:
                continue
            count = countDFS(i, j)
            if count >= K:
                deleteDFS(i, j, arr[i][j])
                exist = True
    if not exist:
        break
    gravity()

for nums in arr:
    print(''.join(nums))
