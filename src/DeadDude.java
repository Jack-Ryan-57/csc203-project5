import java.util.List;

import processing.core.PImage;

public class DeadDude extends CustomAnimation{


    private ImageStore imageStore;
    private WorldModel world;

    public DeadDude(Point p, List<PImage> images, int imageIndex, int animationPeriod, WorldModel world,
            EventScheduler scheduler, ImageStore imageStore) {
        super(p, images, imageIndex, animationPeriod, world, scheduler);
        this.imageStore = imageStore;
        this.world = world;
    }

    @Override
    public void nextImage(){
        if (this.getImageIndex() < this.getImages().size() - 1){
            this.setImageIndex(this.getImageIndex() + 1);
        }else{
            //Replace dead dude with a gravestone
            Factory.endAnimation(getWorld(), this, getScheduler());
            Gravestone g = Factory.createGravestone(getPosition(), imageStore, getWorld());
            world.addEntity(g);
        }
    }
    
}
