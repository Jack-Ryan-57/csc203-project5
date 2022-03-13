import processing.core.PImage;
import java.util.List;
/**
 * An this that exists in the world. See thisKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity
{



    private Point position;
    private List<PImage> images;

    public Entity(Point p, List<PImage> images){
        this.position = p;
        this.images = images;
    }



    protected Point getPosition(){ return this.position; }
    protected void setPosition(Point p){ this.position = p; }
    protected List<PImage> getImages(){ return this.images; }
    protected PImage getCurrentImage(){ return images.get(0); }



}
