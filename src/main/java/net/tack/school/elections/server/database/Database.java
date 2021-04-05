package net.thumbtack.school.elections.server.database;


import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;
import net.thumbtack.school.elections.server.model.Candidate;
import net.thumbtack.school.elections.server.model.Proposal;
import net.thumbtack.school.elections.server.model.Voter;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

public final class Database {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static Database instance;
    private Map<String, Voter> voters;// логин - Voter, база всех Voter
    private Map<Integer, Proposal> proposals;// id - Proposal, база всех Proposal
    private Map<Voter, Candidate> candidates;// база кандидатов
    private BidiMap<String, Voter> online;// токен - Voter, текущая сессия Voter
    private Map<Proposal, Map<Voter, Integer>> proposalsRating;// Proposal - (автор - оценка), отображение базы на оценки
    private MultiValuedMap<Candidate, Voter> elections;// кандидат - голоса
    private int toNoOneCount;

    private Database() {
        voters = new HashMap<>();
        proposals = new HashMap<>();
        online = new DualHashBidiMap<>();
        proposalsRating = new HashMap<>();
        elections = new HashSetValuedHashMap<>();
        candidates = new HashMap<>();
        toNoOneCount = 0;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void clear() {
        voters.clear();
        proposals.clear();
        online.clear();
        proposalsRating.clear();
        elections.clear();
        candidates.clear();
        toNoOneCount = 0;
    }

    public void set(Database database) {
        this.voters = database.voters;
        this.proposals = database.proposals;
        this.online = database.online;
        this.proposalsRating = database.proposalsRating;
        this.elections = database.elections;
        this.candidates = database.candidates;
        this.toNoOneCount = database.toNoOneCount;
    }

    /*public void To() {
        Map<Voter, Collection<Proposal>> proposalsByAuthor1 = proposalsByAuthor.asMap();
    }

    public void From() {
        MultiValuedMap<Voter, Proposal> proposalsByAuthor
                = new HashSetValuedHashMap<>();
        Iterator<Map.Entry<Voter, Collection<Proposal>>> iterator = proposalsByAuthor.asMap().entrySet().iterator();
        while (iterator.hasNext()) {
            proposalsByAuthor.putAll(iterator.next().getKey(),iterator.next().getValue());
        }
    }

    public Type typeOfMapProposal() {
        Type typeOfMap = new TypeToken<MultiValuedMap<String, Proposal>>(){}.getType();
        return typeOfMap;
    }

    public Type typeOfMapMap() {
        Type typeOfMap = new TypeToken<MultiValuedMap<String, Map<String, Integer>>>(){}.getType();
        return typeOfMap;
    }*/

////// регистрация авторизация аутентификация

    public void insertVoter(Voter voter) throws ServerException {
        if (voters.putIfAbsent(voter.getUser().getLogin(), voter) != null) {
            throw new ServerException(ServerExceptionErrorCode.VOTER_REGISTRATION_FAILED);
        }
    }

    public Voter checkPasswordAndDelReg(String login, String pass) throws ServerException {
        Voter voter = getVoterByLogin(login);
        if (!voter.getUser().getPass().equals(pass))
            throw new ServerException(ServerExceptionErrorCode.PASS_INCORRECT);
        if (voter.getUser().isRemoved())
            throw new ServerException(ServerExceptionErrorCode.VOTER_IS_REMOVED);
        return voter;
    }

    public boolean isVoterRegistered(String login) throws ServerException {
        if (voters.containsKey(login))
            throw new ServerException(ServerExceptionErrorCode.VOTER_IS_REGISTERED);
        return  true;
    }

    public void logIn(String token, Voter voter) throws ServerException {
        if (online.containsValue(voter))
            throw new ServerException(ServerExceptionErrorCode.VOTER_IS_ONLINE);
        if (online.putIfAbsent(token, voter) != null)
            throw new ServerException(ServerExceptionErrorCode.LOGIN_IS_FAIL);
    }

    private Voter getVoterByLogin(String login) throws ServerException {
        Voter voter = voters.get(login);
        if (voter == null)
            throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        return voter;
    }

    public Voter getVoterByToken(String token) throws ServerException {
        Voter voter = online.get(token);
        if (voter == null)
            throw new ServerException(ServerExceptionErrorCode.VOTER_IS_OFFLINE);
        return voter;
    }

    public void logOut(String token) throws ServerException {
        if (online.remove(token) == null)
            throw new ServerException(ServerExceptionErrorCode.LOGOUT_IS_FAIL);
    }

////// операции для Voter

    public List<Voter> getAllVoters() throws ServerException {
        if (voters.isEmpty())
            throw new ServerException(ServerExceptionErrorCode.NO_VOTERS);
        return voters.values().stream().sorted(Comparator.comparing(s -> s.getUser().getLastName())).collect(Collectors.toList());
    }

    public void removeVoter(Voter voter) throws ServerException {
        if (candidates.get(voter) != null)
            throw new ServerException(ServerExceptionErrorCode.TAKE_APPROVAL_OFF);
        voter.getUser().setRemoved(true);
        for (Proposal proposal : voter.getSelfProposals()) {
            proposalsRating.get(proposal).remove(voter);
            proposal.setCityAuthor();
            updateAverageRating(proposal);
        }
    }

    public Voter retainVoter(String login, String pass) throws ServerException {
        Voter voter = getVoterByLogin(login);
        if (voter == null)
            throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        if (!voter.getUser().getPass().equals(pass))
            throw new ServerException(ServerExceptionErrorCode.PASS_INCORRECT);
        voter.getUser().setRemoved(false);
        for (Proposal proposal : voter.getSelfProposals()) {
            proposalsRating.get(proposal).put(voter, MAX_RATING);
            updateAverageRating(proposal);
            proposal.setAuthor(voter.getUser().getLogin());
        }
        return voter;
    }

////// операции для Proposal

    public void insertProposal(Proposal proposal, Voter voter) throws ServerException {
        if (proposals.putIfAbsent(proposal.getId(), proposal) != null)
            throw new ServerException(ServerExceptionErrorCode.PROPOSAL_EXIST);
        Map<Voter, Integer> map = new HashMap<>(2);
        map.put(voter, MAX_RATING);
        proposalsRating.put(proposal, map);
        voter.addSelfProposals(proposal);
    }

    public Proposal getProposalById(int id) throws ServerException {
        Proposal proposal = proposals.get(id);
        if (proposal == null)
            throw new ServerException(ServerExceptionErrorCode.PROPOSAL_INCORRECT);
        return proposal;
    }

    public void rateIt(Proposal proposal, Voter voter, Integer rating) throws ServerException {
        if (voter.getSelfProposals().contains(proposal))
            throw new ServerException(ServerExceptionErrorCode.CANT_DEL_SELF_PROPS);
        proposalsRating.get(proposal).put(voter, rating);
        updateAverageRating(proposal);
    }

    public List<Proposal> getProposals() throws ServerException {
        List<Proposal> props = proposals.values().stream().sorted(Comparator.comparingDouble(Proposal::getAverageRating)).
                collect(Collectors.toList());
        if (props.isEmpty())
            throw new ServerException(ServerExceptionErrorCode.PROPOSAL_INCORRECT);
        return props;
    }

    public List<Proposal> getProposalsByAuthor(String login) throws ServerException {
        return getVoterByLogin(login).getSelfProposals().stream().sorted(Comparator.comparingDouble(Proposal::getAverageRating))
                .collect(Collectors.toList());
    }

    public List<Proposal> getProposalsByAuthors(List<String> voters) throws ServerException {
        SortedSet<Proposal> proposalsList = new TreeSet<>(Comparator.comparingDouble(Proposal::getAverageRating));
        for (String voter : voters) {
            proposalsList.addAll(getVoterByLogin(voter).getSelfProposals());
        }
        return List.copyOf(proposalsList);
    }

    public void removeProposalRating(Voter voter, Integer proposal) throws ServerException {
        if (voter.getSelfProposals().contains(getProposalById(proposal)))
            throw new ServerException(ServerExceptionErrorCode.CANT_DEL_SELF_PROPS);
        proposalsRating.get(getProposalById(proposal)).remove(voter);
        updateAverageRating(getProposalById(proposal));
    }

    public void updateProposalRating(Voter voter, Integer proposal, Integer rating) throws ServerException {
        if (voter.getSelfProposals().contains(getProposalById(proposal)))
            throw new ServerException(ServerExceptionErrorCode.CANT_DEL_SELF_PROPS);
        proposalsRating.get(getProposalById(proposal)).replace(voter, rating);
        updateAverageRating(getProposalById(proposal));
    }

    private void updateAverageRating(Proposal proposal) {
        OptionalDouble average = proposalsRating.get(proposal).values().stream()
                .mapToInt(Integer::intValue).average();//+обработчик если среднего нет
        if (average.isPresent()) {
            proposal.setAverageRating((float) average.getAsDouble());
        }
    }

////// операции для Candidate

    public void addCandidate(Voter voter1, Voter voter2) throws ServerException {
        Candidate candidate = new Candidate(voter2.getUser());
        if (candidates.putIfAbsent(voter2, candidate) == null) {
            candidate.getCandidateProposals().addAll(voter2.getSelfProposals());
            if (voter1.equals(voter2))
                candidate.setApproval(true);
        }
        if (!candidate.getApproval()) {
            if (voter1.equals(voter2))
                candidate.setApproval(true);
            else throw new ServerException(ServerExceptionErrorCode.CANDIDATE_IS_NOT_GIVE_HIS_APPROVAL);
        }
    }

    public Candidate getCandidateByVoter(Voter voter) throws ServerException {
        Candidate candidate = candidates.get(voter);
        if (candidate == null)
            throw new ServerException(ServerExceptionErrorCode.NOT_CANDIDATE);
        return candidate;
    }

    public void giveAprovall(Voter voter) throws ServerException {
        Candidate candidate = getCandidateByVoter(voter);
        candidate.setApproval(true);
    }

    public void removeProposalOfCandidate(Voter candidate, Integer proposal) throws ServerException {
        if (!getCandidateByVoter(candidate).getCandidateProposals()
                .removeIf(p -> !p.getAuthor().equals(candidate.getUser().getLogin())
                        && p.getId() == proposal))
            throw new ServerException(ServerExceptionErrorCode.CANT_DEL_SELF_PROPS);
    }

    public void addProposalForCandidate(Voter candidate, Integer proposal) throws ServerException {
        getCandidateByVoter(candidate).addCandidateProposals(getProposalById(proposal));
    }

////// выборы

    public void elect(Voter voter1, Voter voter2) throws ServerException {
        Candidate candidate = getCandidateByVoter(voter2);
        if (voter1.getUser().isRemoved())
            throw new ServerException(ServerExceptionErrorCode.VOTER_IS_REMOVED);
        if (!voter1.getVoice())
            throw new ServerException(ServerExceptionErrorCode.UNE_TIME_ONLY);
        if (voter1.equals(voter2))
            throw new ServerException(ServerExceptionErrorCode.SELF_ELECT);
        if (!candidate.getApproval())
            throw new ServerException(ServerExceptionErrorCode.CANDIDATE_IS_NOT_GIVE_HIS_APPROVAL);
        if (candidate.getCandidateProposals().isEmpty())
            throw new ServerException(ServerExceptionErrorCode.PROPOSAL_INCORRECT);
        if (voter2 == null)
            ++toNoOneCount;
        else
            elections.put(candidate, voter1);
        voter1.setVoice(false);
    }

    private Map.Entry<Integer, Candidate> election() throws ServerException {
        TreeMap<Integer, Candidate> result = new TreeMap<>(Integer::compareTo);
        MapIterator<Candidate, Voter> mi = elections.mapIterator();
        while (mi.hasNext()) {
            Candidate candidate = mi.getKey();
            result.put(elections.get(candidate).size(), candidate);
        }
        if (result.pollFirstEntry().getKey() >= toNoOneCount)
            return result.pollFirstEntry();
        else
            throw new ServerException(ServerExceptionErrorCode.ELECTIONS_FAILD);
    }
}