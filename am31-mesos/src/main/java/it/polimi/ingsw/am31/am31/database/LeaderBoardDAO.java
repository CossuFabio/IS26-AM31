package it.polimi.ingsw.am31.am31.database;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;

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
            System.err.println("LeaderBoardDAO: failed to insert scores — " + e.getMessage());
            try{
                connection.rollback();
                connection.setAutoCommit(true);
            }catch(Exception ignored){}
        }

    }

    public List<GlobalRankingEntry> getLeaderBoardPosition(List<Player> players) throws SQLException {

        if(players.isEmpty()) return new ArrayList<>();

        StringBuilder questionMarks = new StringBuilder();
        for(Player player : players){
            questionMarks.append("?,");
        }
        questionMarks.deleteCharAt(questionMarks.length()-1); // Remove last comma

        List<GlobalRankingEntry> leaderBoard = new ArrayList<>();
        String getLeaderBoardQueryString = """
                
                SELECT player_username, total_prestige_points, total_food, games_played, player_rank
                FROM leaderboard 
                WHERE num_players = ? 
                AND player_username IN (%s)
                                
                """.formatted(questionMarks);

        try(PreparedStatement statement = connection.prepareStatement(getLeaderBoardQueryString)){

            statement.setInt(1, players.size());
            for(int i = 0; i < players.size(); i++){
                statement.setString(2 + i, players.get(i).getNickname());
            }

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                leaderBoard.add(new GlobalRankingEntry(
                        rs.getString("player_username"),
                        rs.getInt("total_prestige_points"),
                        rs.getInt("total_food"),
                        rs.getInt("games_played"),
                        rs.getInt("player_rank")
                ));

            }

        }

        return leaderBoard;


    }


    //this board show all the players on the db, not just those currently in the game
    public List<GlobalRankingEntry> getFullLeaderBoard(int numPlayers) throws SQLException {

        List<GlobalRankingEntry> leaderBoard = new ArrayList<>();
        String query = """
            SELECT player_username, total_prestige_points, total_food, games_played, player_rank
            FROM leaderboard
            WHERE num_players = ?
            ORDER BY player_rank ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numPlayers);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                leaderBoard.add(new GlobalRankingEntry(
                        rs.getString("player_username"),
                        rs.getInt("total_prestige_points"),
                        rs.getInt("total_food"),
                        rs.getInt("games_played"),
                        rs.getInt("player_rank")
                ));
            }
        }
        return leaderBoard;
    }
}
