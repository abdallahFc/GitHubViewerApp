# GitHub Viewer App

The GitHub Repo Viewer App is an Android application developed using Kotlin and Jetpack Compose. This app allows users to view repositories from GitHub, showcasing various modern Android development practices and technologies.

## Features

- Fetches repository data from the GitHub API using Retrofit.
- Displays a list of GitHub repositories on the main screen.
- Displays detailed information about a selected repository.
- Shows a list of issues for a selected repository.
- Follows the MVVM architectural pattern.
- Utilizes Coroutines for asynchronous operations.
- Caches fetched repository data using Room Database.
- Implements unit tests to ensure ViewModel and database functionality.
- Provides error handling and UI feedback for network errors and empty responses.
- Designed with a user-friendly interface using Jetpack Compose.
- Supports both light and dark modes.

## API Integration

The app integrates with the GitHub API to fetch repository data. It uses Retrofit for making API calls and handling responses. The following API endpoints are utilized:

- List Repositories: `/repositories`
- Get Repository Details: `/repos/{owner}/{repo_name}`
- List Issues for a Repository: `/repos/{owner}/{repo}/issues`

## Screens

### Main Screen - Repository List

The main screen displays a list of GitHub repositories. Each repository item includes the repository's name, owner, description, and star count.

### Repository Detail Screen

The repository detail screen shows detailed information about a selected repository. 

### Issues Screen

The issues screen lists the issues for a selected repository. Each issue item displays the issue's title, author, date, and state (open/closed).

## Architecture

The app follows the MVVM architectural pattern:

- **Model**: Represents the data and business logic. Includes data classes for repositories and issues.
- **ViewModel**: Manages UI-related data and interactions. Utilizes LiveData/State for communication with the UI.
- **View**: The UI components created using Jetpack Compose.

## Coroutines and Room Database

Coroutines are used for managing asynchronous operations, such as fetching data from the GitHub API and interacting with the Room Database. Room Database is employed for caching fetched repository data locally.

## Unit Testing

The app includes unit tests to ensure the correctness of ViewModel logic. It mocks data sources and uses testing Coroutines to verify ViewModel behavior. Additionally, database operations are tested to ensure the functionality related to cached data.

## Error Handling

Appropriate error handling is implemented to handle scenarios such as network errors or empty responses from the API. Users receive feedback through the UI when such errors occur.

## UI and User Experience

The app's UI is designed using Jetpack Compose to provide an attractive and user-friendly interface. Smooth transitions and animations enhance the overall user experience. The app also supports both light and dark modes for UI customization.

## Usage

To build and run the app, follow these steps:

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

