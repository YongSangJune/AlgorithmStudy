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
class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
}

private const val MIN = Int.MIN_VALUE
private const val MAX = Int.MAX_VALUE

/**
 * 버전1
 */
class Solution {
    fun isValidBST(root: TreeNode?): Boolean {
        return isValid(root, MIN, MAX)
    }

    private fun isValid(node: TreeNode?, lower: Int, upper: Int): Boolean {
        if (node == null) return true
        
        // 오버, 언더플로우 대비 코드 -> 애초에 입력으로 안들어오는 것 같다. 
        if (node.`val` == MIN && node.left != null) return false
        if (node.`val` == MAX && node.right != null) return false

        if (node.left != null) {
            val leftValue = node.left!!.`val`
            if (leftValue < lower || leftValue >= node.`val`) return false
        }
        if (node.right != null) {
            val rightValue = node.right!!.`val`
            if (rightValue > upper || rightValue <= node.`val`) return false
        }

        return isValid(node.left, lower, node.`val` - 1) && isValid(node.right, node.`val` + 1, upper)
    }
}

/**
 * 버전2 - 버전1과 논리는 같지만 
 * null을 미리 처리해서 속도를 개선함
 */
class Solution {
    
    fun isValidBST(root: TreeNode?): Boolean {
        if (root == null) return true
        
        return isValid(root, MIN, MAX)
    }
    
    private fun isValid(node: TreeNode, lower: Int, upper: Int): Boolean {
        var isLeftValid = true
        var isRightValid = true
        
        // 왼쪽 자식이 왼쪽 범위를 만족하면,
        // 왼쪽 서브 트리 전체를 재귀호출로 확인한다.
        // 자식이 null이면 항상 true이므로 초기값을 true로 설정
        if (node.left != null) {
            val leftValue = node.left.`val`
            isLeftValid = if (leftValue >= lower && leftValue < node.`val`) {
                isValid(node.left, lower, node.`val` - 1)
            } else {
                false
            }
        }
        
        // 위의 로직과 같다
        if (node.right != null) {
            val rightValue = node.right.`val`
            isRightValid = if (rightValue > node.`val` && rightValue <= upper) {
                isValid(node.right, node.`val`+1, upper)
            } else {
                false
            }
        }

        return isLeftValid && isRightValid
        
    }
}