//extension Int {
//    var isOdd: Bool {
//        return self % 2 == 1
//    }
//}
//
//let NK = readLine()!.split(separator: " ").compactMap { Int($0) }
//let N = NK[0]
//let K = NK[1]
//
//let arr = readLine()!.split(separator: " ").compactMap { Int($0) }
//
//var answer = 0
//
//for i in 0..<N {
//    var length = 1
//    var temp = 0
//
//    if arr[i].isOdd {
//        temp += 1
//    }
//
//    for j in i+1..<N {
//        if arr[j].isOdd {
//            temp += 1
//        }
//        if temp < 0 {
//            break
//        }
//        length += 1
//    }
//    answer = max(answer, length)
//}
//
//print(answer - K)


