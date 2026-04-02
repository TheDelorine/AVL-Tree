public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;


    private int getHeight(TreeNode<T> node) {
        return node == null ? 0 : node.getHeight();
    }

    private void updateHeight(TreeNode<T> node) {
        if (node != null) {
            node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        }
    }

    private int getBalanceFactor(TreeNode<T> node) {
        return node == null ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }


    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.getLeft();
        TreeNode<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        x.setParent(y.getParent());
        y.setParent(x);
        if (T2 != null) T2.setParent(y);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.getRight();
        TreeNode<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        y.setParent(x.getParent());
        x.setParent(y);
        if (T2 != null) T2.setParent(x);

        updateHeight(x);
        updateHeight(y);

        return y;
    }


    private TreeNode<T> balance(TreeNode<T> node) {
        if (node == null) return null;

        updateHeight(node);

        int bf = getBalanceFactor(node);

        if (bf > 1 && getBalanceFactor(node.getLeft()) >= 0) {
            return rotateRight(node);
        }

        if (bf > 1 && getBalanceFactor(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        if (bf < -1 && getBalanceFactor(node.getRight()) <= 0) {
            return rotateLeft(node);
        }

        if (bf < -1 && getBalanceFactor(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }


    public void add(T value) {
        root = add(root, value, null);
    }

    private TreeNode<T> add(TreeNode<T> current, T value, TreeNode<T> parent) {
        if (current == null) {
            TreeNode<T> newNode = new TreeNode<>(value);
            newNode.setParent(parent);
            return newNode;
        }

        if (value.compareTo(current.getValue()) < 0) {
            current.setLeft(add(current.getLeft(), value, current));
        } else if (value.compareTo(current.getValue()) > 0) {
            current.setRight(add(current.getRight(), value, current));
        } else {
            return current;
        }

        updateHeight(current);
        return balance(current);
    }


    public TreeNode<T> search(T value) {
        TreeNode<T> current = root;

        while (current != null) {
            int cmp = value.compareTo(current.getValue());

            if (cmp == 0) {
                return current;
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }

    public boolean contains(T value) {
        return search(value) != null;
    }


    public void delete(T value) {
        root = delete(root, value);
    }

    private TreeNode<T> delete(TreeNode<T> current, T value) {
        if (current == null) return null;

        int cmp = value.compareTo(current.getValue());

        if (cmp < 0) {
            current.setLeft(delete(current.getLeft(), value));
            if (current.getLeft() != null) {
                current.getLeft().setParent(current);
            }
        } else if (cmp > 0) {
            current.setRight(delete(current.getRight(), value));
            if (current.getRight() != null) {
                current.getRight().setParent(current);
            }
        } else {
            // Нашли узел для удаления

            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }

            if (current.getLeft() == null) {
                TreeNode<T> rightChild = current.getRight();
                rightChild.setParent(current.getParent());
                return rightChild;
            }

            if (current.getRight() == null) {
                TreeNode<T> leftChild = current.getLeft();
                leftChild.setParent(current.getParent());
                return leftChild;
            }

            // Узел имеет двух детей - ищем минимальный в правом поддереве
            TreeNode<T> successor = findMin(current.getRight());
            current.setValue(successor.getValue());
            current.setRight(delete(current.getRight(), successor.getValue()));
            if (current.getRight() != null) {
                current.getRight().setParent(current);
            }
        }

        updateHeight(current);
        return balance(current);
    }

    private TreeNode<T> findMin(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current != null && current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    // ========== ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ ==========

    public int size() {
        return size(root);
    }

    private int size(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    // ========== ОБХОДЫ ==========

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(TreeNode<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.println(node.getValue());
            inOrder(node.getRight());
        }
    }
}