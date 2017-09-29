/**
 * Lab 2: Debugging with Eclipse and Red Black Tree) <br />
 * The {@code RedBlackTree} class of integers only <br />
 * Reference: <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">
 *              https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 *            </a>
 *
 * Name: Nathan Klapstein
 * ID: 1449872
 *
 */
public class RedBlackTree {

    /**
     * Root node of the red black tree
     */
    private Node root = null;

    /**
     * Size of the tree
     */
    private int size = 0;


    private boolean checkRecursively(Node tree, int value) {
        // end of a branch pull out
        if (tree == null || tree.value == null) {
            return false;
        }

        // found the value pull out
        if (tree.value == value) {
            return true;
        } else {
            // recursively check the left branches then
            // if not found check the right branches
            if(checkRecursively(tree.lChild, value)) {
                return true;
            } else {
                return checkRecursively(tree.rChild, value);
            }
        }
    }


    /**
     * Search the tree to find if the value is contained
     * @param value     {@code int} the value to be checked
     * @return          {@code boolean} If contains, return {@code true}, otherwise return {@code false}
     */
    public boolean contains(int value) {
        return checkRecursively(root, value);
    }


    private void rotateTreeRight(Node node){
        Node parent = node.parent;

        // set rotated node's parent to grandparent
        node.parent = parent.parent;
        if(node.isParentsLeftChild()){
            node.parent.lChild = node;
        } else {
            node.parent.rChild = node;
        }


        // set rotated node's left child as parents right child
        parent.lChild = node.rChild;
        parent.lChild.parent = parent;

        // set rotated nodes left child as its parent
        node.rChild = parent;
        parent.parent = node;
    }


    private void rotateTreeLeft(Node node){
        Node parent = node.parent;

        // set rotated node's parent to grandparent
        node.parent = parent.parent;
        if(node.isParentsLeftChild()){
            node.parent.lChild = node;
        } else {
            node.parent.rChild = node;
        }

        // set rotated node's left child as parents right child
        parent.rChild = node.lChild;
        parent.rChild.parent = parent;

        // set rotated nodes left child as its parent
        node.lChild = parent;
        parent.parent = node;

    }


    private void fixInsertColor(Node node){

        // case 2: check if parent is black return if so
        if (node.isParentBlack()){
            return;
        }

        Node parent = node.parent;
        Node grandparent = node.parent.parent;
        Node uncle;
        if (parent.isParentsLeftChild()){
            uncle = grandparent.rChild;
        } else {
            uncle = grandparent.lChild;
        }

        // case 3:
        if (parent.color == Node.RED && uncle.color == Node.RED){
            parent.color = Node.BLACK;
            uncle.color = Node.BLACK;
            grandparent.color = Node.RED;
            fixInsertColor(grandparent);
        }

        // case 4:
        if(parent.color == Node.RED && uncle.color == Node.BLACK){
            //todo case 4:
            // case 5:
            parent.color = Node.BLACK;
            grandparent.color = Node.RED;

            if(node.isParentsLeftChild()){
                rotateTreeRight(grandparent);
            } else {
                rotateTreeLeft(grandparent);
            }
        }

    }


    private boolean insertRecursively(Node tree, int value, Node parent) {
        // end of a branch pull out

        if (tree.value == null) {
            tree.value = value;
            return true;
        } else {
            // recursively check the left branches then
            // if not found check the right branches
            if(tree.value > value) {
                if (tree.lChild == null) {
                    tree.lChild = new Node(value);
                    tree.lChild.parent = tree;
                    tree.lChild.color = Node.RED;
                    fixInsertColor(tree.lChild);
                } else {
                    insertRecursively(tree.lChild, value, tree);
                }
                return true;

            } else {
                if (tree.rChild == null) {
                    tree.rChild = new Node(value);
                    tree.rChild.parent = tree;
                    tree.rChild.color = Node.RED;
                    fixInsertColor(tree.rChild);
                } else {
                    insertRecursively(tree.rChild, value, tree);
                }
                return true;
            }
        }
    }


    /**
     * Insert an integer to the tree
     * @param value      {@code int} New element to be inserted
     */
    public void insert(int value) {
        // TODO: Lab 2 Part 2-2 -- insert an integer into the tree
        if (checkRecursively(root, value)) {
            return;
        }

        if (root == null) {
            root = new Node(value);
            root.color = Node.BLACK;
        } else {
            insertRecursively(root, value, null);
        }
    }

    /**
     * Get the size of the tree
     * @return          {@code int} size of the tree
     */
    public int size() {
        return size;
    }


    /**
     * Cast the tree into a string
     * @return          {@code String} Printed format of the tree
     */
    @Override public String toString() {
        // TODO: Lab 2 Part 2-3 -- print the tree, where each node contains both value and color
        // You can print it by in-order traversal
        String treeString = "";


        return treeString;
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        for (int i = 0; i < 10; i++)
            rbt.insert((int) (Math.random() * 200));

        assert rbt.root.color == RedBlackTree.Node.BLACK;
        System.out.println(rbt.root);           // This helps to figure out the tree structure
        System.out.println(rbt);

        //tests
        System.out.println(rbt.root.lChild.value);
    }


    /**
     * The {@code Node} class for {@code RedBlackTree}
     */
    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public Integer value;
        public boolean color = BLACK;
        public Node parent = null, lChild = null, rChild = null;

        public Node(Integer value) {             // By default, a new node is black with two NIL children
            this.value = value;
            if (value != null) {
                lChild = new Node(null);         // And the NIL children are both black
                lChild.parent = this;
                rChild = new Node(null);
                rChild.parent = this;
            }
        }


        /**
         * check if a node is a parents left child
         * @return          {@code boolean}
         */
        public boolean isParentsLeftChild(){
            return this == this.parent.lChild;
        }


        /**
         * check if a node's parent black
         * @return          {@code boolean}
         */
        public boolean isParentBlack(){
            return this.parent.color == BLACK;
        }


        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + ">" : "[" + value + "]";
        }
    }

}