**This is temporarily hosted at http://election-app.captorconsulting.com/ **

**Steps to deploy the application for testing**
1. install java 17 if not existing
2. execute ./mvnw package
3. execute java -jar target/electiondemo-1.0.0-SNAPSHOT.jar
4. go to localhost:8080
5. upload a valid json file(can be found at src/test/resources/samples)

**Assumptions Made:**
1. There can always be only 1 winner as only one candidate can have >50% votes
2. If no Candidate has more than 50% votes there will be no winner
3. Value in the ballot preference will always be less than the number of candidates(assuming index starts at 0)
4. Would like to work with a larger test data set than just 2 files. It is hard to convince that this code is defect free.

**Tests done**
1. both test data files have been verified in test class at path: src/test/java/org/election/electiondemo/ElectionServiceTest.java
2. Test to return no winner if the election is tied is done

**Unit Tests Pending:**
1. Invalid input file
2. Invalid Ballot data
3. Rest API response code are simplistic.

