package models.classes.entities;

public class Dice {

    private int faces;

    public Dice(int faces){
        this.faces = faces;
    }

    public int roll(){
        return ( 1 + (int) (Math.random()* ((faces - 1) +1)) );
    }

}
