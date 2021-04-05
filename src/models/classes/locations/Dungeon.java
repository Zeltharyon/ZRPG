package models.classes.locations;
import models.classes.entities.Dice;
import models.classes.entities.Entity;

public class Dungeon {

    private int nbRooms;
    private int treasure;

    public int getTreasure() {
        return treasure;
    }
    public int getNbRooms() {
        return nbRooms;
    }

    public Dungeon(){
        nbRooms = new Dice(5).roll();
        treasure = new Dice(100).roll();
    }

    public static class Room {

        private int nbMonsters;

        public Room(){
            nbMonsters = new Dice(4).roll();
        }
        public int getNbMonsters() {
            return nbMonsters;
        }
    }

}
