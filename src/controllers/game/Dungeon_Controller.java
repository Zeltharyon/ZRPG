package controllers.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.classes.entities.Entity;
import models.classes.locations.Dungeon;
import models.classes.playerdata.Player_Profile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static controllers.functions.WinModif.*;

public class Dungeon_Controller implements Initializable {

    //Déclaration des attributs

    // <Button id="action" fx:id="defend" alignment="CENTER" contentDisplay="CENTER" layoutX="443.0" layoutY="140.0" maxHeight="99.0" maxWidth="99.0" minHeight="99.0" minWidth="99.0" mnemonicParsing="false" prefHeight="99.0" prefWidth="99.0" />
    // <Button id="action" fx:id="defend" alignment="CENTER" contentDisplay="CENTER" layoutX="180.0" layoutY="14.0" maxHeight="99.0" maxWidth="99.0" minHeight="99.0" minWidth="99.0" mnemonicParsing="false" prefHeight="99.0" prefWidth="99.0" />
    @FXML
    public Label playerName;
    public Pane actionBar;
    public ProgressBar healthBar;
    public ImageView dungeonImage;
    public Button close;
    public Label roomNbr;
    public Label dungeonEnd;
    public Button explore_btn;
    public Label pvAmount;
    public ImageView coin;
    public Label goldAmount;
    public Label round;
    public Label msgBox;
    public ImageView mob;
    public Button attack;
    public Button defend;
    public Button flee;
    public Button to_profile;
    private int cleanedRooms;
    private Entity currentMonster;
    public static boolean isBlocked;
    public static boolean isDodged;
    public static boolean isNegated;
    public Button back_to_city;
    public Label mnstrNbr;
    public ProgressBar lifeMob;

    //Méthode jouée à l'initialisation

