package org.election.electiondemo.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotesPile {

  private Integer count;
  private List<Vote> votes;

  public void addVote(Vote vote) {
    votes.add(vote);
  }

  public void addVoteCount(int count) {
    this.count += count;
  }
}
