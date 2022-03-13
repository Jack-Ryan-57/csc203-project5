import processing.core.PImage;
import java.util.List;

public abstract class AnimatedEntity extends Entity{

    private int animationPeriod;
    private int imageIndex;
    

    public AnimatedEntity(Point p, List<PImage> images, int imageIndex, int animationPeriod){
        super(p, images);
        this.imageIndex = imageIndex;
        this.animationPeriod = animationPeriod;
    }
    
    protected int getAnimationPeriod(){ return this.animationPeriod; }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
            Factory.createAnimationAction(this, 0),
            this.getAnimationPeriod());
    }

    @Override
    protected PImage getCurrentImage(){
        return this.getImages().get(imageIndex);
    }

    protected int getImageIndex(){
        return this.imageIndex;
    }

    protected void setImageIndex(int index){
        this.imageIndex = index;
    }

    public void nextImage(){
        this.imageIndex = (this.imageIndex + 1) % this.getImages().size();
    }
}
