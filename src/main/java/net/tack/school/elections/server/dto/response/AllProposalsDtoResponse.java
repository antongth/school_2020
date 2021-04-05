package net.thumbtack.school.elections.server.dto.response;

import net.thumbtack.school.elections.server.model.Proposal;

import java.util.List;

public class AllProposalsDtoResponse {
    private List<Proposal> proposals;

    public AllProposalsDtoResponse(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
