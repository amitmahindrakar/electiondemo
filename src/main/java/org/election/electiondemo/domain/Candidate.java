package org.election.electiondemo.domain;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Candidate {

  private Integer candidateId;
  private String candidateName;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Candidate candidate = (Candidate) o;
    return Objects.equals(candidateId, candidate.candidateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(candidateId, candidateName);
  }
}
