package ohtu;

public class Player implements Comparable<Player> {

    private String name;
    private String nationality;
    private String team;
    private Integer goals;
    private Integer assists;
    private Integer goalsPlusAssist;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public String getTeam() {
        return team;
    }

    public Integer getGoals() {
        return goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getGoalsPlusAssists() {
        goalsPlusAssist = goals + assists;
        return goalsPlusAssist;
    }

//    @Override
//    public String toString() {
//        return name + " Team: " + team + " Goals: " + goals + " Assists: " + assists + " (Goals and Assists total: " + this.getGoalsPlusAssists();
//    }
    
    @Override
    public String toString() {
        return String.format("%-20s %1s %5s + %5s = %5s", name, team, goals, assists, this.getGoalsPlusAssists());
    }
    
//    @Override
//    public int compareTo(Player o) {
//        if (this.getGoalsPlusAssists() > o.getGoalsPlusAssists()) {
//            return -1;
//        } else {
//            return 1;
//        }
//    }

    @Override
    public int compareTo(Player o) {
        return o.getGoalsPlusAssists() - this.getGoalsPlusAssists();
    }

}
