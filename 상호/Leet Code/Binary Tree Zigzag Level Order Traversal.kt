class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
}

class Solution {
    private val answer = mutableListOf<LinkedList<Int>>()

    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) {
            return listOf<List<Int>>()
        }

        zigzagBfs(root)
        return answer
    }

    private fun zigzagBfs(root: TreeNode) {
        val q: Queue<Pair<TreeNode, Int>> = LinkedList()

        q.add(Pair(root, 0))

        while(q.isNotEmpty()) {
            val (node, level) = q.poll()

            if (answer.size == level) {
                answer.add(LinkedList())
            }

            // 지그재그 구현
            // 오른쪽 자식부터 탐색하면 반대로 해야 함
            if (level % 2 == 0) {
                answer[level].addLast(node.`val`)
            } else {
                answer[level].addFirst(node.`val`)
            }

            if (node.left != null) q.add(Pair(node.left!!, level+1))
            if (node.right != null) q.add(Pair(node.right!!, level+1))
        }
    }
}