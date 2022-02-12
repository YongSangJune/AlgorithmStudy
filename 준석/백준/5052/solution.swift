let tc = Int(readLine()!)!

for _ in 0..<tc {
    let n = Int(readLine()!)!
    var numbers: [String] = []
    var isUnique = true
    
    for _ in 0..<n {
        numbers.append(readLine()!)
    }
    numbers.sort()
    
    for i in 1..<numbers.count {
        if numbers[i].hasPrefix(numbers[i-1]) {
            isUnique = false
            break
        }
    }
    
    print(isUnique ? "YES" : "NO")
}
