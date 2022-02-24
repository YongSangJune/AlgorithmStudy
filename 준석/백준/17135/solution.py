import sys, itertools, deque

input = sys.stdin.readline

def bfs(src_x, src_y, board):
    dx, dy = [1, -1, 0, 0], [0, 0, 1, -1]
    visited = [[False] * M for _ in range(N)]
    visited[src_x][src_y] = True
    q = deque()
    q.append([src_x, src_y])

    while q:
        x, y = q.popleft()

        for i in range(4):
            nx, ny = x + dx[i], y + dy[i]

            if 0 <= nx < N and 0 <= ny < M and not visited[nx][ny]:
                if board[nx][ny] == '1' :
                    board[nx][ny] = '0'
                else:
                    visited[nx][ny] = True
                    q.append([nx, ny])


N, M, D = map(int, input().split())
board = [list(input().split()) for _ in range(N)]

archerCombinations = list(itertools.combinations(range(M), 3))

answer = float('inf')

for archerOneY, archerTwoY, archerThreeY in archerCombinations:
    boardCopy = board[:]


