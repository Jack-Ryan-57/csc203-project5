import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Node implements Comparable<Node> {

    private Point p;
    private Node prior, target;
    private int gVal, hVal, fVal;
    
    public Node(Point p, Node prior, Node target){
        this.p = p;
        this.prior = prior;
        this.target = target;
    }

    public Point getPoint(){
        return this.p;
    }

    public void calculateGVal(){
        if (prior == null){
            this.gVal = 0;
        }else{
            this.gVal = 1 + prior.gVal;
        }
    }

    public void calculateHVal(){
        this.hVal = distTo(target);
    }

    public void calculateFVal(){
        this.fVal = gVal + hVal;
    }

    public int distTo(Node other){
        if (other == null || other.equals(this)){
            return 0;
        }
        int x = other.getPoint().x - this.getPoint().x;
        int y = other.getPoint().y - this.getPoint().y;
        return (int)(Math.sqrt(x*x + y*y));
    }

    public Node prior(){ return this.prior; }

    public Node target(){ return this.target; }

    public void setFVal(int f){ this.fVal = f; }
    public void setGVal(int g){ this.gVal = g; }
    public int getGVal(){ return this.gVal; }
    public int getFVal(){ return this.fVal; }

    public boolean equals(Object other){
        if (other == null || !(other instanceof Node)){
            return false;
        }
        Node o = (Node)other;
        if (this.p.equals(o.p)){
            return true;
        }
        return false;
    }

    // Hashing for use in HashMaps
    // We don't want to add to the map if it has the same point
    public int hashCode(){
        int hash = 17;
        hash = hash*31 + p.x;   // check x
        hash = hash*31 + p.y;   // check y
        return hash;
    }


    // Compare f value of nodes
    @Override
    public int compareTo(Node o) {
        Integer i = (Integer)this.fVal;
        return (i).compareTo((Integer)o.fVal);
    }

    // Get a list of valid neighbors
    public List<Node> validNeighbors(Predicate<Point> canPassThrough){
        List<Node> ret = new ArrayList<>();
        Node left = new Node(new Point(p.x-1, p.y), this, target);
        Node right = new Node(new Point(p.x+1, p.y), this, target);
        Node up = new Node(new Point(p.x, p.y-1), this, target);
        Node down = new Node(new Point(p.x, p.y+1), this, target);
        if (canPassThrough.test(left.getPoint())){ ret.add(left); }
        if (canPassThrough.test(right.getPoint())){ ret.add(right); }
        if (canPassThrough.test(up.getPoint())){ ret.add(up); }
        if (canPassThrough.test(down.getPoint())){ ret.add(down); }
        return ret;
    }
}
