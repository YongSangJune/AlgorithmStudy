import Foundation

func dfs(grid: [[Int]], dp: [[Int]], curX: Int, curY: Int) -> Int {
    if curX == destination.0 && curY == destination.1 {
        return 1
    }
    if dp[curX][curY] != -1 {
        return dp[curX][curY]
    }
    var dp = dp
    dp[curX][curY] = 0
    
    for i in 0..<4 {
        let nx = curX + dx[i]
        let ny = curY + dy[i]
        
        if 0 <= nx && nx < N && 0 <= ny && ny < M && grid[curX][curY] > grid[nx][ny] {
            dp[curX][curY] += dfs(grid: grid, dp: dp, curX: nx, curY: ny)
        }
    }
    
    return dp[curX][curY]
}

let NM = readLine()!.split(separator: " ").compactMap { Int($0) }
let N = NM[0]
let M = NM[1]
var grid: [[Int]] = []

(0..<N).forEach { _ in
    let arr = readLine()!.split(separator: " ").compactMap { Int($0) }
    grid.append(arr)
}

let destination = (N-1, M-1)
let dx = [0, 0, 1, -1]
let dy = [1, -1, 0, 0]
var dp: [[Int]] = []

(0..<N).forEach { _ in
    dp.append(Array(repeating: -1, count: M))
}

print(dfs(grid: grid, dp: dp, curX: 0, curY: 0))

