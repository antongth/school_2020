package net.thumbtack.school.elections.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.server.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.server.dto.response.AllProposalsDtoResponse;
import net.thumbtack.school.elections.server.dto.response.RegisterVoterDtoResponse;
import net.thumbtack.school.elections.server.model.Proposal;

import java.util.List;

public class RobotClient {
    private Gson gson = new Gson();
    Server server;

    public RobotClient(Server server) {
        this.server = server;
    }

    public int getList(String token) {
        String prop = server.getAllProposals("{\"token\":\"" + token + "\"}");
        AllProposalsDtoResponse allProposalsDtoResponse = gson.fromJson(prop, AllProposalsDtoResponse.class);
        return allProposalsDtoResponse.getProposals().size();
    }

   public String regFakeClient(String registerVoterReq) {
       String response = server.registerVoter(registerVoterReq);
       RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(response, RegisterVoterDtoResponse.class);
       return registerVoterDtoResponse.getToken();
   }

   public String delFakeClient(String token) {
       return server.removeVoter("{\"token\":\"" + token + "\"}");
   }
}
