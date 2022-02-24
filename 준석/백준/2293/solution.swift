import Foundation


let input = readLine()!.split(separator: " ").compactMap { Int($0) }
let N = input[0], K = input[1]

var coins: [Int] = []

for _ in 0..<N {
    let data = Int(readLine()!)!
    
    coins.append(data)
}

var dp = Array(repeating: 0, count: K+1)

dp[0] = 1

for coin in coins {
    for j in 1...K {
        if coin <= j {
            dp[j] += dp[j - coin]
            if dp[j] > Int(pow(2.0, 31.0)) {
                dp[j] = 0
            }
        }
    }
}

print(dp[K])

