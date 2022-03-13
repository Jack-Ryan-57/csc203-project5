import java.util.*;

import processing.core.PImage;


public class DudeFull extends MovingEntity{

    private String id;
    private int resourceLimit;
    
    public DudeFull(            
        String id,
        Point position,
        List<PImage> images,
        int resourceLimit,
        int resourceCount,
        int actionPeriod,
        int animationPeriod,
        int imageIndex,
        int health,
        int healthLimit)
{
    super(position, images, imageIndex, animationPeriod, actionPeriod);
    this.id = id;
    this.resourceLimit = resourceLimit;


    }


    @Override
    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));
        //world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && this.moveTo(world,
                fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    @Override
    public boolean moveTo(
        WorldModel world,
        Entity target,
        EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }


    
    private void transformFull(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
        {
            AnimatedEntity miner = Factory.createDudeNotFull(this.id,
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.resourceLimit,
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);
        }
        
}
