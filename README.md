# ![ServerApp](https://img.icons8.com/ios-filled/50/000000/server.png) ServerApp README

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation and Setup](#installation-and-setup)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [File Logging](#file-logging)
- [Code Structure](#code-structure)
- [Technologies Used](#technologies-used)
- [Future Enhancements](#future-enhancements)

---

## Introduction
The **ServerApp** is a simple JavaFX-based desktop application that simulates a server connection workflow. It provides a multi-stage user interface where users can log in, connect to a server, change input text to uppercase, and view server communications in a console log. The application logs all user interactions in a local file for auditing and review purposes.

---

## Features
- ![Authentication](https://img.icons8.com/ios-filled/50/000000/user.png) **User Authentication**: Login system with predefined credentials.
- ![Server](https://img.icons8.com/ios-filled/50/000000/cloud.png) **Server Connection**: Users can request to connect to a simulated server.
- ![Text](https://img.icons8.com/ios-filled/50/000000/text.png) **Text Processing**: Allows users to enter text, which is converted to uppercase.
- ![Console](https://img.icons8.com/ios-filled/50/000000/console.png) **Console Log**: Displays user input, server responses, and application state changes.
- ![File Logging](https://img.icons8.com/ios-filled/50/000000/file.png) **File Logging**: Logs all user interactions and system messages in a `user_logs.txt` file.

---

## Installation and Setup

### Requirements:
- Java Development Kit (JDK) 8 or higher.
- JavaFX SDK (comes prepackaged with JDK 11+).

### Download and Installation:
1. Clone or download the source code from the repository.
2. Ensure that the `ServerApp.java` file is placed in the application package.

### Project Structure:

---

## How to Run

### Open the Project:
- Import the project into an IDE like IntelliJ IDEA or Eclipse.

### Run the Application:
- Execute the `ServerApp.java` file to start the program.

---

## Usage

### Login
- Enter the following credentials:
  - Username: `aran` or `rashmit`
  - Password: `0000`

### Server Connection
- Respond to the question: **"Would you like to connect to a server? (y/n)"**
  - Enter `y` to connect to the server or `n` to exit the application.

### Text Processing
- If connected, you will be prompted to convert text to uppercase.
  - Enter `y` to proceed or `n` to return to the server connection prompt.
  - If `y` is selected, input any text to be converted to uppercase. The system will split the text into words, send them as packets, and display the server's response.

### Exit
- Enter `n` at any prompt to exit the application.

---

## File Logging
All user actions and system responses are logged in the `user_logs.txt` file. This file tracks the following events:
- User input and server responses.
- User authentication attempts.
- Text processing actions.
- Application start, stop, and exit events.

Example log snippet:
---

## Code Structure

### Main Classes
- **ServerApp**: Main class that handles user authentication, UI rendering, server communication, and logging.
- **Server**: Simulates server processing and handles packet processing (not shown in the given code).

### Key Methods
- **start(Stage primaryStage)**: Sets up the login interface and handles user authentication.
- **showServerScene()**: Transitions to the main server interaction screen.
- **handleSubmission()**: Handles user inputs at each stage of the application (connect, change text, enter text).
- **logToFile(String logMessage, boolean append)**: Logs user and system messages to `user_logs.txt`.

### Application States
- **LOGIN**: Initial state where users enter their credentials.
- **CONNECT_TO_SERVER**: Users choose whether to connect to the server.
- **CHANGE_TEXT**: Users decide whether to convert text to uppercase.
- **ENTER_TEXT**: Users enter the text to be converted.

---

## Technologies Used
- ![JavaFX](https://img.icons8.com/ios-filled/50/000000/java.png) **JavaFX**: Used for the user interface and scene transitions.
- ![Java IO](https://img.icons8.com/ios-filled/50/000000/folder-invoices.png) **Java I/O**: Used to log user actions and server interactions to a file.
- ![BufferedWriter](https://img.icons8.com/ios-filled/50/000000/write.png) **BufferedWriter**: For file-based logging.

---

## Future Enhancements
- **Dynamic User Authentication**: Replace static user credentials with a database or file-based authentication.
- **Server Integration**: Replace the mock server with an actual server-client connection.
- **Input Validation**: Provide more robust input validation to prevent invalid inputs.
- **Enhanced Logging**: Add timestamps to log entries to improve traceability.
- **Improved UI Design**: Modernize the UI with better design and user experience elements.

---

This README provides a comprehensive guide on how to set up, run, and use the **ServerApp**, as well as details on its features, file logging, and potential future enhancements.
