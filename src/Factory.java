import java.util.List;

import processing.core.PImage;

public class Factory {

    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;


    public static Action createAnimationAction(AnimatedEntity entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(
            ActiveEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

    public static House createHouse(
            String id, Point position, List<PImage> images)
    {
        return new House(position, images);
    }

    public static Obstacle createObstacle(
            String id, Point position, int animationPeriod, List<PImage> images)
    {
        return new Obstacle(animationPeriod, position, 0, images);
    }

    public static Tree createTree(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new Tree(health, 0, 0, actionPeriod, animationPeriod, position, id, images);
    }


    public static Stump createStump(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Stump(position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Sapling createSapling(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Sapling(id, position, images, 0,
                SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public static Fairy createFairy(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Fairy(id, position, images, 0,
                actionPeriod, animationPeriod);
    }


    // need resource count, though it always starts at 0
    public static DudeNotFull createDudeNotFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new DudeNotFull(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0, 0);
    }

    // don't technically need resource count ... full
    public static DudeFull createDudeFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new DudeFull(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0, 0);
    }

    public static CustomAnimation createAnimation(
            Point p,
            List<PImage> images,
            int animationPeriod, 
            WorldModel world,
            EventScheduler scheduler     
    ){
            return new CustomAnimation(p, images, 0, animationPeriod, world, scheduler);
    }

    public static Gravestone createGravestone(Point p, ImageStore imageStore, WorldModel world){
        Gravestone g = new Gravestone(p, imageStore.getImageList(Functions.GRAVE_KEY));
        return g;
    }


    public static void endAnimation(WorldModel world, CustomAnimation ex, EventScheduler scheduler){
        scheduler.unscheduleAllEvents(ex);
        world.removeEntity(ex);
    }

    public static Crater createCrater(
            String id,
            Point p,
            List<PImage> images, 
            WorldModel world,
            EventScheduler scheduler

    ){
            return new Crater(id, images, p, world, scheduler);
    }
}
