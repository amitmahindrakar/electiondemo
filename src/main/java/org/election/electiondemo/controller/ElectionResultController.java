package org.election.electiondemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.election.electiondemo.domain.ElectionData;
import org.election.electiondemo.service.ElectionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ElectionResultController {

  private final ElectionService electionService;

  @PostMapping(value = "file/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Set<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    log.info("Received Request to get election result");
    ObjectMapper mapper = new ObjectMapper();
    String content = new String(file.getBytes(), StandardCharsets.UTF_8);
    ElectionData electionData = mapper.readValue(content, ElectionData.class);
    Set<String> winners = new HashSet<>();
    electionService.countBallots(electionData).forEach(winner -> {
      winners.add(electionData.getMetadata().get("candidates").get(winner).textValue());
    });
    return winners;
  }
}
