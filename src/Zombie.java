import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Zombie extends MovingEntity{

    public Zombie(
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
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> zombieTarget =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(DudeNotFull.class, DudeFull.class)));
        if (zombieTarget.isPresent())
        {
            Point tgtPos = zombieTarget.get().getPosition();
            if (this.moveTo(world, zombieTarget.get(), scheduler)) {
                Entity gravestone = Factory.createGravestone(tgtPos, imageStore, world);
                world.removeEntity(zombieTarget.get());
                scheduler.unscheduleAllEvents(zombieTarget.get());
                world.addEntity(gravestone);
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
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
