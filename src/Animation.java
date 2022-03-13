public class Animation extends Action{

    private AnimatedEntity entity;
    private int repeatCount;

    public Animation(
        AnimatedEntity entity, 
        int repeatCount
    ){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    @Override
    public void executeAction(EventScheduler scheduler) {
        
        entity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity,
                Factory.createAnimationAction(entity,
                                        Math.max(repeatCount - 1,
                                                0)),
                entity.getAnimationPeriod());
        }
    }

}