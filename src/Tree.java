import java.util.List;

import processing.core.PImage;

public class Tree extends Plant{


    public Tree(
            int health,
            int healthLimit,
            int imageIndex,
            int actionPeriod,
            int animationPeriod,
            Point position,
            String id,
            List<PImage> images){

        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health, healthLimit);
    }
    

    @Override
    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {

        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean transform(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
    {
        return super.transform(world, scheduler, imageStore);
    }

}
