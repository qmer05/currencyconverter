# Currency Converter

## Overview
Currency Converter is a JavaFX application that allows users to convert amounts between different currencies using the latest exchange rates. The application fetches the latest rates from valutakurser.dk and provides an intuitive interface for users to select currencies, input amounts, and view converted values.

## Features
Convert amounts between various currencies
Display corresponding flag images for selected currencies
Fetch live exchange rates from the internet
User-friendly interface with JavaFX
Confirmation dialog before exiting the application
Error handling and alert messages for common issues

## Technologies Used 
Java 21
JavaFX 21
OkHttp 4.12.0 (for HTTP requests)
Jsoup 1.18.1 (for web scraping)
Maven (for project management and build automation)

## Dependencies
Dependencies are managed through Maven. The key dependencies used in this project are:

JavaFX Controls
JUnit 5 (for testing)
Jsoup (for web scraping)
OkHttp (for HTTP requests)
Refer to pom.xml for the complete list of dependencies.

## Installation
Clone the repository:
git clone https://github.com/yourusername/currency-converter.git
cd currency-converter

Download and set up JavaFX:
Ensure JavaFX is installed and properly configured in your environment. You can download it from the official website.

Download and set up OkHttp3:
Add OkHttp3 dependency in your build configuration (e.g., pom.xml for Maven or build.gradle for Gradle).

Download and set up JSoup:
Add JSoup dependency in your build configuration.

## Running the Application
Compile the project:
javac -cp "path/to/javafx/lib/*;path/to/okhttp3.jar;path/to/jsoup.jar" -d bin src/app/currencyconverter/*.java

Run the application:
java -cp "bin;path/to/javafx/lib/*;path/to/okhttp3.jar;path/to/jsoup.jar" app.currencyconverter.Main

## Usage
Launch the application.
Select the source currency from the "From currency" dropdown menu.
Select the target currency from the "To currency" dropdown menu.
Enter the amount to convert in the "Amount" text field.
Click the "Convert" button to see the converted amount.
Exit the application by clicking the "Exit" button. A confirmation prompt will appear to prevent accidental closure.
