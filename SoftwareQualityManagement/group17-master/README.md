# University Information System #

The University Information System (UIS) is a web-based management software for universities. <br/>
Students and faculty staff can organize and administrate their courses, the system furthermore assists students by
suggesting future courses in a smart way.

## Setup and running ##

Execute the steps described below on your command-line.

Step 1 has only be done once (as long as you do not delete the files).

### 1. Setup ###

Download Javascript Libraries:

```
cd application/src/main/resources/static
npm install
./node_modules/.bin/webpack
```

_Prerequisite:_ npm (Node.js) has to be available on your computer.

### 2. Build ###

Build your project using Maven:

```
mvn clean install
```

or (in case you want to skip test execution)

```
mvn clean install -DskipTests
```

_Prerequisite:_  Maven 3.3.x or higher and Java 1.8 has to be available on your computer.

### 3. Execution ###

Deploy the application on Spring Boot's Embedded Apache Tomcat 7 Application Server:

```
cd application
mvn spring-boot:run
```

After start-up, the application is available under:
http://localhost:8080/

### 4. Sample Data and User Accounts ###

```at.ac.tuwien.inso.sqm.initializer.DataInitializer``` automatically generates sample data for the application.<br/>
Log in with the following credentials:

```
Student: emma - pass
Lecturer: eric - pass
Administrator: admin - pass
```



