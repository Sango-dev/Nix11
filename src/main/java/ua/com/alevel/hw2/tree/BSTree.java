package ua.com.alevel.hw2.tree;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw2.comparator.CustomComparator;
import ua.com.alevel.hw2.model.TechProduct;

@Getter
public class BSTree<T extends TechProduct> {

    private BSTNode root;
    private final CustomComparator<T> comparator;

    @Getter
    @Setter
    private class BSTNode {
        private T product;
        private BSTNode left;
        private BSTNode right;

        public BSTNode(T product, BSTNode left, BSTNode right) {
            this.product = product;
            this.left = left;
            this.right = right;
        }
    }

    public BSTree(CustomComparator<T> comparator) {
        this.comparator = comparator;
    }

    private boolean isEmpty() {
        return root == null;
    }

    private BSTNode createBSTNode(T product) {
        return new BSTNode(product, null, null);
    }

    public void addNode(T product) {
        insertNode(root, createBSTNode(product));
    }

    private void insertNode(BSTNode node, BSTNode nodeIns) {
        if (node == null) {
            node = nodeIns;
            root = node;
        } else if (comparator.compare(node.getProduct(), nodeIns.getProduct()) < 0 && node.left == null) {
            node.left = nodeIns;
        } else if (comparator.compare(node.getProduct(), nodeIns.getProduct()) > 0 && node.right == null) {
            node.right = nodeIns;
        } else {
            if (comparator.compare(node.getProduct(), nodeIns.getProduct()) < 0) {
                insertNode(node.left, nodeIns);
            } else {
                insertNode(node.right, nodeIns);
            }
        }
    }

    public void showTree() {
        printBST(root, 0);
    }

    private void printBST(BSTNode node, int len) {
        if (isEmpty()) {
            System.out.println("Tree's empty!!!");
            return ;
        }
        if (node != null) {
            printBST(node.right, len + 13);
            for (int i = 0; i < len; i++) {
                System.out.print(" ");
            }
            System.out.printf("Model:%4s\n", node.getProduct().getModel());
            for (int i = 0; i < len; i++) {
                System.out.print(" ");
            }
            System.out.printf("Count:%d\n", node.getProduct().getCount());
            for (int i = 0; i < len; i++) {
                System.out.print(" ");
            }
            System.out.printf("Price:%f", node.getProduct().getPrice());
            printBST(node.left, len + 13);
        } else {
            System.out.println("\n");
        }
    }

    public double countPriceOfLeftSubtree(BSTNode node) {
        if (node == root) {
            return countPriceOfLeftSubtree(node.left);
        } else if (node != null) {
            return node.getProduct().getPrice() + countPriceOfLeftSubtree(node.left) + countPriceOfLeftSubtree(node.right);
        }
        return 0.0;
    }

    public double countPriceOfRightSubtree(BSTNode node) {
        if (node == root) {
            return countPriceOfRightSubtree(node.right);
        } else if (node != null) {
            return node.getProduct().getPrice() + countPriceOfRightSubtree(node.left) + countPriceOfRightSubtree(node.right);
        }
        return 0.0;
    }

}
