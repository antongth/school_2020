package net.thumbtack.school.elections.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Candidate {
    private final User user;
    private List<Proposal> candidateProposals;
    private boolean approval = false; //согласие на участие в выборах

    public Candidate(User user) {
        this.user = user;
        candidateProposals = new ArrayList<>();
    }

    public boolean getApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public User getUser() {
        return user;
    }

    public List<Proposal> getCandidateProposals() {
        return candidateProposals;
    }

    public void addCandidateProposals(Proposal proposal) {
        candidateProposals.add(proposal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate)) return false;
        Candidate candidate = (Candidate) o;
        return getUser().getLogin().equals(candidate.getUser().getLogin());
        //return Objects.equals(getUser().getLogin(), candidate.getUser().getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser().getLogin());
    }
}
