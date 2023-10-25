# Selenium TestNG Project

This is the Selenium project for automated tests on [Daft.ie](https://www.daft.ie/) website, as a part of the Technical round for the QA role.
the project focuses on running an end-to-end test case for a user visiting the website and searching for the ad.

Test cases for the project: [here](https://docs.google.com/spreadsheets/d/1W0QQWk4NRlN-g_NFQPikxX_GbvZBIkUrPQdN88XeF3o/edit?usp=sharing)
## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Configuration](#configuration)
- [Running Tests](#running-tests)

## Prerequisites

To run this project, you need the following prerequisites:

- Java (JDK 17 or higher)
- Selenium WebDriver
- TestNG
- Web browser drivers (e.g., ChromeDriver, GeckoDriver)
- IntelliJ IDE
- Maven

## Project Structure

Explain the project structure here, such as the directory layout and the purpose of each directory. For example:

- `src/test/java`: Contains the test packages where the test cases are written.
- `src/main/resources`: Contains resource files, including configuration files.
- `test-output`: This directory stores TestNG reports.

## Getting Started

These are the steps to set up the project:

### Clone the Repository

Clone this repository to your local machine:

```bash
git clone https://github.com/vikas-sp-97/selenium-test.git
```

### Configuration

Before running the tests, make sure you've configured the project:

1. **Update `testng.xml`**:
   
   Update the `testng.xml` file with relevant configuration details, such as the base URL and browser choice.

2. **WebDriver Executables**:

   Ensure that the necessary WebDriver executables (e.g., `chromedriver.exe`) are in your system PATH. Please note that the ChromeDriver version should match your Chrome browser version.

3. **Maven Dependencies**:

   Make sure all the Maven dependencies are resolved. You can ensure this by running a `mvn clean install` command to download and resolve the required dependencies.

### Running Tests

To run the tests in this Selenium TestNG project, you have several options:

1. **Test Runner Class**:

   - Open the `DaftTest` class.
   - Right-click on the class and select "Run as TestNG".

2. **Programmatic Execution**:

   - Right-click on the `testng.xml` file.
   - Select "Run".

3. **Command-Line Options**:

   If you prefer running tests via the CLI, follow these steps:

   - Open your terminal or command prompt.
   - Navigate to the project directory:
     ```bash
     cd <project directory>
     ```
   - Run the tests using Maven:
     ```bash
     mvn clean test
     ```

That's it! 
You have successfully run the test cases mentioned above.




