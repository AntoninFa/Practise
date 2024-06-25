public class fdf {
    private static final int treeSize = 16;
    private static final Integer[] tree = new Integer[treeSize];


    public static void main(String[] args) {
        BinaryTree binT = new BinaryTree(tree, treeSize);
        binT.generateBinT();
        binT.printBinaryTree();



    }




}
