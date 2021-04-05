package models.classes.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.classes.entities.Entity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Player_Profile {

    protected static Entity player;

    public static void setPlayer(Entity player) {
        Player_Profile.player = player;
        System.out.println("Profil de " + player.getName() + " mis à jour !");
    }

    public static void Save() throws IOException {
        Gson saveGson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter saveFile = new FileWriter("./src/models/data/save_"+player.getName()+".json");
        saveFile.write(saveGson.toJson(player));
        saveFile.flush();
        saveFile.close();
    }
    public static void Load(String name) throws IOException {
        Gson loadGson = new Gson();
        FileReader loadFile = new FileReader("./src/models/data/save_"+name+".json");
        setPlayer(loadGson.fromJson(loadFile, Entity.class));
    }
    public static void DeleteSave(String name) throws IOException {
        new File("./src/models/data/save_"+name+".json").delete();
    }

    public static Entity getPlayer() {
        return player;
    }

}
