package org.election.electiondemo.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

  private String type;
  @JsonProperty("total_auditable_ballots")
  private String totalAuditableBallots;
}
