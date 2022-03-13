import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import processing.core.PImage;

public abstract class MovingEntity extends ActiveEntity{

    public MovingEntity(Point p, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod) {
        super(p, images, imageIndex, animationPeriod, actionPeriod);
    }

    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        Point nextPos = this.nextPosition(world, target.getPosition());

        if (!this.getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }
        return false;
    }
    
    public Point nextPosition(WorldModel world, Point destPos){
        //int horiz = Integer.signum(destPos.x - this.getPosition().x);
        //int vert = Integer.signum(destPos.y - this.getPosition().y);
        //Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y + vert);
        Predicate<Point> canPassThrough = (b) -> (world.withinBounds(b) && !b.equals(this.getPosition()) && !world.isOccupied(b));
        boolean checkDude = ((this.getClass() == DudeFull.class || this.getClass() == DudeNotFull.class));
        BiPredicate<Point, Point> withinReach = (a, b) -> (checkDude ? (a.adjacent(b) && (!(world.getOccupancyCell(b) instanceof Stump))): a.adjacent(b));
        List<Point> compute = new AStarPathingStrategy().computePath(this.getPosition(),
            destPos,
            canPassThrough, 
            withinReach, 
            PathingStrategy.CARDINAL_NEIGHBORS);
        return compute.size() >= 1  ? compute.get(compute.size()-1) : this.getPosition();
        //boolean bool = true;
        /*if (this.getClass() == DudeFull.class || this.getClass() == DudeNotFull.class){
            bool = (!(world.getOccupancyCell(newPos) instanceof Stump));
        }
        if (horiz == 0 || world.isOccupied(newPos) && bool) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos) && bool) {
                newPos = this.getPosition();
            }
        }

        return newPos;*/
    }

}
