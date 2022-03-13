
import processing.core.PImage;
import java.util.List;

public abstract class ActiveEntity extends AnimatedEntity{

    private int actionPeriod;

    public ActiveEntity(Point p, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod){
        super(p, images, imageIndex, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
            Factory.createActivityAction(this, world, imageStore),
            this.getActionPeriod());
        super.scheduleActions(scheduler, world, imageStore);
    }
    
    
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected int getActionPeriod(){ return this.actionPeriod; }
}