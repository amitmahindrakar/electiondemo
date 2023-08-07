package org.election.electiondemo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.election.electiondemo.domain.ElectionData;
import org.election.electiondemo.domain.Vote;
import org.election.electiondemo.domain.VotesPile;
import org.springframework.stereotype.Component;

@Component
public class ElectionService {

  private Integer findWinner(Map<Integer, VotesPile> candidateVotes, Integer totalVotes) {
    Integer winners = null;
    for (Entry<Integer, VotesPile> entry : candidateVotes.entrySet()) {
      Integer key = entry.getKey();
      VotesPile value = entry.getValue();
      if (value.getCount() > totalVotes / 2) {
        winners = key;
      }
    }
    return winners;
  }

  private Map<Integer, VotesPile> divideVotesToCandidateBuckets(ElectionData election) {
    int totalVotes = 0;
    int numberOfCandidates = election.getNumberOfCandidates();
    List<Vote> allVotes = election.getVotes();

    //initialize
    Map<Integer, VotesPile> electionBuckets = IntStream.range(0, numberOfCandidates).boxed()
        .collect(
            Collectors.toMap(i -> i, i -> new VotesPile(0, new ArrayList<>()), (a, b) -> b));

    for (Vote vote : allVotes) {
      totalVotes += vote.getCount();
      VotesPile pile = electionBuckets.get(vote.getPreference().get(0));
      pile.addVote(vote);
      pile.addVoteCount(vote.getCount());
    }
    election.setTotalVotes(totalVotes);
    return electionBuckets;
  }

  private void dropEliminatedCandidateVotesToNextPreferredCandidate(
      Map<Integer, VotesPile> candidateVotes, Set<Integer> eliminatedCandidates) {
    //need to reduce big O
    eliminatedCandidates.forEach(
        candidate -> candidateVotes.get(candidate).getVotes().forEach(vote -> {
          Integer voteCount = vote.getCount();
          Optional<Integer> preferredCandidate = vote.getPreference().stream().filter(
              candidateId -> !eliminatedCandidates.contains(candidateId)).findFirst();
          preferredCandidate.ifPresent(integer -> {
            VotesPile nextPreferredVotes = candidateVotes.get(preferredCandidate.get());
            nextPreferredVotes.setCount(nextPreferredVotes.getCount() + voteCount);
          });
        }));
  }

  private void eliminate(Map<Integer, VotesPile> candidateVotes,
      Set<Integer> eliminatedCandidates) {
    Map<Integer, Integer> eliminated = new HashMap<>(); // this needs to have minimum one value

    candidateVotes.entrySet().stream().filter(entry ->
        !eliminatedCandidates.contains(entry.getKey())).forEach(entry -> {
      eliminated.put(entry.getKey(), entry.getValue().getCount()); // initialise
    });

    int leastVotes = Integer.MAX_VALUE;
    for (Map.Entry<Integer, VotesPile> entry : candidateVotes.entrySet()) {
      if (!eliminatedCandidates.contains(entry.getKey())
          && entry.getValue().getCount() < leastVotes) {
        leastVotes = entry.getValue().getCount();
      }
    }

    for (Map.Entry<Integer, Integer> entry : eliminated.entrySet()) {
      if (entry.getValue() == leastVotes) {
        eliminatedCandidates.add(entry.getKey());
      }
    }
  }

  public Integer countBallots(ElectionData election) {
    Map<Integer, VotesPile> candidateVotes = divideVotesToCandidateBuckets(election);
    Integer winner = findWinner(candidateVotes, election.getTotalVotes());
    if (Objects.nonNull(winner)) {
      return winner;
    }
    //map above get garbage collected
    Set<Integer> eliminatedCandidates = new HashSet<>();

    do {
      eliminate(candidateVotes,
          eliminatedCandidates); // both objects are being modified in child methods
      dropEliminatedCandidateVotesToNextPreferredCandidate(candidateVotes, eliminatedCandidates);
      winner = findWinner(candidateVotes, election.getTotalVotes());
    } while (eliminatedCandidates.size() != election.getNumberOfCandidates() && Objects.isNull(winner));
    return winner;
  }
}
