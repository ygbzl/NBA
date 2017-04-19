package javabean;

import java.sql.ResultSet;

/**
 * Created by zhupd on 4/18/2017.
 */
public class Query extends Connect{


    /**
     *
     * @return string[] as playerName, score, team
     * @throws Exception
     */
    public String[] topPlayerPoints() throws Exception {
        this.startConnect();
        ResultSet rs = stmt.executeQuery("select PLAYER_NAME, P/2, TEAM_ABBREVIATION from (\n" +
                "select sum(MATCH_PLAYER_STATS.PTS) as p, player_id , player_name, TEAM_ABBREVIATION from MATCH_STATS, MATCH_PLAYER_STATS where \n" +
                "MATCH_PLAYER_STATS.GAME_ID = MATCH_STATS.GAME_ID_A and\n" +
                "GAME_DATE_EST = '2006-04-18T00:00:00'\n" +
                "group by  player_id, player_name, TEAM_ABBREVIATION\n" +
                "order by sum(MATCH_PLAYER_STATS.PTS) desc)\n" +
                "where p is not null and rownum <= 5");
        String[] res = new String[3];
        rs.next();
        res[0] = rs.getString(1);
        res[1] = rs.getInt(2) + "";
        res[2] = rs.getString(3);

        return res;
    }

    public String[] topTeamPoints() throws Exception {
        this.startConnect();
        ResultSet rs = stmt.executeQuery("select * from\n" +
                "(select CITY, NICKNAME, PTS from MATCH_STATS, TEAM_COMMON_INFO\n" +
                "where GAME_DATE_EST = '2006-04-18T00:00:00'\n" +
                "and TEAM_COMMON_INFO.TEAM_ID = MATCH_STATS.TEAM_ID_A\n" +
                "order by PTS desc)\n" +
                "where rownum <= 4");
        String[] res = new String[3];
        rs.next();
        res[0] = rs.getString(1);
        res[1] = rs.getString(2);
        res[2] = rs.getInt(3) + "";
        return res;
    }



}
