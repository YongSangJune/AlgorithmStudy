import Foundation

let N = Int(readLine()!)!
let word = readLine()!.map { String($0) }
let reversedWord = word.reversed().map { $0 }

var dp = [[Int]].init(repeating: [Int](repeating: 0, count: N+1), count: N+1)


for i in 1..<N+1 {
    for j in 1..<N+1 {
        if word[i-1] == reversedWord[j-1] {
            dp[i][j] = dp[i-1][j-1] + 1
        } else {
            dp[i][j] = max(dp[i-1][j], dp[i][j-1])
        }
    }
}

print(N - dp[N][N])

