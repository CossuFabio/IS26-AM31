package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class HuntEventCard extends EventCard {
    private int foodBonus;

    //for JSON use
    public HuntEventCard() {}

    public HuntEventCard(int era, int foodBonus, int prestigePointsBonus){
        super(era);
        this.foodBonus = foodBonus;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = 0;
        this.priority = 1+era;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveHunt(foodBonus, prestigePointsBonus);});
    }



}
