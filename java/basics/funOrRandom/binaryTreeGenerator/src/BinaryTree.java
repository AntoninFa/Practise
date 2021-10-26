public class BinaryTree {
    private final Integer[] tree;
    private final int treeSize;


    BinaryTree(Integer[] tree, int treeSize) {
        this.tree = tree;
        this.treeSize = treeSize;
    }

    public boolean generateBinT() {

        for (int n = 0; n < treeSize; n++) {
            tree[n] = n;
        }
        System.out.println(java.util.Arrays.toString(tree));
        return true;

        /**
        try {
            System.out.println(java.util.Arrays.toString(tree));
            recursivBin(2);
        } catch (NullPointerException e) {
            return false;
        }
        System.out.println(java.util.Arrays.toString(tree));
        return true;
         **/
    }

    private void recursivBin(int n) throws NullPointerException {
        if ((n * 2) +1 <= treeSize) {
            addNewChild((int)(n / 2), n);
            recursivBin(n +1);
        }
    }

    private void addNewChild(int parent , int childValue) throws NullPointerException {
        System.out.println("Im here" + parent + "--" + childValue);
        if (npAA(parent)) {
            setAlgoArray(parent, parent);
            System.out.println("moin");
        }


        if (npAA(parent * 2)) {
            setAlgoArray(parent *2, childValue);
        } else {
            try {
                setAlgoArray((parent * 2) + 1, childValue);
            } catch (NullPointerException e) {
                //This should never happen
                e.printStackTrace();
                throw new NullPointerException();
            }
        }

    }

    public void printBinaryTree() {
        int binHeight = log2(treeSize);
        int counter = 0;
        int x = 4;
        int currentBinHeight = binHeight;
        int lengthOfLRL;
        int lengthOfThisL;
        StringBuilder thisLine;
        //TODO System.out.println(binHeight);

        for (int l = 0; l < binHeight; l++) {
            lengthOfLRL = (int) Math.pow(2, --currentBinHeight);
            lengthOfThisL = (int) Math.pow(2, l);

            // Jedes Level des Baums
            int startingP = (lengthOfLRL / 2) * 5;
            thisLine = new StringBuilder();
            // Leerzeichen davor
            thisLine.append(" ".repeat(Math.max(0, startingP)));
            for (int m = 0; m < lengthOfThisL; m++) {
                thisLine.append(counter++).append(" ");
            }
            System.out.println(thisLine);
        }

    }

    private int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    private void setAlgoArray(int index, int value) {
        tree[index - 1] = value;
    }
    private int getAlgoArray(int index) {
        return tree[index - 1];
    }

    private boolean npAA(int index) {
        return (tree[index -1] == -1);
    }


}
