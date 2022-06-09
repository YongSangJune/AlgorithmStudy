/* https://leetcode.com/problems/n-queens/submissions/ */
/* 완전탐색 - 백트랙킹, 구현이 은근 까다롭다! */

import java.util.*

class Solution {
    
    private lateinit var isColAvail: BooleanArray // 사용가능한 column인가?
    private lateinit var board: Array<CharArray>  // 현재 체스판 상태
    private val answerList = mutableListOf<List<String>>()
    
    fun solveNQueens(n: Int): List<List<String>> {
        isColAvail = BooleanArray(n) { true }
        board = Array(n) {
            CharArray(n) {'.'}
        }
        
        putQueen(0)
        
        return answerList
    }
    
    // board[row]에 퀸을 둘 수 있어야 다음 row+1로 넘어간다
    private fun putQueen(row: Int) {

        // n줄 모두 선택했으면 정답에 추가한다
        if (row == board.size) {
            copyBoardToAnswer()
            return
        }
        
        for (currentCol in board.indices) {
            // 위에 행에서 currentCol을 사용중이면 넘어간다
            if (isColAvail[currentCol].not()) continue
            
            var available = true
            
            // 왼쪽 위 대각선와 오른쪽 위 대각선에 퀸이 있는지 검사한다
            for (tempRow in row - 1 downTo 0) {
                val step = row - tempRow // row에서 얼마나 위의 row인지를 알려준다
                val leftCol = currentCol - step
                val rightCol = currentCol + step
                
                // 위 대각선에 퀸이 하나라도 있으면 둘 수 없다 
                if ((leftCol >= 0 && board[tempRow][leftCol] == 'Q') || rightCol < board.size && board[tempRow][rightCol] == 'Q') {
                    available = false
                    break
                }
            }
        
            // 둘 수 있으면 체스판과 col 상태에 체크하고 다음 행으로 넘어간다
            if (available) {
                isColAvail[currentCol] = false
                board[row][currentCol] = 'Q'
                putQueen(row + 1)
                isColAvail[currentCol] = true
                board[row][currentCol] = '.'
            }
        }
    }
    
    private fun copyBoardToAnswer() {
        board.map { charArray ->
            StringBuilder().append(charArray).toString()
        }.let {
            answerList.add(it)
        }
    }
}