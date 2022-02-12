class Solution(object):
    def __init__(self):
        self.nodes = []
        
    def inorder(self, root):
        if root == None:
            return
        else:
            self.inorder(root.left)
            self.nodes.append(root.val)
            self.inorder(root.right)
            return
        
    def isValidBST(self, root):
        self.inorder(root)
        
        for i in range(len(self.nodes) - 1):
            if self.nodes[i] >= self.nodes[i + 1]:
                return False
        return True
