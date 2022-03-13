import java.util.List;

import processing.core.PImage;

public class CustomAnimation extends AnimatedEntity{

    private WorldModel world;
    private EventScheduler scheduler;

    public CustomAnimation(Point p, List<PImage> images, int imageIndex, int animationPeriod,
            WorldModel world,
            EventScheduler scheduler) {
        super(p, images, imageIndex, animationPeriod);
        this.world = world;
        this.scheduler = scheduler;
    }

    @Override
    public void nextImage(){
        if (this.getImageIndex() < this.getImages().size() - 1){
            this.setImageIndex(this.getImageIndex() + 1);
        }else{
            Factory.endAnimation(world, this, scheduler);
        }
    }

    public WorldModel getWorld(){
        return this.world;
    }

    public EventScheduler getScheduler(){
        return this.scheduler;
    }
    
}
