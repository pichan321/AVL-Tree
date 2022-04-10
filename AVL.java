public class AVL<T extends Comparable<T>> {
    Node root;

    class Node {
        T data;
        int size;
        int height;
        Node left;
        Node right;

        public Node(T data, int size, int height) {
            this.data = data;
            this.size = size;
            this.height = height;
            this.left = null;
            this.right = null;
        }
    }

    public  AVL() {
        root = null;
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public int size() {
        return size(root);
    }   
    
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return 1 + size(x.left) + size(x.right);

    }

    public void put(T data) {
        root = put(root, data);
    }

    private Node put(Node x, T data) {
        if (x == null) {
            return new Node(data, 1, 0);
        }
        int comparison = data.compareTo(x.data);
        if (comparison < 0) {
            x.left = put(x.left, data);
        } else if (comparison > 0) {
            x.right = put(x.right, data);
        } else {
            x.data = data;
        }
        x.size = 1 + size(x.left) + size(x.right);
		x.height = 1 + Math.max(height(x.left), height(x.right));
		return balance(x);
    }

    public void delete(T data) {
        root = delete(root, data);
    }

    private Node delete(Node x, T data) {
        int comparison = data.compareTo(x.data);
        if (comparison < 0) {
            x.left = delete(x.left, data);
        } else if (comparison > 0) {
            x.right = delete(x.right, data);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node temp = x;
            x = min(temp.right);
            x.right = deleteMin(temp.right);
            x.left = temp.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
		x.height = 1 + Math.max(height(x.left), height(x.right));
		return balance(x);

    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        return deleteMin(x.left);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        return x.right;
    }

    public int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    public Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return y;
    }

    public Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return y;
    }


    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
              x.right  = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    public static void main(String[] args) {
        AVL<Integer> a = new AVL<>();
        for (int i = 0; i < 10000; i++) {
            a.put(i);
        }
        System.out.println(a.height());
    }


   
}
