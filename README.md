# Java-test
### Overview
Spring Boot project for general testing purposes.

### Technologies Used
- Programming Language: Java 17
- Web Framework: Spring Boot 3.2.5
- Database: MySql 

## Prerequisites

Before you begin, ensure you have met the following requirements:
- **Java**: You need Java 17 installed on your machine. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/?variant=openjdk17&jvmVariant=hotspot).
- **Maven**: Ensure Maven is installed. You can download it from [Apache Maven](https://maven.apache.org/download.cgi). Maven 3.6 or higher is recommended.
- **Git** (optional): For cloning the repository, though you can also download the project as a ZIP file.

## Getting Started

These instructions will get your copy of the project up and running on your local machine for development and testing purposes.

### Installation

1. **Clone the repository**

   ```bash
   git clone git@github.com:Mislav0508/Java-test.git

2. **Install MySQL. Ensure that MySQL is installed on your machine. If not, download it from [MySQL](https://dev.mysql.com/downloads/mysql/) and follow the installation instructions**

- Windows: Use the MySQL installer and follow the wizard.
- Mac: You can use Homebrew by running brew install mysql.
- Linux: Use your distribution’s package manager, e.g., sudo apt-get install mysql-server for Ubuntu.

3. **Configure MySQLStart the MySQL server and ensure it is running on port 3306. You may need to check your MySQL server’s configuration file (usually named my.cnf or my.ini):**


4. **Create a new database**

    ```bash
    CREATE DATABASE java-test;
    USE java-test;
   
5. **Configure your application. Update the src/main/resources/application.properties file with the database connection details:**

    ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/java-test?useUnicode=true&characterEncoding=UTF-8
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
   
6. **Navigate to the project directory where the pom.xml file is located:**
        
    ```bash
    cd path/to/your/project

7. **Build the project. Use Maven to build the project:**

    ```bash
    mvn clean install

8. **Run the Spring Boot application:**

    ```bash
    mvn spring-boot:run

9. **Accessing the Application:**

    ```bash
   Access REST endpoints: 
   Use the base URL http://localhost:8080/api/users 
   For detailed info go to:
   http://localhost:8080/swagger-ui/index.html
   
10. **Testing: using an Integrated Development Environment (IDE)**

    ```bash
    1. Open Your Project: Start by opening your Spring Boot project in your IDE.
    
    2. Navigate to Test Classes: 
    Go to the src/test/java directory where the test classes are located.
    There are: UserControllerTest, UserRepositoryTest, and UserServiceTest.
    
    3. Run Individual Test Class:
    
    IntelliJ IDEA/Eclipse: Right-click on a test class file or a test method and select Run 'TestName'. 
    This will execute either the single test method or all tests in the given class.
    
    VS Code: Open the test class file, 
    hover over the @Test annotation, and you'll see a play button appear
    which lets you run or debug the test directly from the editor.
    
    4. Run All Tests in the Project:

    IntelliJ IDEA/Eclipse: You can also right-click on the project or the src/test/java directory 
    and select Run All Tests to execute all tests in the project.

### Additional Notes:

- **Logging**: 

    Additional logging to the filesystem can be enabled by specifying the path in LogWriterServiceImpl.
    
    Example: 

  private static final String PATH_TO_LOG = "C:\\Users\\user\\Desktop\\java-test-errors\\errors.txt";

- **Using Docker**: 

  After cloning the project locally, go to the root of the project
  and run:
  docker-compose up
  * make sure port 3306 is available before running the project.
