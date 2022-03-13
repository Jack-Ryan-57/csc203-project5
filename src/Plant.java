import java.util.List;

import processing.core.PImage;

public abstract class Plant extends ActiveEntity{

    private String id;

    private int health;
    private int healthLimit;

    public Plant(String id, Point p, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int health, int healthLimit) {
        super(p, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
        this.id = id;
    }

    

    protected boolean transform(WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore){
        if (this.getHealth() <= 0) {
            Stump stump = Factory.createStump(this.id,
                    this.getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);
            //stump.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
    protected int getHealth(){ return this.health; }
    protected int getHealthLimit(){ return this.healthLimit; }
    protected void setHealth(int next){ this.health = next; }
    
}