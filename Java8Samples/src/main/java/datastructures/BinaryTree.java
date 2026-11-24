package datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Binary tree basics: visit the nodes in different orders and level by level,
 * find the height, check a binary search tree (BST), and find the lowest common ancestor.
 *
 * <p>Keywords: TreeNode, recursion, inorder, preorder, postorder, level order (BFS with a queue),
 * height, BST check, lowest common ancestor (LCA).
 *
 * <p>First written: 2026-11-24.
 */
public class BinaryTree {

    private static class TreeNode {
        final int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        //         5
        //       /   \
        //      3     8
        //     / \   / \
        //    2   4 7   9
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);

        List<Integer> inorder = new ArrayList<>();
        inorder(root, inorder);
        System.out.println("Inorder (left, node, right): " + inorder);     // [2, 3, 4, 5, 7, 8, 9]
        List<Integer> preorder = new ArrayList<>();
        preorder(root, preorder);
        System.out.println("Preorder (node, left, right): " + preorder);   // [5, 3, 2, 4, 8, 7, 9]
        List<Integer> postorder = new ArrayList<>();
        postorder(root, postorder);
        System.out.println("Postorder (left, right, node): " + postorder); // [2, 4, 3, 7, 9, 8, 5]
        System.out.println("Level by level: " + levelOrder(root)); // [[5], [3, 8], [2, 4, 7, 9]]
        System.out.println("Height: " + height(root));                     // 3
        System.out.println("Is a valid BST: " + isValidBst(root, Long.MIN_VALUE, Long.MAX_VALUE));

        TreeNode notBst = new TreeNode(5);
        notBst.left = new TreeNode(6); // 6 is bigger than 5, so it can't be on the left
        System.out.println("Is a valid BST: " + isValidBst(notBst, Long.MIN_VALUE, Long.MAX_VALUE));

        System.out.println("Lowest common ancestor of 2 and 4: "
                + lowestCommonAncestor(root, 2, 4).value);                // 3
        System.out.println("Lowest common ancestor of 2 and 9: "
                + lowestCommonAncestor(root, 2, 9).value);                // 5
    }

    static void inorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        inorder(node.left, out);
        out.add(node.value);
        inorder(node.right, out);
    }

    static void preorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        out.add(node.value);
        preorder(node.left, out);
        preorder(node.right, out);
    }

    static void postorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        postorder(node.left, out);
        postorder(node.right, out);
        out.add(node.value);
    }

    // Level by level with a queue (breadth-first search)
    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size(); // the number of nodes on this level
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                level.add(node.value);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }

    // The number of levels: 1 + the height of the taller side
    static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // In a BST every node must be between the limits set by all the nodes above it,
    // not only its parent
    static boolean isValidBst(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return isValidBst(node.left, min, node.value) && isValidBst(node.right, node.value, max);
    }

    // The lowest node that has both a and b below it (a node also counts as below itself).
    // It assumes both a and b are in the tree. If only one is there, it returns that node.
    static TreeNode lowestCommonAncestor(TreeNode node, int a, int b) {
        if (node == null || node.value == a || node.value == b) {
            return node;
        }
        TreeNode left = lowestCommonAncestor(node.left, a, b);
        TreeNode right = lowestCommonAncestor(node.right, a, b);
        if (left != null && right != null) {
            return node; // a is on one side and b is on the other
        }
        return left != null ? left : right;
    }
}
