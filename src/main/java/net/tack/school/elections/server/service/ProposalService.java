package net.thumbtack.school.elections.server.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerUtils;
import net.thumbtack.school.elections.server.daoimpl.ProposalDaoImpl;
import net.thumbtack.school.elections.server.dto.request.*;
import net.thumbtack.school.elections.server.dto.response.AllProposalsDtoResponse;
import net.thumbtack.school.elections.server.dto.response.GetProposalsByAuthorDtoResponse;
import net.thumbtack.school.elections.server.model.Proposal;
import net.thumbtack.school.elections.server.model.Voter;

import java.util.List;


public class ProposalService {
    private Gson gson;
    private ProposalDaoImpl proposalDao;

    public ProposalService() {
        gson = new Gson();
        this.proposalDao = new ProposalDaoImpl();
    }

    public String addProposal(String requestJsonString) {
        AddProposalDtoRequest request = gson.fromJson(requestJsonString, AddProposalDtoRequest.class);
        try {
            request.validate();
            Voter voter = proposalDao.getVoterByToken(request.getToken());
            Proposal proposal = new Proposal(request.getText(), voter.getUser().getLogin());
            proposalDao.addProposal(proposal, voter);
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String getAllProposals(String requestJsonString) {
        AllProposalsDtoRequest request = gson.fromJson(requestJsonString, AllProposalsDtoRequest.class);
        try {
            request.validate();
            proposalDao.getVoterByToken(request.getToken());
            return gson.toJson(new AllProposalsDtoResponse(proposalDao.getAllProposals()), AllProposalsDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String rateIt(String requestJsonString) {
        RateItDtoRequest request = gson.fromJson(requestJsonString, RateItDtoRequest.class);
        try {
            request.validate();
            proposalDao.rateIt(
                    proposalDao.getProposalById(request.getIdOfProposal()),
                    proposalDao.getVoterByToken(request.getToken()),
                    request.getRating());
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String getProposalsByAuthor(String requestJsonString) {
        GetProposalsByAuthorDtoRequest request =
                gson.fromJson(requestJsonString, GetProposalsByAuthorDtoRequest.class);
        try {
            request.validate();
            proposalDao.getVoterByToken(request.getToken());
            List<Proposal> proposals;
            if (request.getAuthors().size() == 1) {
                proposals = proposalDao.getProposalsByAuthor(request.getAuthors().get(0));
            } else {
                proposals = proposalDao.getProposalsByAuthors(request.getAuthors());
            }
            return gson.toJson(new GetProposalsByAuthorDtoResponse(proposals), GetProposalsByAuthorDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String removeProposalRating(String requestJsonString) {
        RemoveProposalRatingDtoRequest request = gson.fromJson(requestJsonString, RemoveProposalRatingDtoRequest.class);
        try {
            request.validate();
            proposalDao.removeProposalRating(
                    proposalDao.getVoterByToken(request.getToken()),
                    request.getIdOfProposal());
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }

    }

    public String updateProposalRating(String requestJsonString) {
        UpdateProposalRatingDtoRequest request = gson.fromJson(requestJsonString, UpdateProposalRatingDtoRequest.class);
        try {
            request.validate();
            proposalDao.updateProposalRating(
                    proposalDao.getVoterByToken(request.getToken()),
                    request.getIdOfProposal(),
                    request.getRating());
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }
}
