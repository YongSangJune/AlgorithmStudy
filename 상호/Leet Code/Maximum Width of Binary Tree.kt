/* https://leetcode.com/problems/maximum-width-of-binary-tree/ */

/** 모든 노드를 방문하면서 자신이 depth에서 몇 번째 노드인지
 * depth에 해당하는 리스트에 추가한다
 * 예를 들어, (1,2,3,4,null,null,5)에서 
 * Node(4)의 인덱스는 0, Node(5)의 인덱스는 3이 된다.
 */

import kotlin.math.max

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
class Solution {
    // 리스트마다 node가 해당 depth에서 몇 번째 인덱스인지를 저장한다
    private val depthNodeList = mutableListOf<MutableList<Int>>()
    
    fun widthOfBinaryTree(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        
        addIndex(root, 0, 0)
        return findMaxWidth()
    }
    
    private fun addIndex(node: TreeNode, depth: Int, myIndex: Int) {
        if (depthNodeList.size == depth) {
            depthNodeList.add(mutableListOf<Int>())
        }
        
        depthNodeList[depth].add(myIndex)
        
        if (node.left != null) {
            addIndex(node!!.left, depth+1, 2*myIndex)
        }
        if (node.right != null) {
            addIndex(node!!.right, depth+1, 2*myIndex+1)
        }
    }
    
    /**
     * 각 depth의 길이를 계산해서 그 중 최대 값을 반환한다.
     * width: 오른쪽 끝 노드 인덱스 - 왼쪽끝 노드 인덱스 + 1 
     */
    private fun findMaxWidth(): Int {
        var maxWidth = 1
        
        for (nodeList in depthNodeList) {
            val currentWidth = 
                if (nodeList.size == 1) 1
                else nodeList.last() - nodeList.first() + 1 
            maxWidth = max(maxWidth, currentWidth)
        }
        
        return maxWidth
    }
}