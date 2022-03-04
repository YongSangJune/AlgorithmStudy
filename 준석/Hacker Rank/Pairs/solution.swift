func pairs(k: Int, arr: [Int]) -> Int {
    var subSet = Set<Int>()
    
    for number in arr {
        subSet.insert(k + number)
    }
    
    return Set(arr).intersection(subSet).count
}
