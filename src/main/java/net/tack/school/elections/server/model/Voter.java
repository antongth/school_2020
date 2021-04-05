package net.thumbtack.school.elections.server.model;

import java.util.*;

public class Voter {
    private final User user;
    private final List<Proposal> selfProposals;
    private boolean voice = true;

    public Voter(User user) {
        this.user = user;
        selfProposals = new ArrayList<>();
    }

    public List<Proposal> getSelfProposals() {
        return selfProposals;
    }

    public void addSelfProposals(Proposal proposal) {
        selfProposals.add(proposal);
    }

    public boolean getVoice() {
        return voice;
    }

    public void setVoice(boolean voice) {
        this.voice = voice;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voter)) return false;
        Voter voter = (Voter) o;
        return getUser().getLogin().equals(voter.getUser().getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser().getLogin());
    }
}
