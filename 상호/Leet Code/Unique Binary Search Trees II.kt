/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

/* Divide and Conquer*/
class Solution {
   fun generateTrees(n: Int): List<TreeNode?> {
       return generate(1, n)
    }
    
    private fun generate(start: Int, end: Int): List<TreeNode?> {
        if (start == end) {
            return listOf(TreeNode(start))
        }
        
        if (start > end) {
            return listOf(null)
        }
        
        val generatedTrees = mutableListOf<TreeNode?>()
        
        for (i in start..end) {
            val leftList = generate(start, i - 1)
            val rightList = generate(i + 1, end)
            
            leftList.forEach { leftNode ->
                rightList.forEach { rightNode ->
                    val rootNode = TreeNode(i)
                    rootNode.left = leftNode
                    rootNode.right = rightNode
                    generatedTrees.add(rootNode)
                }
            }
        }
        
        return generatedTrees
    }
}