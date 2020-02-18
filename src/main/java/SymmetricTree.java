import java.util.ArrayList;
import java.util.List;

public class SymmetricTree {


    private static int TEMP = 0;

    public static void main(String[] args) {
        isSymmetric(null);
    }

    private static boolean isSymmetric(TreeNode root) {

        //1223443;
        //1 2 3 3 null 2 null
        //[5,4,1,null,1,null,4,2,null,2,null]
        TreeNode node = new TreeNode(5);
        TreeNode left = new TreeNode(4);
        left.left = null;
        TreeNode node1 = new TreeNode(1);
        node1.left = new TreeNode(2);
        node1.right = null;
        left.right = node1;

        TreeNode right = new TreeNode(1);
        right.left = null;
        TreeNode node4 = new TreeNode(4);
        node4.left = new TreeNode(2);
        node4.right = null;
        right.right = node4;

        node.left = left;
        node.right = right;

        Integer[] arr = new Integer[]{6, 82, 82, null, 53, 53, null, -58, null, null, -58, null, -85, -85, null, -9, null,
                null, -9, null, 48, 48, null, 33, null, null, 33, 81, null, null, 81, 5, null, null, 5, 61, null, null, 61, null,
                9, 9, null, 91, null, null, 91, 72, 7, 7, 72, 89, null, 94, null, null, 94, null, 89, -27, null, -30, 36, 36, -30, null,
                -27, 50, 36, null, -80, 34, null, null, 34, -80, null, 36, 50, 18, null, null, 91, 77, null, null, 95, 95, null, null, 77,
                91, null, null, 18, -19, 65, null, 94, null, -53, null, -29, -29, null, -53, null, 94, null, 65, -19, -62, -15, -35, null,
                null, -19, 43, null, -21, null, null, -21, null, 43, -19, null, null, -35, -15, -62, 86, null, null, -70, null, 19, null,
                55, -79, null, null, -96, -96, null, null, -79, 55, null, 19, null, -70, null, null, 86, 49, null, 25, null, -19, null,
                null, 8, 30, null, 82, -47, -47, 82, null, 30, 8, null, null, -19, null, 25, null, 49};
        node = createBinaryTreeByArray(arr, 0);

        System.out.println("深度是: " + getMaxDepth(node));
        toAllTree(node);

        System.out.println();
        List<Integer> list2 = new ArrayList<>();
        inOrderTraversal(node, list2);
        System.out.println(is(list2));
        System.out.println("深度是: " + getMaxDepth(node));


        return is(list2);
    }


    private static void inOrderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        } else {
            //递归遍历
            inOrderTraversal(node.left, list);
            System.out.print(node.val + ",");
            list.add(node.val);
            inOrderTraversal(node.right, list);
        }
    }

    /**
     * 将一颗二叉树转换为满二叉树
     */
    private static TreeNode toAllTree(TreeNode node) {
        TreeNode root = node;
        int deep = getMaxDepth(root);
        inflateNode(root, deep);
        return root;
    }


    private static void inflateNode(TreeNode node, int deep) {
        int curDeep = deep;
        if (curDeep <= 1) {
            return;
        }
        if (node.left == null) {
            node.left = new TreeNode(TEMP);
        }
        if (node.right == null) {
            node.right = new TreeNode(TEMP);
        }
        curDeep--;
        inflateNode(node.left, curDeep);
        inflateNode(node.right, curDeep);
    }

    /**
     * 获取二叉树的深度
     */
    public static int getMaxDepth(TreeNode root) {
        if (root == null)
            return 0;
        else {
            int left = getMaxDepth(root.left);
            int right = getMaxDepth(root.right);
            return 1 + Math.max(left, right);
        }
    }

    private static boolean is(List<Integer> list) {
        boolean is = true;
        int size = list.size();
        for (int index = 0; index < size; index++) {
            if (!list.get(index).equals(list.get(size - index - 1))) {
                return false;
            }

        }
        return is;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    private static TreeNode createBinaryTreeByArray(Integer[] array, int index) {
        TreeNode tn = null;
        if (index < array.length) {
            Integer value = array[index];
            if (value == null) {
                return null;
            }
            tn = new TreeNode(value);
            tn.left = createBinaryTreeByArray(array, 2 * index + 1);
            tn.right = createBinaryTreeByArray(array, 2 * index + 2);
            return tn;
        }
        return tn;
    }

}
