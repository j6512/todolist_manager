# Todo List Manager
## overview

TodoList Manager is a studying / time management web app that allows Users to create lists that can be filled with tasks to do. There is a countdown timer that can be configured by the User in which they can set a timer for their "study sessions" as well as a timer for their "study breaks". The aim of this web app is to help Users focus on the tasks on their lists and to promote better time management.

The idea came from personal experience where I found it difficult to concentrate on a given task without getting side tracked. I found that by breaking up studying sessions into shorter segments followed by a break at the end of each segment, my productivity increased and that I was able to stay focused on what I was currently working on. The purpose of the study breaks is for Users to have something to look forward to after giving their entire attention to the task during their study session. Users are free to do whatever they would like within that study break time frame. Once the break ends, Users can get back to working on their tasks.

## features

#### user registration
- Users can register an account and login/logout.

#### todo lists and tasks
- Users can create/edit/view/delete a list that will be used to store their tasks.

- Users can create/edit/view/delete their tasks.

#### search functionality
- Users can search for a specific list or task.

#### countdown timer
- Users can configure the built-in countdown timer and set intervals for their "study session", "study break", and how many times the cycle should be repeated.
- Users can select preconfigured buttons for a basic timer countdown feature

## technologies used
- Java

- Spring Boot

- Spring Security

- Gradle

- Thymeleaf

- MySQL

- Bootstrap

- Junit Testing

- HTML/CSS/Javascript


## how to run this repo

1. Clone or download the repo to your machine
2. Set up a MySQL schema as well as a User with access to that schema
3. Open the project in your IDE and navigate to todolist_manager/src/main/resources/application.properties
4. Configure and update the top of the file with your username, password, and schema name
 ```
 spring.datasource.url=jdbc:mysql://localhost:3306/schema_name
 spring.datasource.username=user
 spring.datasource.password=password
 ```
5. Build the project by using Gradle's Boot Run located at Tasks/application/bootRun
6. Navigate to localhost:8080
