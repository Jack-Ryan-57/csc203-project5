import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {

        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashMap<Point, Node> openHash = new HashMap<>();
        //Use a hash set so we can make sure we're not visiting nodes that have been visited
        HashSet<Node> closedList = new HashSet<>();
        Node target = new Node(end, null, null);
        Node currentNode = new Node(start, null, target);
        // Push first node to open list
        openList.add(currentNode);
        openHash.put(currentNode.getPoint(), currentNode);
        while (currentNode != null && !withinReach.test(currentNode.getPoint(), end)){
            // Get valid neighbors that are not in closed list
            List<Point> valids = potentialNeighbors.apply(currentNode.getPoint())
                .filter(canPassThrough)
                .collect(Collectors.toList());
            // Convert points to nodes
            List<Node> validNeighbors = new ArrayList<>();
            for (Point p: valids){
                validNeighbors.add(new Node(p, currentNode, target));
            }
            for (Node n: validNeighbors){
                boolean skip = false;
                // Check to make sure neighboring node not in closed list
                if (!closedList.contains(n)){
                    //Calculate g value 
                    n.calculateGVal();
                    if (openHash.containsKey(n.getPoint())){
                        // Get previously calculated gval of node already in list
                        int prevG = openHash.get(n.getPoint()).getGVal();
                        // If our new one is better: 
                        if (n.getGVal() < prevG){
                            // Set the old one to this one
                            openHash.get(n.getPoint()).setGVal(n.getGVal());
                        }else{
                            // Else skip to step a for next node
                            skip = true;
                        }
            
                    }
                    n.calculateHVal();
                    n.calculateFVal();
                    // With g, h, and f values calculated (in node constructor), add node to open list
                    if (!skip){
                        openList.add(n);
                        openHash.put(n.getPoint(), n);
                    }
                }
            }
            //Move current node to closed list
            closedList.add(currentNode);
            openList.remove(currentNode);
            openHash.remove(currentNode.getPoint());
            // Get node from open list with smallest f value and make it the current node
            currentNode = openList.peek();
        }
        
        List<Point> path = new LinkedList<Point>();
        if (currentNode == null){
            return path;
        }
        while (currentNode.prior() != null){
            path.add(currentNode.getPoint());
            currentNode = currentNode.prior();
        }

        return path;
    }
}