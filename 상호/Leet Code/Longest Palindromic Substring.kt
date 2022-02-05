class Solution {
    fun longestPalindrome(s: String): String {
        val array = s.toCharArray()
        
        // [answerStart, answerEnd)
        var answerStart = 0
        var answerEnd = 1 // 가장 긴 길이가 1일 경우 
        
        // 길이 2 ~ 전체인 문자열 모두 탐색, 투 포인터 사용
        for (size in 2..array.size) {
            var start = 0
            var end = size
            
            // 현재 길이 문자열을 모두 확인
            while (end <= array.size){
                if (isPalindromic(array, start, end)) {
                    answerStart = start
                    answerEnd = end
                    break
                }
                
                start++
                end++
            }
        }
        
        return s.substring(answerStart, answerEnd)
    }
    
    private fun isPalindromic(array: CharArray, start: Int, end: Int): Boolean {
        val lastIndex = end - 1
        
        for (i in 0 until (end - start) / 2) {    
            if (array[start + i] != array[lastIndex - i]){
                return false
            }
        }
        
        return true
    }
}