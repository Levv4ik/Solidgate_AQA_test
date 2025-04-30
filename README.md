# Solidgate_AQA_test

# Automated UI & API Testing Project

This project provides automated testing for a payment page and related backend API using **Selenium** and **Rest Assured**. The framework supports both UI and API tests to ensure functional integrity of the payment workflow.

## 🔧 Technologies Used

- Java 17+
- Maven
- Selenium WebDriver
- Rest Assured
- JUnit 5
- Jackson (for JSON parsing)
- Lombok (for model generation)
- WebDriverManager (for automatic driver handling)

## 📁 Project Structure


## 🚀 Running the Tests

### 1. Set required environment variables

These are used for API authentication.

#### On **Windows (PowerShell)**:

```powershell
$env:PUBLIC_KEY="your_public_key_here"
$env:SECRET_KEY="your_secret_key_here"


mvn clean test
