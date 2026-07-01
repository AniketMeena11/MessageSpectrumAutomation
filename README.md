# MessageSpectrumAutomation

## Overview

**MessageSpectrumAutomation** is a Selenium-based automation testing project developed to automate high-priority test scenarios for the Message Spectrum application.

The objective of this project was to identify test cases based on their business priority, automate the feasible ones, and generate detailed execution reports. Before automation, manual test cases were created and prioritized to ensure that the most critical functionalities were covered first.

---

## Project Objective

- Create and prioritize manual test cases.
- Automate high-priority test scenarios.
- Follow industry-standard automation framework practices.
- Generate detailed execution reports.
- Improve regression testing efficiency.

---

## Tech Stack

- **Language:** Java
- **Automation Tool:** Selenium WebDriver
- **Build Tool:** Maven
- **Testing Framework:** TestNG
- **Design Pattern:** Page Object Model (POM)
- **Reporting:** Extent Reports

---

## Framework Structure

```
MessageSpectrumAutomation
│
├── src
│   ├── main
│   │   ├── base
│   │   ├── pages
│   │   └── utilities
│   │
│   └── test
│       ├── tests
│       └── resources
│
├── pom.xml
├── testng.xml
└── README.md
```

---

## Automation Approach

The automation process followed these steps:

1. Understand the application's functionality.
2. Create manual test cases.
3. Assign priority to each test case.
4. Select automation candidates based on priority.
5. Develop reusable Page Object Model classes.
6. Execute test cases using TestNG.
7. Generate execution reports using Extent Reports.

---

## Challenges Faced

One of the major challenges during automation was handling **CAPTCHA**.

- The production application includes CAPTCHA verification.
- CAPTCHA cannot be reliably automated using Selenium because it is designed to prevent automated interactions.
- During testing in the staging environment, CAPTCHA was disabled, allowing the automated test cases to execute successfully.
- As a result, some production-specific scenarios cannot be fully automated without additional support from the application.

---

## Test Execution Summary

```
===============================================
Default Test
Tests Run : 13
Passed    : 9
Failed    : 4
Skipped   : 0
===============================================

===============================================
Overall Execution Summary
Total Tests : 13
Passed      : 9
Failed      : 4
Skipped     : 0
===============================================
```

---

## Features

- Selenium WebDriver automation
- Page Object Model (POM)
- TestNG execution
- Maven dependency management
- Extent Report integration
- Reusable utility classes
- Screenshot capture on failures
- Configurable test execution

---

## Future Improvements

- Improve test coverage.
- Automate additional medium- and low-priority test cases.
- Integrate with Jenkins for Continuous Integration.
- Execute tests on Selenium Grid.
- Support cross-browser execution.
- Integrate with GitHub Actions.
- Add Docker support.

---

## Author

**Aniket Meena**

Automation Testing | Java | Selenium | TestNG | Maven | Git | GitHub
