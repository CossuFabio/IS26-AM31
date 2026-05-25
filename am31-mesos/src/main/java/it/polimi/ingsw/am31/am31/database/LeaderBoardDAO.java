package it.polimi.ingsw.am31.am31.database;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.sql.*;
import java.util.List;

public class LeaderBoardDAO {

    private final Connection connection;

    public LeaderBoardDAO(Connection connection){
        this.connection = connection;
    }


    private int insertGame(int numPlayers) throws SQLException {

        String insertGameQueryString = """
                
                INSERT INTO games (game_date, num_players)
                VALUES (CURDATE(), ?) 
                
                
                """;

        try(PreparedStatement statement = connection.prepareStatement(insertGameQueryString, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, numPlayers);

            int affectedRows = statement.executeUpdate();

            //If insert fail we throw exception then rollback
            if(affectedRows != 1) throw new SQLException();

            ResultSet keysSet = statement.getGeneratedKeys();
            keysSet.next();

            return keysSet.getInt(1);

        }



    }



    public void insertScores(List<Player> players){

        try{

            connection.setAutoCommit(false);

            Integer gameKey = insertGame(players.size());


            connection.commit();
            connection.setAutoCommit(true);
        }catch(Exception e){
            try{
                connection.rollback();
            }catch(Exception ignored){}
        }

    }


}
