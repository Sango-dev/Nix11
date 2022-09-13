package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.comparator.CustomComparator;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.model.product.TechProduct;
import ua.com.alevel.hw2.model.product.TechProductType;
import ua.com.alevel.hw2.tree.BSTree;

public class Tree implements Command{
    public static final int N = 6;

    @Override
    public void execute() {
        BSTree<TechProduct> bsTree = new BSTree<>(new CustomComparator<TechProduct>());
        for (int i = 0; i < N; i++) {
            Phone phone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
            bsTree.addNode(phone);
        }

        System.out.println("Show tree:");
        bsTree.showTree();

        System.out.println("Price of left subtree: " + bsTree.countPriceOfLeftSubtree(bsTree.getRoot()));
        System.out.println("Price of right subtree: " + bsTree.countPriceOfRightSubtree(bsTree.getRoot()));
    }

}
