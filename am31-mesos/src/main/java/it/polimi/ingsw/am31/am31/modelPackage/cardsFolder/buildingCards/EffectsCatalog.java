package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.FoodEndTurnDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.BonusHunterHandleDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.GeneralAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.InventorAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.BonusPaintHandlerDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.NoMalusRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.DoubleWinRitualStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ArtistDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.FarmerDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.InventorDiscountBonus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

//Used to load resources from JSON
public class EffectsCatalog {

    private final Map<String, Consumer<Player>> effects;

    public EffectsCatalog(){
        effects = new HashMap<String, Consumer<Player>>();
        //Era 1 effects
        effects.put(EffectIdsConstants.GENERAL_ADDITIONAL_FOOD, player -> player.addDrawEffect(GeneralAdditionalFoodDecorator::new));

        effects.put(EffectIdsConstants.FARMER_DISCOUNT_BONUS, player -> player.addSustainBonus(FarmerDiscountBonus::new));

        effects.put(EffectIdsConstants.ARTIST_DISCOUNT_BONUS, player -> player.addSustainBonus(ArtistDiscountBonus::new));

        effects.put(EffectIdsConstants.NO_MALUS_RITUAL_LOSE, player -> player.addRitualLoseEffect(NoMalusRitualLoseStrategy::new));

        effects.put(EffectIdsConstants.FOOD_END_TURN, player -> player.addEndTurnEffect(FoodEndTurnDecorator::new));

        effects.put(EffectIdsConstants.INVENTOR_ADDITIONAL_FOOD, player -> player.addDrawEffect(InventorAdditionalFoodDecorator::new));


        //Era 2 effects
        effects.put(EffectIdsConstants.DOUBLE_WIN_RITUAL, player -> player.addRitualWinEffect(DoubleWinRitualStrategy::new));

        effects.put(EffectIdsConstants.INCREASE_STARS_3, player -> player.increaseStars(3)); // ⚠️ non decorator

        effects.put(EffectIdsConstants.INVENTOR_DISCOUNT_BONUS, player -> player.addSustainBonus(InventorDiscountBonus::new));

        effects.put(EffectIdsConstants.BONUS_HUNTER, player -> player.addHuntEffect(BonusHunterHandleDecorator::new));

        effects.put(EffectIdsConstants.DOUBLE_BUILDER_ENDGAME, player -> player.addEndGameEffect(DoubleBuilderEndGameDecorator::new));

        effects.put(EffectIdsConstants.BONUS_PAINT, player -> player.addPaintEffect(BonusPaintHandlerDecorator::new));

        effects.put(EffectIdsConstants.CARD_SET_BONUS, player -> player.addEndGameEffect(CardSetDecorator::new));


        // ===== ERA 3 =====
        effects.put(EffectIdsConstants.HUNTER_PRESTIGE_BONUS,
                player -> player.addEndGameEffect(HunterPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.FARMER_PRESTIGE_BONUS, player -> player.addEndGameEffect(FarmerPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.SHAMAN_PRESTIGE_BONUS,
                player -> player.addEndGameEffect(ShamanPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.BUILDER_PRESTIGE_BONUS,
                player -> player.addEndGameEffect(BuilderPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.ARTIST_PRESTIGE_BONUS,
                player -> player.addEndGameEffect(ArtistPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.INVENTOR_PRESTIGE_BONUS, player -> player.addEndGameEffect(InventorPrestigeBonusDecorator::new));

        effects.put(EffectIdsConstants.ONE_MORE_CARD, player -> player.addBonusDrawFromUpper(1));

        effects.put(EffectIdsConstants.FLAT_PRESTIGE_POINTS, player -> player.addEndGameEffect(FlatPrestigePointsDecorator::new));
    }

    public Consumer<Player> getEffect(String id) {
        //Requested effect
        Consumer<Player> effect = effects.get(id);

        //Shouldn't happen but it's safer to check
        if (effect == null) {
            throw new IllegalArgumentException("Unknown effect: " + id);
        }

        return effect;
    }


}