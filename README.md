**Steps to deploy the application for testing**
1. install java 17 if not existing
2. execute ./gradlew build from the root of the application code
3. execute ./gradlew bootRun to deploy the service locally

**Testing with postman:**
1. Create a post request with address: localhost:8080
2. go to body tab and select: form-data option
3. hover over the key and select "file" option
4. enter key: file, value: select path to a valid JSON ballot data

**alternatively testing via commandline**
in following command replace /sample_data.json with a valid path on your file system
curl --location 'localhost:8080/uploadFile' \
--form 'file=@"/sample_data.json"'

**Testing with front-end app locally**
prerequisite: NodeJs installation on the machine
steps:
1. deploy backend app using steps mentioned above
2. build front-end app using npm install
3. run front-end app using npm start

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

**There is work in progress to host this on AWS.**