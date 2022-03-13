import java.util.List;

import processing.core.PImage;

public class Explosion extends CustomAnimation{

    private boolean interrupt;
    public Explosion(Point p, List<PImage> images, int imageIndex, int animationPeriod, WorldModel world,
            EventScheduler scheduler, boolean interrupt) {
        super(p, images, imageIndex, animationPeriod, world, scheduler);
        this.interrupt = interrupt;
    }

    @Override
    public void nextImage(){
        if (!interrupt && this.getImageIndex() < this.getImages().size() - 1){
            this.setImageIndex(this.getImageIndex() + 1);
        }else{
            //Replace dead dude with a gravestone
            Factory.endAnimation(getWorld(), this, getScheduler());
        }
    }
    
    public void setInterrupt(boolean b){
        this.interrupt = b;
    }
}
