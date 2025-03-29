package edu.grinnell.csc207.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading
    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {

        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
    }

    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in the tree
     */
    public int size() {
        return sizeH(root);
    }

    private Node<T> insertH(T value, Node<T> root) {
        if (root == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(root.value) < 0) {
                root.left = insertH(value, root.left);
            } else {
                root.right = insertH(value, root.right);
            }
            return root;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertH(value, root);
    }

    ///// Part 1: Traversals
    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        ArrayList<T> lst = new ArrayList<T>();
        return toListInorderHelper(lst, root);
    }

    public List<T> toListInorderHelper(List<T> lst, Node<T> cur) {
        Node<T> temp = cur;
        ArrayList<T> tmplst = new ArrayList<T>();
        while (temp != null) {
            tmplst.add(temp.value);
            temp = temp.left;
        }

        for (int i = tmplst.size() - 1; i >= 0; i--) {
            lst.add(tmplst.get(i));
        }
        if (cur.right != null) {
            lst = toListInorderHelper(lst, cur.right);
        }

        return lst;
    }

    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        ArrayList<T> lst = new ArrayList<T>();
        return toListPreorderHelper(lst, root);
    }

    public List<T> toListPreorderHelper(List<T> lst, Node<T> cur) {
        if (cur != null) {
            lst.add(cur.value);
            if (cur.left != null) {
                lst = toListPreorderHelper(lst, cur.left);
            }
            if (cur.right != null) {
                lst = toListPreorderHelper(lst, cur.right);
            }
        }
        return lst;
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        ArrayList<T> lst = new ArrayList<T>();
        return toListPostorderHelper(lst, root);
    }

    public List<T> toListPostorderHelper(List<T> lst, Node<T> cur) {
        if (cur != null) {
            if (cur.left != null) {
                lst = toListPostorderHelper(lst, cur.left);
            }
            if (cur.right != null) {
                lst = toListPostorderHelper(lst, cur.right);
            }
            lst.add(cur.value);
        }
        return lst;
    }

    ///// Part 2: Contains
    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        ArrayList lst = new ArrayList();
        lst = containsHelper(root, value, lst);
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).equals(true)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList containsHelper(Node<T> cur, T value, ArrayList lst) {
        if (cur != null) {
            if (cur.value == value) {
                lst.add(true);
            } else {
                lst.add(false);
            }
            if (cur.left != null) {
                lst = containsHelper(cur.left, value, lst);
            }
            if (cur.right != null) {
                lst = containsHelper(cur.right, value, lst);
            }
        }
        return lst;

    }

    ///// Part 3: Pretty Printing
    /**
     * @return a string representation of the tree obtained via an pre-order
     * traversal in the form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        StringBuffer buf = new StringBuffer("[");
        buf = toStringPreorderHelper(root, root, buf);
        buf.append("]");
        return buf.toString();
    }

    public <T extends Comparable<? super T>> StringBuffer toStringPreorderHelper(Node<T> cur, Node<T> root, StringBuffer buf) {
        if (cur != null) {
            if (cur.equals(root)) {
                buf.append(cur.value);
            } else {
                buf.append(", " + cur.value);
            }
            if (cur.left != null) {
                buf = toStringPreorderHelper(cur.left, root, buf);
            }
            if (cur.right != null) {
                buf = toStringPreorderHelper(cur.right, root, buf);
            }
        }
        return buf;
    }

    ///// Part 4: Deletion
    /*
     * The three cases of deletion are:
     * 1. No nodes beneath
     * 2. One child
     * 3. Two children
     */
    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code>
     * found in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (contains(value)) {
            //ArrayList<Node<T>> lst = new ArrayList();
            //lst = deleteHelper1(root, value, lst);
            //Node<T> temp = lst.get(0);
            root = deleteHelper2(root, value);

        }

    }

    public Node<T> deleteHelper2(Node<T> cur, T value) {

        if (cur != null) {
            if (cur.value == value) {
                if (cur.right == null && cur.left == null) {
                    cur = null;
                } else if (cur.right == null) {
                    cur = cur.left;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else {
                    Node<T> temp = cur;
                    temp = temp.left;
                    while (temp.right != null) {
                        temp = temp.right;
                    }
                    cur.value = temp.value;
                    if (temp.right == null && temp.left == null) {
                        temp = null;
                    } else if (temp.right == null) {
                        temp = temp.left;
                    } else if (temp.left == null) {
                        temp = temp.right;
                    }
                }
                return cur;
            }
            if (cur.left != null) {
                cur.left = deleteHelper2(cur.left, value);
            }
            if (cur.right != null) {
                cur.right = deleteHelper2(cur.right, value);
            }

        }
        return cur;
    }
}