    public void initialize(URL url, ResourceBundle resourceBundle) {
        cleanedRooms = 0;
        WinGameSetup(dungeonImage, "dungeon.png", playerName, pvAmount, healthBar, coin, goldAmount, to_profile);
        Dungeon dungeon = new Dungeon();
        Image img = new Image(new File("src/views/resources/pictures/actions/flee.png").toURI().toString());
        ImageView view = new ImageView(img);
        System.out.println(Player_Profile.getPlayer().getFilepicName());
        flee.setGraphic(view);

        img = new Image(new File("src/views/resources/pictures/actions/" + Player_Profile.getPlayer().getFilepicName() + "/attack.png").toURI().toString());
        view = new ImageView(img);
        attack.setGraphic(view);
        img = new Image(new File("src/views/resources/pictures/actions/" + Player_Profile.getPlayer().getFilepicName() + "/defend.png").toURI().toString());
        view = new ImageView(img);
        defend.setGraphic(view);

        attack.setVisible(false);
        defend.setVisible(false);
        flee.setVisible(false);
        lifeMob.setVisible(false);
        back_to_city.setVisible(false);
        msgBox.setVisible(false);

        lifeMob.setStyle("-fx-accent: darkgreen;");

        explore_btn.setLayoutX(212.0);
        explore_btn.setLayoutY(63.0);
        explore_btn.setPrefSize(287.0,128.0);
        explore_btn.setText("Entrer...");
        explore_btn.setOnMouseClicked(event -> {
            try {
                NewRoom(Player_Profile.getPlayer(), dungeon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            explore_btn.setLayoutX(549.0);
            explore_btn.setLayoutY(15.0);
            explore_btn.setPrefSize(157.0,57);
            explore_btn.setText("Salle suivante...");
        });
        back_to_city.setOnMouseClicked(Event -> {
            try {
                WinChange("Game/City");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        flee.setOnMouseClicked(Event -> {
            try {
                WinChange("Game/City");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //Fermeture de la fenêtre

    public void close() throws IOException {
        WinClose();

    }

    //Création d'une pièce

    public void NewRoom(Entity player, Dungeon dungeon) throws IOException {

        AtomicInteger rounds = new AtomicInteger();

        roomNbr.setText("Salle " + cleanedRooms + "/" + dungeon.getNbRooms());

        if (cleanedRooms < dungeon.getNbRooms()){
            Dungeon.Room currentRoom = new Dungeon.Room();
            AtomicInteger mnstrCount = new AtomicInteger();
            round.setText("Tour " + rounds);
            mnstrNbr.setText(mnstrCount + "/" + currentRoom.getNbMonsters() + " monstres tués");
            if(mnstrCount.get() < currentRoom.getNbMonsters()){
                currentMonster = genRandMonster();
                attack.setVisible(true);
                defend.setVisible(false);
                flee.setVisible(true);
                explore_btn.setVisible(false);

                attack.setOnMouseClicked(event -> {

                    if(player.getPv() > 0 && currentMonster.getPv() > 0){

                        rounds.addAndGet(1);
                        msgBox.setText(player.attack(player, currentMonster));
                        float mobpv = currentMonster.getPv();
                        float mobmaxpv = currentMonster.getMaxPv();
                        lifeMob.setProgress(mobpv/mobmaxpv);
                        round.setText("Tour "+ rounds);

                        if(currentMonster.getPv() <= 0){
                            mnstrCount.addAndGet(1);
                            mnstrNbr.setText(mnstrCount + "/" + currentRoom.getNbMonsters() + " monstres tués");

                            if(mnstrCount.get() < currentRoom.getNbMonsters()){
                                try {
                                    currentMonster = genRandMonster();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                round.setText("Salle vide");
                                explore_btn.setVisible(true);
                                attack.setVisible(false);
                                defend.setVisible(false);
                                flee.setVisible(false);
                            }
                        }
                        else {
                            attack.setVisible(false);
                            flee.setVisible(false);
                            new PerfectParry(1, attack, flee, defend, currentMonster, pvAmount, goldAmount, healthBar, lifeMob, msgBox);
                        }
                    }
                    if(player.getPv() <= 0){
                        player.setPv(1);
                        try {
                            WinChange("Game/City");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                defend.setOnMouseClicked(event ->{
                    if(player.getPv() > 0 && currentMonster.getPv() > 0 && player.getDefense_type().equals("shield")) {
                        isBlocked = true;
                    }
                    if(player.getPv() > 0 && currentMonster.getPv() > 0 && player.getDefense_type().equals("dodge")) {
                        isDodged = true;
                    }
                    if(player.getPv() > 0 && currentMonster.getPv() > 0 && player.getDefense_type().equals("magic_shield")) {
                        isNegated = true;
                    }

                });
            }
            cleanedRooms += 1;
        }
        else{
            dungeonEnd.setText("Donjon terminé !");
            player.setGold(player.getGold() + dungeon.getTreasure());
            ProfileUpdate(pvAmount, healthBar, goldAmount);
            attack.setVisible(false);
            defend.setVisible(false);
            flee.setVisible(false);
            explore_btn.setVisible(false);
            back_to_city.setLayoutX(212.0);
            back_to_city.setLayoutY(63.0);
            back_to_city.setPrefSize(287.0,128.0);
            back_to_city.setText("Rentrer en ville");
            back_to_city.setVisible(true);
        }
    }

    //Création d'un monstre

    public Entity genRandMonster() throws IOException {
        Entity monster = new Entity("",false,0,false);
        mob.setImage(new Image(new File("src/views/resources/pictures/monsters/"+monster.getName()+".gif").toURI().toString()));
        lifeMob.setVisible(true);
        lifeMob.setProgress(1.0);
        return monster;
    }

    //Classe de la parade timée

    public static class PerfectParry extends Dungeon_Controller {

        private int duration;
        private final Button defendBtn;
        private final Button attackBtn;
        private final Button fleeBtn;
        private final Entity currentMonster;
        private final Entity player;

        public PerfectParry(int seconds, Button attack, Button flee, Button defend, Entity monster, Label pvAmount, Label goldAmount, ProgressBar healthBar, ProgressBar mobBar, Label msgBox){

            this.duration = seconds;
            this.defendBtn = defend;
            this.attackBtn = attack;
            this.fleeBtn = flee;
            this.currentMonster = monster;
            this.player = Player_Profile.getPlayer();
            int time_to_parry = (int) (player.getDefchance() * 1000);
            double minX = 180.0;
            double maxX = 443.0;
            double minY = 14.0;
            double maxY = 140.0;
            double coordX = minX + Math.random()* (maxX - minX);
            double coordY = minY + Math.random()* (maxY - minY);

            coordX = Math.round(coordX);
            coordY = Math.round(coordY);
            defendBtn.setLayoutY(coordY);
            defendBtn.setLayoutX(coordX);
            defendBtn.setStyle("-fx-effect:dropshadow(three-pass-box, rgb(255,205,0), 30, 0, 0, 0);");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> Fight_Update(pvAmount, healthBar, goldAmount, mobBar, msgBox));
                    if(duration > 0){
                        defendBtn.setVisible(true);
                        msgBox.setVisible(true);
                        duration--;
                    }
                    else if(duration == 0){
                        if(isBlocked){
                            player.setPv(player.getPv() - (currentMonster.getDmg() - player.getDef()));
                        }
                        if(isDodged){
                            player.setPv(player.getPv());
                        }
                        if(isNegated){
                            player.setPv(player.getPv() - (currentMonster.getDmg() - player.getDef()));
                            if(currentMonster.getPv() > 2) {
                                currentMonster.setPv(currentMonster.getPv() - 2);
                                System.out.println(currentMonster.getPv());
                            }
                        }

                        else {
                            player.setPv(player.getPv() - currentMonster.getDmg());
                        }
                        isBlocked = false;
                        defendBtn.setVisible(false);
                        attackBtn.setVisible(true);
                        fleeBtn.setVisible(true);
                        cancel();
                        msgBox.setVisible(false);
                    }
                }
            }, 0, time_to_parry);
        }
        public void Fight_Update(Label pvAmount, ProgressBar healthBar, Label goldAmount, ProgressBar mobBar, Label msgBox){
            float pv = Player_Profile.getPlayer().getPv();
            float maxpv = Player_Profile.getPlayer().getMaxPv();
            pvAmount.setText(Player_Profile.getPlayer().getPv() + "/" + Player_Profile.getPlayer().getMaxPv());
            goldAmount.setText(""+Player_Profile.getPlayer().getGold());
            healthBar.setProgress(pv/maxpv);
            float mobpv = currentMonster.getPv();
            float mobmaxpv = currentMonster.getMaxPv();
            mobBar.setProgress(mobpv/mobmaxpv);
            isDodged = false;
            isBlocked = false;
            isNegated = false;
        }
    }
}
