import java.util.List;

import processing.core.PImage;

public class Explosion extends CustomAnimation{

    private boolean replace;
    private ImageStore imageStore;
    private WorldModel world;
    private String replaceType = "grave";
    private Point p;

    public boolean done = false;

    public Explosion(Point p, List<PImage> images, int imageIndex, int animationPeriod, WorldModel world,
            EventScheduler scheduler, boolean replace, MovingEntity previous, ImageStore imageStore) {
        super(p, images, imageIndex, animationPeriod, world, scheduler);
        this.p = p;
        this.world = world;
        this.replace = replace;
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
            if (replace){
                if (replaceType == "grave"){
                    Gravestone g = Factory.createGravestone(p, imageStore, world);
                    getWorld().addEntity(g);
                }else if (replaceType == "dude"){
                    DudeNotFull newDude = Factory.createDudeNotFull("newdude", p, 3, Functions.DUDE_ANIMATION_PERIOD, Functions.DUDE_LIMIT, imageStore.getImageList(Functions.DUDE_KEY));
                    newDude.scheduleActions(getScheduler(), world, imageStore);
                }
                this.replace = false;
            }
        }
    }

    public void setReplaceType(String s){
        this.replaceType = s;
    }
    
    public void setReplace(boolean b){
        this.replace = b;
    }
}
