# Time tracker
#### This small project represents a multi-user time tracker (without registration and authorization). The point of the project is to track how much time did an user spend on a particular task

# Usage

## Build

### Requirements:
- #### JDK21
- #### Gradle

### To build (macos):
- #### clone the project
- #### open terminal
- #### move to the cloned directory
- #### use this commands: 

`./gradlew clean && ./gradlew build && java -jar build/libs/time-tracker-assignment-0.0.1-SNAPSHOT.jar`
#### Now you can use project with Postman

## What this project can do

### Endpoints with `BASE_URL=http://localhost:8080/api/v1`:
- #### Creates user

`Request: POST {BASE_URL}/user/create RequestBody={"username": "<username>"}`

`Response: 200 OK "User successfully created", 400 BAD_REQUEST "Username can't be blank!"`

- #### Updates user

`Request: PATCH {BASE_URL}/user/update RequestBody={"oldUsername": "<oldUsername>", "newUsername: "<newUsername>"}`

`Response: 200 OK "User successfully updated, 400 BAD_REQUEST "Username can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Deletes user and all it's tasks

`Request: DELETE {BASE_URL}/user/delete RequestBody={"username": "<username>"}`

`Response: 200 OK "User <username> successfully deleted", 400 BAD_REQUEST "Username can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Deletes all user's tasks info but not user itself

`Request: DELETE {BASE_URL}/user/delete-tracking RequestBody={"username": "<username>"}`

`Response: 200 OK "<username>'s tracking info successfully deleted", 400 BAD_REQUEST "Username can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Starts tracking a task for user

`Request: POST {BASE_URL}/task/start RequestBody={"username": "<username>", "name": "<name>"}`

`Response: 200 OK "Task successfully started", 400 BAD_REQUEST "Username can't be blank!" / "Task name can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Finishes tracking a task for user

`Request: POST {BASE_URL}/task/finish RequestBody={"username": "<username>", "name": "<name>"}`

`Response: 200 OK "Task successfully finished", 400 BAD_REQUEST "Username can't be blank!" / "Task name can't be blank!" / "User <username> hasn't got such task: <name>", 404 NOT_FOUND "Task with name: <name> is not found!"`

- #### Shows how much time did the user spend on the every separate task

`Request: GET {BASE_URL}/summary/task-time RequestBody={"username": "<username>", "from": "<from (Date format yyyy-mm-dd)>", "to": "<to (Date format yyyy-mm-dd)>"}`

`Response: 200 OK "[<task1>:<timeSpent1>, <task2>:<timeSpent2>...]", 400 BAD_REQUEST "Username can't be blank!" / "The period of time can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Shows how much time did the user spend on the every separate task by days (to get an answer to a question like "How much did I get done last week?")

`Request: GET {BASE_URL}/summary/time-task RequestBody={"username": "<username>", "from": "<from (Date format yyyy-mm-dd)>", "to": "<to (Date format yyyy-mm-dd)>"}`

`Response: 200 OK "[<time1 (Date format day)>:<task1>, <time2 (Date format day)>:<task2>...]", 400 BAD_REQUEST "Username can't be blank!" / "The period of time can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

- #### Shows the total time the user has been working for a certain period of time (from ... to ...)

`Request: GET {BASE_URL}/summary/worked RequestBody={"username": "<username>", "from": "<from (Date format yyyy-mm-dd)>", "to": "<to (Date format yyyy-mm-dd)>"}`

`Response: 200 OK "<houresSpentOnWork>:<minutesSpentOnWork>", 400 BAD_REQUEST "Username can't be blank!" / "The period of time can't be blank!", 404 NOT_FOUND "User with username: <username> is not found"`

# For any questions you can contact me:
- ### telegram: @wdsjkska
- ### discord: wdsjk