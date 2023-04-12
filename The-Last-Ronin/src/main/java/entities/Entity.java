package entities;

//This is abstract Entity class.
//Describes all entities in the game.
public abstract class Entity {

    protected float x, y;
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
