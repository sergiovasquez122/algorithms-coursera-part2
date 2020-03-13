import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BaseballElimination {

    private ST<String,Integer> st;
    private String teamNames[];
    private int numberOfTeams;
    private int wins[];
    private int losses[];
    private int gamesAgainst[][];
    private int remainingGames[];
    private boolean isEliminated[];
    private SET<String> certificateOfElimination[];

    public BaseballElimination(String filename){
        In in = new In(filename);
        st = new ST<>();
        numberOfTeams = in.readInt();
        in.readLine();
        certificateOfElimination = new SET[numberOfTeams];
        for(int i = 0;i<numberOfTeams;++i)
            certificateOfElimination[i] = new SET<>();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remainingGames = new int[numberOfTeams];
        gamesAgainst = new int[numberOfTeams][numberOfTeams];
        isEliminated = new boolean[numberOfTeams];
        teamNames = new String[numberOfTeams];
        int index = 0;
        while(in.hasNextLine()){
            String line[] = in.readLine().trim().split(" +");
            st.put(line[0],index);
            wins[index] = Integer.parseInt(line[1]);
            losses[index] = Integer.parseInt(line[2]);
            teamNames[index] = line[0];
            remainingGames[index] = Integer.parseInt(line[3]);
            for(int i = 4;i<line.length;++i)
                gamesAgainst[index][i-4] = Integer.parseInt(line[i]);
            index++;
        }
        trivialElimination();

        for(int i = 0;i<numberOfTeams;++i)
            if(!isEliminated[i])
                maxFlowProblem(i);
    }

    public int numberOfTeams(){
        return numberOfTeams;
    }

    public Iterable<String> teams(){
        return st.keys();
    }

    public int wins(String team){
        if(st.contains(team))
            return wins[st.get(team)];
        throw new IllegalArgumentException();
    }

    public int losses(String team){
        if(st.contains(team))
            return losses[st.get(team)];
        throw new IllegalArgumentException();
    }

    public int remaining(String team){
        if(st.contains(team))
            return remainingGames[st.get(team)];
        throw new IllegalArgumentException();
    }

    public int against(String team1,String team2){
        if(st.contains(team1) && st.contains(team2))
            return gamesAgainst[st.get(team1)][st.get(team2)];
        throw new IllegalArgumentException();
    }

    public boolean isEliminated(String team){
        if(st.contains(team))
            return isEliminated[st.get(team)];
        throw new IllegalArgumentException();
    }

    public Iterable<String> certificateOfElimination(String team){
        if(st.contains(team))
            return certificateOfElimination[st.get(team)];
        throw new IllegalArgumentException();
    }

    /**
     * solve a maxflow problem given a team x
     */
    private void maxFlowProblem(int x){

        ArrayList<String> games  = createPossibleGames(x);

        // the vertices are all the game vertices the indiviual team nodes and the artifical source and sink
        int vertices = games.size() + numberOfTeams + 2;
        int artifical_source = vertices-2;
        int artificial_sink = vertices-1;
        FlowNetwork network = new FlowNetwork(vertices);
        //append the artifical sink to the game vertices
        int offset = numberOfTeams;
        for(int i = 0;i< games.size();++i){
            String nodes[] = games.get(i).split("-");
            int ith = Integer.parseInt(nodes[0]);
            int jth = Integer.parseInt(nodes[1]);
            network.addEdge(new FlowEdge(artifical_source,i+offset,gamesAgainst[ith][jth]));
        }

        for(int i = 0;i<games.size();++i){
            String nodes[] = games.get(i).split("-");
            int ith = Integer.parseInt(nodes[0]);
            int jth = Integer.parseInt(nodes[1]);
            network.addEdge(new FlowEdge(i+offset,ith,Double.POSITIVE_INFINITY));
            network.addEdge(new FlowEdge(i+offset,jth,Double.POSITIVE_INFINITY));
        }

        for(int i = 0;i<numberOfTeams;++i){
            if(i==x)continue;
            network.addEdge(new FlowEdge(i,artificial_sink,wins[x]+remainingGames[x]-wins[i]));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(network,artifical_source,artificial_sink);

        for(FlowEdge e : network.adj(artifical_source))
            if(e.flow()!=e.capacity())
            {
            isEliminated[x] = true;
            }


           if(isEliminated[x]) {
            for(int i = 0;i<numberOfTeams;++i)
                if(i!=x && fordFulkerson.inCut(i)) certificateOfElimination[x].add(teamNames[i]);
       }

    }

    private void trivialElimination(){
        for(int x = 0;x<numberOfTeams;++x){
            for(int i = 0;i<numberOfTeams;++i){
                if(i==x) continue;
                if(wins[x]+remainingGames[x]<wins[i]) {
                    isEliminated[x] = true;
                    certificateOfElimination[x].add(teamNames[i]);
                }
            }
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams54.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

    }

    private ArrayList<String> createPossibleGames(int x) {
        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; ++i)
            for (int j = i + 1; j < numberOfTeams; ++j) {
                if (i == x || j == x) continue;
                StringBuilder sb = new StringBuilder(i + "-" + j);
                games.add(sb.toString());
            }
            return games;
    }

}
