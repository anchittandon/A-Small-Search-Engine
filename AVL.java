interface AVLInterface<T>{
    public int height(Node<T> N);
    public int max(int a, int b);
    private Node<T> rightRotate(Node<T> y);
    private Node<T> leftRotate(Node<T> x);
    private int getBalance(Node<T> N);
    public Node<T> insert(Node<T> node, T key);
    public void inOrder(Node<T> node)
}

public class AVL<T extends Comparable<T> > implements AVLInterface<T>{

    /*
        A height-balanced binary search tree.
    */
   
    Node<T> root;
    
    private class Node<T>{ 
        int height; 
        T key;
        Node<T> left, right; 

        Node (T d){ 
            key = d; 
            height = 1; 
        }
        public Node<T> getLeft(){
            return left;
        }
        public Node<T> getRight(){
            return right;
        }
        public void setLeft(Node<T> a){
            this.left = a;
        }
        public void setRight(Node<T> b){
            this.right = b;
        }
        public int getHeight(){
            return height;
        }
        public void setHeight(int x){
            this.height = x;
        }
        public T getKey(){
            return key;
        } 
    } 

    public int height(Node<T> N) {
    // Returns the height of a Node in the AVL Tree
        if (N == null) 
            return 0; 
        return N.getHeight(); 
    } 

    public int max(int a, int b){ 
        return (a > b) ? a : b; 
    } 

    private Node<T> rightRotate(Node<T> y){
    // Right rotation of AVL Tree 
        Node<T> x = y.getLeft(); 
        Node<T> T2 = x.getRight(); 

        x.setRight(y); 
        y.setLeft(T2); 

        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1); 
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1); 

        return x; 
    } 

    private Node<T> leftRotate(Node<T> x){ 
    // Left rotation of AVL Tree 
        Node<T> y = x.getRight(); 
        Node<T> T2 = y.getLeft(); 

        y.setLeft(x); 
        x.setRight(T2); 

        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1); 
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1); 

        return y; 
    } 

    private int getBalance(Node<T> N){
    // Returns the Balance Factor of the AVL tree
        if (N == null) 
            return 0; 
        return height(N.getLeft()) - height(N.getRight()); 
    } 

    public Node<T> insert(Node<T> node, T key){
    // Inserts an element into the AVL Tree
        if (node == null) 
            return (new Node<T>(key)); 

        if (key.compareTo(node.getKey()) < 0) 
            node.setLeft(insert(node.getLeft(), key)); 
        else if (key.compareTo(node.getKey()) > 0) 
            node.setRight(insert(node.getRight(), key)); 
        else
            return node; 

        node.setHeight(1 + max(height(node.getLeft()), height(node.getRight()))); 

        int balance = getBalance(node); 

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) < 0 ) 
            return rightRotate(node); 

        if (balance < -1 && key.compareTo(node.getRight().getKey())>0) 
            return leftRotate(node); 

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) > 0 ){ 
            node.setLeft(leftRotate(node.getLeft())); 
            return rightRotate(node); 
        } 

        if (balance < -1 && key.compareTo(node.getRight().getKey()) < 0){ 
            node.setRight(rightRotate(node.getRight())); 
            return leftRotate(node); 
        } 

        return node; 
    } 

    public void inOrder(Node<T> node){
    // Prints the output of inorder traversal of the AVL tree
        if (node != null){ 
            inOrder(node.getLeft()); 
            System.out.print(node.getKey() + " ");   
            inOrder(node.getRight()); 
        } 
    }

    public Node<T> search(T key){
    // Searches AVL Tree for node containing particular data
        if (node == null) 
            return null; 
        if (key.compareTo(node.getKey()) < 0) 
            return node.getLeft().search(key); 
        else if (key.compareTo(node.getKey()) > 0) 
            return node.getRight().search(key);
        else
            return node;
    }
}