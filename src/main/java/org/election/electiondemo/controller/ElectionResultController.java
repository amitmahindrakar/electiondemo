package org.election.electiondemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.election.electiondemo.domain.ElectionData;
import org.election.electiondemo.service.ElectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    log.info("Received Request to get election result");
    ObjectMapper mapper = new ObjectMapper();
    try {
      String content = new String(file.getBytes(), StandardCharsets.UTF_8);
      ElectionData electionData = mapper.readValue(content, ElectionData.class);
      Integer winner = electionService.countBallots(electionData);
      if (Objects.isNull(winner)) {
        return new ResponseEntity<>("nobody", HttpStatusCode.valueOf(200));
      }
      return new ResponseEntity<>(electionData.getMetadata().get("candidates").get(winner).textValue(), HttpStatusCode.valueOf(200));
    } catch (Exception e) {
      return new ResponseEntity<>("There was an error in processing the request", HttpStatusCode.valueOf(400));
    }
  }
}
