import processing.core.PImage;
import java.util.List;

public class Obstacle extends AnimatedEntity {


    public Obstacle(int animationPeriod, Point position, int imageIndex, List<PImage> images){
        super(position, images, imageIndex, animationPeriod);
    }

}
