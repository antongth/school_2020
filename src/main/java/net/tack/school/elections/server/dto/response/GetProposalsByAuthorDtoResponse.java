package net.thumbtack.school.elections.server.dto.response;

import net.thumbtack.school.elections.server.model.Proposal;

import java.util.List;

public class GetProposalsByAuthorDtoResponse {
    List<Proposal> proposals;

    public GetProposalsByAuthorDtoResponse(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
