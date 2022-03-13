import java.util.*;
import processing.core.PImage;


public class Fairy extends MovingEntity{
    
    private String id;

    public Fairy(
         String id,
         Point position,
         List<PImage> images,
         int imageIndex,
         int actionPeriod,
         int animationPeriod
    ){
        super(position, images, imageIndex, animationPeriod, actionPeriod);
    }


    @Override
    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Stump.class, Gravestone.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {
                AnimatedEntity sapling = Factory.createSapling("sapling_" + id, tgtPos,
                imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    @Override
    public boolean moveTo(
        WorldModel world,
        Entity target,
        EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }


}
