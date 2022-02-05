from collections import deque
import sys

input = sys.stdin.readline

N, M = map(int, input().split())
graph = [list(map(int, input().strip())) for _ in range(N)]
dx, dy = [0, 0, 1, -1], [1, -1, 0, 0]

q = deque()
q.append([0, 0, 1])
visited = [[[0] * 2 for _ in range(M)] for _ in range(N)]
visited[0][0][1] = 1

def bfs():
    while q:
        x, y, w = q.popleft()
        if x == N-1 and y == M-1:
            return visited[x][y][w]
        for i in range(4):
            nx, ny = x + dx[i], y + dy[i]
            if 0 <= nx < N and 0 <= ny < M:
                if graph[nx][ny] == 1 and w == 1:
                    visited[nx][ny][0] = visited[x][y][w] + 1
                    q.append([nx, ny, 0])
                elif graph[nx][ny] == 0 and visited[nx][ny][w] == 0:
                    visited[nx][ny][w] = visited[x][y][w] + 1
                    q.append([nx, ny, w])
    return -1

print(bfs())
