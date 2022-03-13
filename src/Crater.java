import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PImage;

public class Crater extends Background{
    
    private Point p;
    private WorldModel world;
    private List<Point> affectedPoints;

    public Crater(String id, List<PImage> images, Point p, WorldModel world, EventScheduler scheduler) {
        super(id, images);
        this.p = p;
        this.world = world;
        this.affectedPoints = findAffectedPoints();
    }

    /*private List<PImage> getProperImage(int index){
        if (index < 5){
            return Arrays.asList(this.getImages().get(0));
        }else if (index == 5){
            return Arrays.asList(this.getImages().get(1));
        }else if (index == 6){
            return Arrays.asList(this.getImages().get(2));
        }else if (index == 7){
            return Arrays.asList(this.getImages().get(3));
        }else{
            return Arrays.asList(this.getImages().get(4));
        }
    }*/

    //Return a list of all of the tiles that are a part of the explosion
    private List<Point> findAffectedPoints(){
        Point left = new Point(p.x - 1, p.y), right = new Point(p.x + 1, p.y);
        Point up = new Point(p.x, p.y - 1), down = new Point(p.x, p.y + 1);
        Point nw = new Point(up.x - 1, up.y), ne = new Point(up.x + 1, up.y);
        Point sw = new Point(down.x - 1, down.y), se = new Point(down.x + 1, down.y);
        List<Point> surroundingTiles = Arrays.asList(p, up, down, left, right, sw, se, nw, ne);
        List<Point> ret = new ArrayList<>();
        for (int i = 0; i < surroundingTiles.size(); i++){
            Point t = surroundingTiles.get(i);
            if (world.withinBounds(t)){
                ret.add(t);
            }
        }
        return ret;
    }

    public List<Point> getAffectedPoints(){
        return this.affectedPoints;
    }

    public void displayImages(){
        List<Point> points = affectedPoints;
        for (int i = 0; i < points.size(); i++){
            Point t = points.get(i);
            world.setBackground(t, new Background("crater", this.getImages()));
        }
    }
}
