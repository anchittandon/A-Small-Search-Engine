import java.util.*;

interface AVLInterface<T extends Comparable<T>>{
    public int height(AVL<T>.Node N);
    public int max(int a, int b);
    public AVL<T>.Node rightRotate(AVL<T>.Node y);
    public AVL<T>.Node leftRotate(AVL<T>.Node x);
    public int getBalance(AVL<T>.Node N);
    public AVL<T>.Node insert(AVL<T>.Node node, T key);
    public void inOrder(AVL<T>.Node node);
}

public class AVL<T extends Comparable<T> > implements AVLInterface<T>{

    /*
        A height-balanced binary search tree.
    */
   
    Node root;
    
    public class Node{ 
        int height; 
        T key;
        Node left, right; 

        Node (T d){ 
            key = d; 
            height = 1; 
        }
        public Node getLeft(){
            return left;
        }
        public Node getRight(){
            return right;
        }
        public void setLeft(Node a){
            this.left = a;
        }
        public void setRight(Node b){
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

    public AVL(){}   
    public AVL(Node node){
        root = node;
    }

    public Node getRoot(){
        return root;
    }

    public void setRoot(Node N){
        root = N;
    }

    public int height(Node N) {
    // Returns the height of a Node in the AVL Tree
        if (N == null) 
            return 0; 
        return N.getHeight(); 
    } 

    public int max(int a, int b){ 
        return (a > b) ? a : b; 
    } 

    public Node rightRotate(Node y){
    // Right rotation of AVL Tree 
        Node x = y.getLeft(); 
        Node T2 = x.getRight(); 

        x.setRight(y); 
        y.setLeft(T2); 

        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1); 
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1); 

        return x; 
    } 

    public Node leftRotate(Node x){ 
    // Left rotation of AVL Tree 
        Node y = x.getRight(); 
        Node T2 = y.getLeft(); 

        y.setLeft(x); 
        x.setRight(T2); 

        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1); 
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1); 

        return y; 
    } 

    public int getBalance(Node N){
    // Returns the Balance Factor of the AVL tree
        if (N == null) 
            return 0; 
        return height(N.getLeft()) - height(N.getRight()); 
    } 

    public Node insert(Node node, T key){
    // Inserts an element into the AVL Tree
        if (node == null) 
            return (new Node(key)); 

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

    public void inOrder(Node node){
    // Prints the output of inorder traversal of the AVL tree
        if (node != null){ 
            inOrder(node.getLeft()); 
            System.out.print(node.getKey() + " ");   
            inOrder(node.getRight()); 
        } 
    }

    public Node search(T key){
    // Searches AVL Tree for node containing particular data
        if (root == null) 
            return null; 
        if (key.compareTo(root.getKey()) < 0){
            Node node = root.getLeft();
            AVL<T> leftAVL = new AVL<T>(node);
            return leftAVL.search(key);
        } 
        else if (key.compareTo(root.getKey()) > 0) {
            Node node = root.getRight();
            AVL<T> rightAVL = new AVL<T>(node);
            return rightAVL.search(key);
        }
        else
            return root;
    }

    public static void main(String[] args)
    {
        AVL<Integer> node = new AVL<Integer>();
        Scanner in = new Scanner(System.in);
        int response;
        boolean exitFlag = false;
        while(!exitFlag)
        {
            System.out.print("Choose - \n\t1) Insert\n\t2) Search\n\t3) Print\n\t0) Exit\n: ");
            response = in.nextInt();
            AVL<Integer>.Node resp = null;
            switch(response)
            {
                case 1:
                    System.out.print("Enter number to insert: ");
                    response = in.nextInt();
                    node.setRoot(node.insert(node.getRoot(),response));
                    break;
                case 2:
                    System.out.println("Enter number to search: ");
                    response = in.nextInt();
                    if(node.search(response) == null)
                    {
                        System.out.print("Not inserted\n");
                    }
                    else
                    {
                        System.out.print("Present in tree\n");
                    }
                    break;
                case 3:
                    node.inOrder(node.getRoot());
                    break;
                
                case 0:
                    System.out.println("Exiting");
                    exitFlag = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}