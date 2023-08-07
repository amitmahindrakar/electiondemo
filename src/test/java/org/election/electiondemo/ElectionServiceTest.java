package org.election.electiondemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.election.electiondemo.domain.ElectionData;
import org.election.electiondemo.domain.Vote;
import org.election.electiondemo.service.ElectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ElectionServiceTest {

  @Test
  public void test_valid_sample_data() throws IOException {
    File file1 = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("samples/sample_data.json")).getFile());
    File file2 = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("samples/sample_data2.json")).getFile());
    assertElectionResult(file1);
    assertElectionResult(file2);
  }

  private void assertElectionResult(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ElectionData electionData = mapper.readValue(file, ElectionData.class);
    Integer winner = new ElectionService().countBallots(electionData);
    Assertions.assertEquals(winner, Integer.valueOf(electionData.getWinner()));
  }

  @Test
  public void test_no_winner() {
    ElectionData electionData = buildElectionData();
    Integer result = new ElectionService().countBallots(electionData);
    Assertions.assertNull(result);
  }

  private ElectionData buildElectionData() {
    ElectionData electionData = new ElectionData();
    List<Vote> votes = new ArrayList<>();
    votes.add(new Vote(1000, Arrays.asList(0,1,2,3)));
    votes.add(new Vote(1000, Arrays.asList(0,1,2,3)));
    votes.add(new Vote(1000, Arrays.asList(0,1,2,3)));
    votes.add(new Vote(1000, Arrays.asList(0,1,2,3)));
    votes.add(new Vote(1000, Arrays.asList(1,2,3,0)));
    votes.add(new Vote(1000, Arrays.asList(1,2,3,0)));
    votes.add(new Vote(1000, Arrays.asList(1,2,3,0)));
    votes.add(new Vote(1000, Arrays.asList(1,2,3,0)));
    votes.add(new Vote(1000, Arrays.asList(2,3,0,1)));
    votes.add(new Vote(1000, Arrays.asList(2,3,0,1)));
    votes.add(new Vote(1000, Arrays.asList(2,3,0,1)));
    votes.add(new Vote(1000, Arrays.asList(2,3,0,1)));
    votes.add(new Vote(1000, Arrays.asList(3,0,1,2)));
    votes.add(new Vote(1000, Arrays.asList(3,0,1,2)));
    votes.add(new Vote(1000, Arrays.asList(3,0,1,2)));
    votes.add(new Vote(1000, Arrays.asList(3,0,1,2)));
    electionData.setVotes(votes);
    electionData.setNumberOfCandidates(4);
    return electionData;
  }
}