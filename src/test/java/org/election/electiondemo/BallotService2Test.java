package org.election.electiondemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.election.electiondemo.domain.ElectionData;
import org.election.electiondemo.domain.Vote;
import org.election.electiondemo.service.ElectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BallotService2Test {
    @Test
    public void testMe() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ElectionData electionData = mapper.readValue(new File("/Users/sandeepbajwa/Documents/ballot-apps/springboot-react-starter-master/backend/src/test/resources/sample_data2.json"), ElectionData.class);
//        ElectionData electionData = mapper.readValue(new File("/Users/sandeepbajwa/Documents/ballot-apps/springboot-react-starter-master/backend/src/test/resources/sample_data.json"), ElectionData.class);
//        System.out.println(electionData);
        ObjectReader reader = mapper.readerFor(new TypeReference<Map<String, List<String>>>() {});
        Map<String, List<String>> metadata = reader.readValue(electionData.getMetadata());
        Set<Integer> candidateSet = new HashSet<>();
        for(int i = 0 ; i < 4; i++) {
            candidateSet.add(i);
        }

        List<Vote> votes = new ArrayList<>();



//        Election election = new Election(candidateSet, 3, votes);
        Set<Integer> winners = new ElectionService().countBallots(electionData);
        winners.forEach(
                winner -> System.out.println("winner is " + metadata.get("candidates").get(winner)));

        Assertions.assertNotNull(votes);
    }

}