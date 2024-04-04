class RBTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key;
        int value;
        Node left, right;
        boolean color;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;

    public void insert(int key, int value) {
        root = insert(root, key, value);
        root.color = BLACK;
    }

    private Node insert(Node node, int key, int value) {
        if (node == null)
            return new Node(key, value);

        if (key < node.key)
            node.left = insert(node.left, key, value);
        else if (key > node.key)
            node.right = insert(node.right, key, value);
        else
            node.value = value;

        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        return node;
    }

    public int get(int key) {
        Node node = get(root, key);
        return node == null ? -1 : node.value;
    }

    private Node get(Node node, int key) {
        while (node != null) {
            if (key < node.key)
                node = node.left;
            else if (key > node.key)
                node = node.right;
            else
                return node;
        }
        return null;
    }

    public int remove(int key) {
        int value = get(key);
        root = remove(root, key);
        return value;
    }

    private Node remove(Node node, int key) {
        if (node == null)
            return null;

        if (key < node.key)
            node.left = remove(node.left, key);
        else if (key > node.key)
            node.right = remove(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;
            else {
                Node temp = min(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = deleteMin(node.right);
            }
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null)
            return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == RED;
    }

    private Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = node.color;
        node.color = RED;
        return temp;
    }

    private Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = node.color;
        node.color = RED;
        return temp;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public static void main(String[] args) {
        RBTree rbTree = new RBTree();

        rbTree.insert(10, 100);
        rbTree.insert(5, 50);
        rbTree.insert(15, 150);
        rbTree.insert(3, 30);
        rbTree.insert(7, 70);
        rbTree.insert(12, 120);
        rbTree.insert(18, 180);

        //TEST ALGORYTMU

        System.out.println("Wartosc dla klucza 5: " + rbTree.get(5));

        System.out.println("usuwanie klucza 10, wartosc: " + rbTree.remove(10));

        System.out.println("Wysokosc drzewa: " + rbTree.height());
    }
}

