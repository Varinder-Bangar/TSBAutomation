# TSBAutomation
## Trade Me Sandbox — UI & API Test Automation Framework

## About This Framework

This is a test automation framework built for the TSB Senior QA Engineer
assignment. It covers UI automation for the Trade Me Sandbox homepage and
search functionality, and API automation for the Trade Me categories endpoint.

### Tech Stack

| Tool          | Version  | Purpose                         |
|---------------|----------|---------------------------------|
| Java          | 17 LTS   | Programming language            |
| Selenium      | 4.18.1   | UI automation                   |
| REST Assured  | 5.4.0    | API automation                  |
| TestNG        | 7.9.0    | Test framework                  |
| Maven         | 3.9.x    | Build and dependency management |
| ExtentReports | 5.1.1    | HTML test reporting             |
| Awaitility    | 4.2.0    | Async waiting                   |
| Chrome        | Latest   | Browser under test              |

---

## Prerequisites

Before running the tests, ensure you have the following installed:

### 1. Java 17 LTS

Verify installation:
```bash
java -version
```
Expected output:
java version "17" 2021-09-14 LTS

**Mac (Homebrew):**
```bash
brew install openjdk@17
```
**Windows**: 
Download from https://www.oracle.com/java/technologies/downloads/#java17

---

### 2. Maven 3.9+

Verify installation:
```bash
mvn -version
```

Expected output:
Apache Maven 3.9.x

**Mac (Homebrew):**
```bash
brew install maven
```

**Windows:**
Download from https://maven.apache.org/download.cgi

---

### 3. Google Chrome

Latest version of Chrome is required.
Selenium Manager handles ChromeDriver automatically. No need to download driver manually.

---

## Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/YourUsername/tsb-automation-assignment.git
```

### 2. Navigate to project

```bash
cd tsb-automation-assignment
```

### 3. Install dependencies

```bash
mvn clean install -DskipTests
```

This downloads all required dependencies automatically.

---

## Running the Tests

### Run Smoke Suite
Quick health check — verifies core functionality:
```bash
mvn clean test
```

### Run Regression Suite
Full test coverage including search functionality:
```bash
mvn clean test -DtestSuite=testsuite/regression.xml
```

### Run All Suites
```bash
mvn clean test
```

---


## Test Cases

Test cases are written in markdown files and can be found in the root directory **testcases**.

## Test Reports

Reports are automatically generated after each run.

### ExtentReports HTML Report
Located at: test-reports/report_TSB_timestamp/ExtentReport.html
Failure Screenshots: test-reports/report_TSB_timestamp/screenshots
