package it.polimi.ingsw.am31.am31.testUtils.player;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class PlayerFluentBuilder implements FluentBuilder<Player> {

    private String name;
    private Color color;


    public PlayerFluentBuilder(){
        reset();
    }


    public PlayerFluentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerFluentBuilder color(Color color){
        this.color = color;
        return this;
    }

    @Override
    public void reset(){
        this.name = "Dummy";
        this.color = Color.RED;
    }
    @Override
    public Player build(){
        Player toReturn = new Player(name, color);
        reset();
        return toReturn;
    }
}