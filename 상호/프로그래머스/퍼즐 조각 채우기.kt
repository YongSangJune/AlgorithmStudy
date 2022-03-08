/* https://programmers.co.kr/learn/courses/30/lessons/84021 */

/* 구현 문제이지만 기가 막히게 어렵다  */

data class Coord(val row: Int, val col: Int)
data class Piece(val list: MutableList<Coord>)

class Solution {
    val pieceList = mutableListOf<Piece>()
    val dR = intArrayOf(-1, 1, 0, 0)
    val dC = intArrayOf(0, 0, -1, 1)

    lateinit var mTable: Array<IntArray>
    lateinit var board1: Array<IntArray>
    lateinit var board2: Array<IntArray>
    lateinit var board3: Array<IntArray>
    lateinit var board4: Array<IntArray>
    lateinit var solved: BooleanArray
    val emptySpaceSize = mutableListOf<Int>(0, 0)

    var number = 2

    fun solution(game_board: Array<IntArray>, table: Array<IntArray>): Int {
        var answer = 0

        mTable = table
        board1 = game_board
        board2 = Array(board1.size) { IntArray(board1.size) }
        board3 = Array(board1.size) { IntArray(board1.size) }
        board4 = Array(board1.size) { IntArray(board1.size) }

        doNumberingOnEmpty()
        solved = BooleanArray(number)

        rotateBoard()
        extractPieces()

        for (piece in pieceList) {
            if (check(board1, piece) || check(board2, piece) || check(board3, piece) || check(board4, piece)) {
                answer += piece.list.size
            }
        }

        return answer
    }

    private fun check(board: Array<IntArray>, piece: Piece): Boolean {
        for (row in board.indices) {
            for (col in board.indices) {
                val number = board[row][col]

                if (board[row][col] != 1 && solved[number].not() && piece.list.size == emptySpaceSize[number]) {
                    if (checkIfPossible(board, row, col, piece)) {
                        solved[number] = true
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun checkIfPossible(board: Array<IntArray>, startR: Int, startC: Int, piece: Piece): Boolean {
        val currentNumber = board[startR][startC]

        for (d in piece.list) {
            val row = startR + d.row
            val col = startC + d.col

            if (row !in board.indices || col !in board.indices || board[row][col] != currentNumber) return false
        }

        return true
    }
    private fun doNumberingOnEmpty() {
        for (row in board1.indices) {
            for (col in board1.indices) {
                if (board1[row][col] == 0) {
                    board1[row][col] = number
                    emptySpaceSize.add(0)
                    doNumbering(row, col)
                    number++
                }
            }
        }
    }

    private fun doNumbering(row: Int, col: Int) {
        emptySpaceSize[number]++

        for (i in 0 until 4) {
            val newRow = row + dR[i]
            val newCol = col + dC[i]

            if (newRow !in board1.indices || newCol !in board1.indices || board1[newRow][newCol] != 0) continue

            board1[newRow][newCol] = number
            doNumbering(newRow, newCol)
        }
    }

    private fun rotateBoard() {
        val lastIndex = board1.lastIndex

        for (row in board2.indices) {
            for (col in board2.indices) {
                board2[col][lastIndex - row] = board1[row][col]
            }
        }

        for (row in board3.indices) {
            for (col in board3.indices) {
                board3[lastIndex - row][lastIndex - col] = board1[row][col]
            }
        }

        for (row in board4.indices) {
            for (col in board4.indices) {
                board4[lastIndex - col][row] = board1[row][col]
            }
        }
    }

    private fun extractPieces() {
        val visited = Array(mTable.size) { BooleanArray(mTable.size) }

        for (row in mTable.indices) {
            for (col in mTable.indices) {
                if (mTable[row][col] == 1 && visited[row][col].not()) {
                    visited[row][col] = true
                    val piece = Piece(mutableListOf(Coord(0, 0)))
                    extract(row, col, row, col, visited, piece.list)
                    pieceList.add(piece)
                }
            }
        }
    }

    private fun extract(startRow: Int, startCol: Int, row: Int, col: Int, visited: Array<BooleanArray>, list: MutableList<Coord>) {
        for (i in 0 until 4) {
            val newRow = row + dR[i]
            val newCol = col + dC[i]

            if (newRow !in mTable.indices || newCol !in mTable.indices || visited[newRow][newCol] || mTable[newRow][newCol] == 0) continue

            list.add(Coord(startRow - newRow, startCol - newCol))
            visited[newRow][newCol] = true
            extract(startRow, startCol, newRow, newCol, visited, list)
        }
    }
}