package net.thumbtack.school.elections.server;


import net.thumbtack.school.elections.server.database.Database;

import net.thumbtack.school.elections.server.service.ProposalService;
import net.thumbtack.school.elections.server.service.VoterService;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Server {
    private VoterService voterService;
    private ProposalService proposalService;
    private Database database;
    private static Date nowDay;
    private static Date electionDay;

    public static void main (String[] args) {
        Server server = new Server();
        if (electionDayCheck() <= 0)
            server.startServer(null);
    }

    public Server() {
        voterService = new VoterService();
        proposalService = new ProposalService();
        database = Database.getInstance();
        GregorianCalendar electionDate = new GregorianCalendar(2020, Calendar.SEPTEMBER, 21);
        electionDay = electionDate.getTime();
    }

    public void startServer(String savedDataFileName) {
        if (savedDataFileName != null && !savedDataFileName.equals("")) {
            try (BufferedReader br = new BufferedReader(new FileReader(savedDataFileName))) {
                Database databaseback = ServerUtils.getGson().fromJson(br, Database.class);
                database.set(databaseback);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stopServer(String saveDataFileName) {
        if (saveDataFileName != null && !saveDataFileName.equals("")) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveDataFileName))) {
                ServerUtils.getGson().toJson(database, bw);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        database.clear();
    }

    private static int electionDayCheck() {
        GregorianCalendar nowDate = new GregorianCalendar();
        nowDay = nowDate.getTime();
        //return nowDay.compareTo(electionDay);
        return 0;
    }

    public String registerVoter(String requestJsonString) {
        if (electionDayCheck() > 0)
            return ServerUtils.makeErrorDateResponse();
        return voterService.registerVoter(requestJsonString);
    }

    public String getAllVoters(String requestJsonString) {
        return voterService.getAllVoters(requestJsonString);
    }

    public String logIn(String requestJsonString) {
        return voterService.logIn(requestJsonString);
    }

    public String logOut(String requestJsonString) {
        return voterService.logOut(requestJsonString);
    }

    public String removeVoter(String requestJsonString) {
        if (electionDayCheck() > 0)
            return ServerUtils.makeErrorDateResponse();
        return voterService.removeVoter(requestJsonString);
    }

    public String retainVoter(String requestJsonString) {
        if (electionDayCheck() > 0)
            return ServerUtils.makeErrorDateResponse();
        return voterService.retainVoter(requestJsonString);
    }

    public String getAllProposals(String requestJsonString) {
        return proposalService.getAllProposals(requestJsonString);
    }

    public String addProposal(String requestJsonString) {
        if (electionDayCheck() > 0)
            return ServerUtils.makeErrorDateResponse();
        return proposalService.addProposal(requestJsonString);
    }

    public String rateIt(String requestJsonString) {
        if (electionDayCheck() > 0)
            return ServerUtils.makeErrorDateResponse();
        return proposalService.rateIt(requestJsonString);
    }
}