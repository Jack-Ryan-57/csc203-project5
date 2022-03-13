import java.util.List;

import processing.core.PImage;

public class Explosion extends CustomAnimation{

    private boolean addGrave;
    private ImageStore imageStore;
    private WorldModel world;
    private Point p;

    public boolean done = false;

    public Explosion(Point p, List<PImage> images, int imageIndex, int animationPeriod, WorldModel world,
            EventScheduler scheduler, boolean addGrave, MovingEntity previous, ImageStore imageStore) {
        super(p, images, imageIndex, animationPeriod, world, scheduler);
        this.p = p;
        this.world = world;
        this.addGrave = addGrave;
        this.imageStore = imageStore;
        if (previous != null){
            world.removeEntity(previous);
            scheduler.unscheduleAllEvents(previous);
        }
    }

    @Override
    public void nextImage(){
        if (this.getImageIndex() < this.getImages().size() - 1){
            this.setImageIndex(this.getImageIndex() + 1);
        }else{
            Factory.endAnimation(getWorld(), this, getScheduler());
            if (addGrave){
                System.out.println("WE GOT HERE");
                Gravestone g = Factory.createGravestone(p, imageStore, world);
                getWorld().addEntity(g);
                this.addGrave = false;
            }
        }
    }
    
    public void setAddGrave(boolean b){
        this.addGrave = b;
    }
}
