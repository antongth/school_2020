package net.thumbtack.school.elections.server.dto.response;

import net.thumbtack.school.elections.server.model.Voter;

import java.util.List;

public class AllVotersDtoResponse {
    private List<Voter> voters;

    public AllVotersDtoResponse(List<Voter> candidates) {
        this.voters = candidates;
    }

    public List<Voter> getAllVoters() {
        return voters;
    }

    public void setAllVoters(List<Voter> candidates) {
        this.voters = candidates;
    }
}
