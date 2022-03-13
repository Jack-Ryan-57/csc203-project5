import java.util.*;

import processing.core.PImage;

public class DudeNotFull extends MovingEntity{
    
    private String id;
    private int resourceLimit;
    private int resourceCount;


    public DudeNotFull(
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
    this.resourceCount = resourceCount;

}

    public boolean transformNotFull(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit) {
            MovingEntity miner = Factory.createDudeFull(this.id,
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.resourceLimit,
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    public boolean moveTo(
        WorldModel world,
        Entity target,
        EventScheduler scheduler)
    {
        if (target instanceof Plant && this.getPosition().adjacent(target.getPosition())) {
            this.resourceCount += 1;
            ((Plant)target).setHealth(((Plant)target).getHealth() - 1);
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target =
        world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo(world,
                (Plant)target.get(),
                scheduler)
                || !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
        
    }

}
