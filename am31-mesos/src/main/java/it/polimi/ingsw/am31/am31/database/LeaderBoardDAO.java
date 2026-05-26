package it.polimi.ingsw.am31.am31.database;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
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

            if (!keysSet.next()) throw new SQLException("No generated key returned");
            return keysSet.getInt(1);
        }



    }

    private void insertScores(int gameId, List<Player> players) throws SQLException {

        String insertScoresQueryString = """
                
                INSERT INTO participation (game_id, player_username, prestige_points, food)
                
                VALUES (?, ?, ?, ?)
                
                """;

        try(PreparedStatement statement = connection.prepareStatement(insertScoresQueryString)){

            for(Player player : players){
                statement.setInt(1, gameId);
                statement.setString(2, player.getNickname());
                statement.setInt(3, player.getPrestigePoints());
                statement.setInt(4, player.getFood());

                statement.addBatch();
            }

            statement.executeBatch();

        }


    }

    public void insertScores(List<Player> players){

        try{

            connection.setAutoCommit(false);

            int gameKey = insertGame(players.size());
            insertScores(gameKey, players);

            connection.commit();
            connection.setAutoCommit(true);


        }catch(Exception e){
            try{
                connection.rollback();
            }catch(Exception ignored){}
        }

    }

    /**
     *
     * @param players
     * @return
     * @throws SQLException
     */
    public List<LeaderBoardBean> getLeaderBoardPosition(List<Player> players) throws SQLException {

        if(players.isEmpty()) return new ArrayList<>();

        StringBuilder questionMarks = new StringBuilder();
        for(Player player : players){
            questionMarks.append("?,");
        }
        questionMarks.deleteCharAt(questionMarks.length()-1); // Remove last comma

        List<LeaderBoardBean> leaderBoard = new ArrayList<>();
        String getLeaderBoardQueryString = """
                
                SELECT player_username, total_prestige_points, total_food, games_played, player_rank
                FROM leaderboard 
                WHERE num_players = ? 
                AND player_username IN (?)
                                
                """;

        try(PreparedStatement statement = connection.prepareStatement(getLeaderBoardQueryString)){

            statement.setInt(1, players.size());
            statement.setString(2, questionMarks.toString());

            ResultSet rs = statement.executeQuery();
            while(rs.next()){


            }

        }

        return leaderBoard;


    }

    }
