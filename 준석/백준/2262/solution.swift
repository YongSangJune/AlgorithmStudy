import Foundation

let N = Int(readLine()!)!
var players = readLine()!.split(separator: " ").compactMap { Int($0) }

var count = 0

while true {
    let max = players.max()
    let lowest = players.firstIndex { $0 == max }!
    let length = players.count

    if length == 1 {
        print(count)
        break
    }
    var gap = 0
    if lowest == 0 {
        gap = players[lowest] - players[lowest + 1]
    } else if lowest == length - 1 {
        gap = players[lowest] - players[lowest - 1]
    } else {
        if players[lowest - 1] > players[lowest + 1] {
            gap = players[lowest] - players[lowest - 1]
        } else {
            gap = players[lowest] - players[lowest + 1]
        }
    }
    count += gap
    players.remove(at: lowest)
}



