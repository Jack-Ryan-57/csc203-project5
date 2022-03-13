import java.util.*;
import processing.core.PImage;




public class Sapling extends Plant{
    
    private String id;


    private static final int TREE_ANIMATION_MAX = 600;
    private static final int TREE_ANIMATION_MIN = 50;
    private static final int TREE_ACTION_MAX = 1400;
    private static final int TREE_ACTION_MIN = 1000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    public Sapling(String id, 
        Point position,
        List<PImage> images,
        int imageIndex,
        int actionPeriod,
        int animationPeriod,
        int health,
        int healthLimit
        ){
            super(id, position, images, imageIndex, animationPeriod, actionPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {
        this.setHealth(this.getHealth() + 1);
        if (!this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    protected boolean transform(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
    {
        if (super.transform(world, scheduler, imageStore)){
            return true;
        }
        else if (this.getHealth() >= this.getHealthLimit())
        {
            Tree tree = Factory.createTree("tree_" + this.id,
                    this.getPosition(),
                    Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}
