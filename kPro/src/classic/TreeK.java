package classic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeK {


    //层次遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList();
        if (root == null)
            return res;
        int count = 1;
        TreeNode temp;
        queue.add(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> aList = new ArrayList<>();
            count = queue.size();
            for (int i = 0; i < count; i++) {
                temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
                aList.add(temp.val);
            }
            res.add(aList);
        }
        return res;
    }

    //锯齿形层次遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList();
        if (root == null)
            return res;
        int count = 1;
        TreeNode temp;
        queue.add(root);
        boolean flag = true;
        while (!queue.isEmpty()) {
            ArrayList<Integer> aList = new ArrayList<>();
            count = queue.size();
            for (int i = 0; i < count; i++) {
                temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
                if (flag) {
                    aList.add(temp.val);
                } else {
                    aList.add(0, temp.val);
                }
            }
            flag = !flag;
            res.add(aList);
        }
        return res;
    }


    //是否对称
    public boolean isSymmetric(TreeNode root) {
        if (root == null || root.left == null && root.right == null)
            return true;
        if (root.left == null || root.right == null)
            return false;
        return isMirror(root.left, root.right);
    }

    public boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isMirror(t1.right, t2.left)
                && isMirror(t1.left, t2.right);
    }

    public boolean isSameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isSameTree(t1.left, t2.left)
                && isSameTree(t1.right, t2.right);
    }

    //根据一棵树的前序遍历与中序遍历构造二叉树。
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0)
            return null;
        return processBuild(preorder, inorder, 0, 0, inorder.length);
    }

    private TreeNode processBuild(int[] preorder, int[] inorder, int start1, int start2, int len) {
        if (len == 1)
            return new TreeNode(preorder[start1]);
        if (len == 0)
            return null;
        TreeNode res = new TreeNode(preorder[start1]);
        int kIndex = -1;
        for (int i = start2; i < start2 + len; i++) {
            if (inorder[i] == preorder[start1]) {
                kIndex = i;
                break;
            }
        }
        int newLen = kIndex - start2;
        res.left = processBuild(preorder, inorder, start1 + 1, start2, newLen);
        res.right = processBuild(preorder, inorder, start1 + newLen + 1, kIndex + 1, len - newLen - 1);
        return res;
    }

    //根据一棵树的中序遍历与后序遍历构造二叉树
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        if (postorder == null || postorder.length == 0)
            return null;
        return processBuild2(postorder, inorder, 0, 0, inorder.length);
    }

    private TreeNode processBuild2(int[] postorder, int[] inorder, int start1, int start2, int len) {
        if (len == 1)
            return new TreeNode(postorder[start1]);
        if (len == 0)
            return null;
        TreeNode res = new TreeNode(postorder[start1 + len - 1]);
        int kIndex = -1;
        for (int i = start2; i < start2 + len; i++) {
            if (inorder[i] == postorder[start1 + len - 1]) {
                kIndex = i;
                break;
            }
        }
        int newLen = kIndex - start2;
        res.left = processBuild2(postorder, inorder, start1, start2, newLen);
        res.right = processBuild2(postorder, inorder, start1 + newLen, kIndex + 1, len - newLen - 1);
        return res;
    }

    //二叉树自底向上的层次遍历
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> aList = new ArrayList<>();
            int curSize = queue.size();
            for (int i = 0; i < curSize; i++) {
                TreeNode tNode = queue.poll();
                aList.add(tNode.val);
                if (tNode.left != null)
                    queue.add(tNode.left);
                if (tNode.right != null)
                    queue.add(tNode.right);
            }
            res.add(0, aList);
        }
        return res;
    }

    //升序有序数组转换为高度平衡二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0)
            return null;
        return processSortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode processSortedArrayToBST(int[] nums, int start, int end) {
        if (end == start)
            return new TreeNode(nums[start]);
        if (end < start)
            return null;
        int mid = (end + start) >> 1;
        TreeNode res = new TreeNode(nums[mid]);
        res.left = processSortedArrayToBST(nums, start, mid - 1);
        res.right = processSortedArrayToBST(nums, mid + 1, end);
        return res;
    }

    //二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    //给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        if (root.left != null && hasPathSum(root.left, sum - root.val))
            return true;
        if (root.right != null && hasPathSum(root.right, sum - root.val))
            return true;
        return false;
    }

}
