package net.thumbtack.school.elections.server.daoimpl;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.dao.DAO;
import net.thumbtack.school.elections.server.model.Proposal;
import net.thumbtack.school.elections.server.model.Voter;

import java.util.List;

public class ProposalDaoImpl implements DAO<Proposal> {


    public void addProposal(Proposal proposal, Voter voter) throws ServerException {
        database.insertProposal(proposal, voter);
    }

    public List<Proposal> getAllProposals() throws ServerException {
        return database.getProposals();
    }

    public Proposal getProposalById(int id) throws ServerException {
        return database.getProposalById(id);
    }

    public List<Proposal> getProposalsByAuthor(String login) throws ServerException {
        return database.getProposalsByAuthor(login);
    }

    public List<Proposal> getProposalsByAuthors(List<String> voters) throws ServerException{
        return database.getProposalsByAuthors(voters);
    }

    public void removeProposalOfCandidate(Voter voter, Integer proposal) throws ServerException {
        database.removeProposalOfCandidate(voter, proposal);
    }

    public void removeProposalRating(Voter voter, Integer proposal) throws ServerException {
        database.removeProposalRating(voter, proposal);
    }

    public void updateProposalRating(Voter voter, Integer proposal, Integer rating) throws ServerException {
        database.updateProposalRating(voter, proposal, rating);
    }

    public void rateIt(Proposal proposal, Voter voter, Integer rating) throws ServerException {
        database.rateIt(proposal, voter, rating);
    }

}
