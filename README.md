# projects-01-spring-boot-to-do-list
projects-01-spring-boot-to-do-list created by GitHub Classroom

# Jason Duong

## Project Description
For my proposal, I decided to create a standalone project of a To-Do list for Group Project Management, utilizing Spring Boot to develop the backend, displaying a list of things that need to be completed through a web page. Project Managers can create and manage their groups, as well as manage individuals apart of their projects, who can make changes to their shared To-Do Lists, coordinating objectives through the website. 

Simple and easy-to-use, the To-Do list also shows urgent items, and ranks items by priority, further facilitating ease of schedule management. I came up with this idea because in a course like Software Design, where assignments are more team-focused, having some method of coordinating tasks would not only be convenient for a group, but also having a more dedicated to-do list would make it easier to find the tasks that the group should do, as opposed to a list of items in a group chat or some other communication medium.

### Use Case Diagram
![Individual Proposal](images/use_case.png)

## How this Project meets the Goals of this Course
This project adheres to the educational goals of CIS-3296: Software Design through giving an opportunity for students to gain experience with Java-based Frameworks for developing standalone, production-ready spring applications. While the REST API that Spring Boot employs is not using XML-RPC, the REST API provides the ability to build an HTTP API. The Spring-based backend will access a To-Do List stored in a MySQL database (or some other database) that is returned to a web page invoking the server. Since this project will be created from scratch, the team responsible for the creation of this project will employ Test-Driven Development to properly develop the To-Do List server. These goals are important in the context of Software Design since it offers a perspective in developing a fully functional software application from the ground up, utilizing many different concepts discussed in this course to do so. 

## About the Components and Conceptual Design
The main components involved in this project are Spring Boot, MySQL, and Postman. Spring Boot is an open source Java-based framework designed to develop standalone, production-grade Spring-based Applications. Spring Boot utilizes the Spring framework, which provides a programming and configuration model for Java-based applications or any other deployment platforms. MySQL, which will be used for the database component of the Project, is a database management system that allows users to manage relational databases, which has a GUI component through MySQL Workbench. Lastly, Postman will be utilized as a platform for API development to create HTTP Requests for testing the results that the server returns.

The program involves using a web page as a client program to manage their To-Do Lists, located in a database. Users can create their own group and invite others to their group, to access a To-Do List for a group project. Additionally, users can manage the to-do lists in the groups they are in, adding items, modifying items, and checking off items from their lists. These operations are sent to the Spring Boot server as an HTTP Request, which will invoke a MySQL database to make the changes specified by the client. The Spring Server will be responsible for returning these updated entries to the web page. 

## Project
As this is a standalone project, I created a preliminary project folder containing a very basic version of the project's implementation. I made sure to add some Spring Boot setup for those unfamiliar with creating Spring Boot projects, as well as a preliminary connection to a local MySQL database (should probably be changed once it goes public; this was mainly for testing purposes).

## Building
- Install Spring Boot compatibility into your Java IDE. I used Eclipse for my IDE. 
- Clone the repository, and open it in the IDE.
- Open the pom.xml file in the Project, which will install the dependencies required to run the Project.

## Running
- Select "Run As → Spring Boot App" to run the Spring Boot Application. For different IDEs, this may show something different.

## Required Resources
- MySQL Server (and maybe MySQL Workbench) (I used version 5.7)
- Java IDE compatible with Spring Boot (e.g. IntelliJ, Eclipse)
- Postman (for making HTTP Requests for testing)
- An IDE for developing web pages (e.g. NetBeans, Visual Studio Code)

## Releases
- 
