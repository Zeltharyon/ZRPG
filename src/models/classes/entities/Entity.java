package models.classes.entities;

import com.google.gson.Gson;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static models.classes.playerdata.Player_Profile.Save;

public class Entity {

    private String name;
    private int id;
    private String classe;
    private String filepicName;
    private int pv;
    private int dmg;
    private int dmgchance;
    private int def;
    private String defense_type;
    private double defchance;
    private double deftime;
    private int gold;
    private int maxPv;


    public Entity(String nom, boolean joueur, int class_id, boolean amical) throws IOException {

        if (joueur){
            if (class_id == 1) {
                this.name = nom;
                this.classe = "Guerrier";
                this.filepicName = "warrior";
                this.pv = 50;
                this.dmg = 5;
                this.dmgchance = 4; // stat sur 1D12
                this.def = 4;
                this.defense_type = "shield";
                this.deftime = 0.9; // stat sur 1D12
                this.gold = 0;
                this.maxPv = pv;
            }
            else if (class_id == 2) {
                this.name = nom;
                this.classe = "Mage";
                this.filepicName = "wizard";
                this.pv = 30;
                this.dmg = 10;
                this.dmgchance = 10; // stat sur 1D12
                this.def = 3;
                this.defense_type= "magic_shield";
                this.deftime = 0.7; // stat sur 1D12
                this.gold = 0;
                this.maxPv = pv;
            }
            else if (class_id == 3) {
                this.name = nom;
                this.classe = "Voleur";
                this.filepicName = "thief";
                this.pv = 40;
                this.dmg = 7;
                this.dmgchance = 7; // stat sur 1D12
                this.def = 2;
                this.defense_type = "dodge";
                this.deftime = 0.450; // stat sur 1D12
                this.gold = 50;
                this.maxPv = pv;
            }
        }

        else {

            if (amical){
                this.name = nom;
                this.classe = "Citoyen";
                this.pv = 10;
                this.dmg = 1;
                this.def = 1;
                this.gold = 0;
                this.maxPv = pv;
            }

            else {

                int roll = new Dice(3).roll();

                if(roll == 1){
                    this.name = "zombie";
                    this.pv = 15;
                    this.dmg = 4;
                    this.def = 2;
                    this.dmgchance = 3;
                    this.defchance = 5;
                    this.maxPv = pv;
                }
                else if(roll==2){
                    this.name = "skeleton";
                    this.pv = 10;
                    this.dmg = 5;
                    this.def = 1;
                    this.dmgchance = 5;
                    this.defchance = 3;
                    this.maxPv = pv;
                }
                else if(roll==3){
                    this.name = "spider";
                    this.pv = 20;
                    this.dmg = 3;
                    this.def = 2;
                    this.dmgchance = 4;
                    this.defchance = 6;
                    this.maxPv = pv;
                }
            }
        }
    }

    public String attack(Entity attaquant, Entity cible){
        int diceroll = new Dice(12).roll();

        if (diceroll <= attaquant.dmgchance){
            if(cible.mstrdefend(cible)){
                if(attaquant.dmg - cible.def > 0){
                    cible.pv = cible.pv - (attaquant.dmg - cible.def);
                    return diceroll + "/" + attaquant.dmgchance + " -> Succès ! Mais la cible se défend et réduit ses dégâts de " + cible.def;
                }
            }
            else{
                cible.pv = cible.pv - attaquant.dmg;
                return diceroll + "/" + attaquant.dmgchance + " -> Succès !";
            }
        }
        else {
            return diceroll + "/" + attaquant.dmgchance + " -> Échec !";
        }
        return "";
    }

    public boolean mstrdefend(Entity defenseur){
        int diceroll = new Dice(12).roll();

        if (diceroll <= defenseur.defchance){
            return true;
        }
        else {
            return false;
        }
    }
    public void heal(Entity joueur, int healAmount){
        for (int i = 0; i < healAmount; i++) {
            if(joueur.pv < joueur.maxPv){
                joueur.pv += 1;
            }
        }
    }

    public int getPv() {
        return pv;
    }
    public void setPv(int pv) {
        this.pv = pv;
    }
    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public int getDef() {
        return def;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public double getDefchance() {
        return defchance;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public String getClasse() {
        return classe;
    }
    public void setClasse(String classe) {
        this.classe = classe;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaxPv(){ return maxPv;}
    public void setMaxPv(int MaxPv) {
        this.maxPv = MaxPv;
    }
    public String getFilepicName() {
        return filepicName;
    }
    public String getDefense_type() {
        return defense_type;
    }
}
