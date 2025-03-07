# Dream Journal

A Kotlin-based Android app that integrates the Gemini API, allowing users to create, save, and analyze their dream journal entries. The app features a loading screen, home screen, and an entry screen. Users can edit their journal entries or have the Gemini large language model (LLM) analyze their dreams for insights. All journal entries are stored locally using Room Database for persistent storage.

## Features

* Home Screen: Displays a list of saved journal entries with options to view or edit.
* Entry Screen: Allows users to write or edit journal entries. Optionally, the Gemini LLM can analyze the content of a dream.
* Loading Screen: Provides a smooth user experience while the app loads.
* Room Database: Journals are stored locally using Room Database, enabling offline access to past entries.

## Technology Stack

* Kotlin: The primary programming language for the app.
* Gemini API: Integrated to analyze and provide insights from journal entries, specifically focused on dreams.
* Room Database: For storing journal entries locally in a structured database.
* Jetpack Compose: Used for building modern, responsive UI components.
* ViewModel & LiveData: For managing UI-related data lifecycle-consciously.

## How to Use

* Create a New Entry: On the home screen, tap the "New Entry" button to begin writing a new journal entry.
* Edit an Existing Entry: Tap on any saved entry to open and modify it.
* Analyze a Dream: On the entry screen, you can choose to have the Gemini LLM analyze your dream by tapping the "Analyze Dream" button.

## Installation

Clone the repository:
``bash
git clone https://github.com/itsnotleilani/Dream-Journal.git
``

Open the project in Android Studio.

Install any required dependencies by syncing the project with Gradle.

Set up the Gemini API:

Follow the instructions provided by Gemini to obtain your API key.

Set up the API key in the app by adding it to the appropriate configuration file.

The Room Database will automatically set up when the app is first run, and it will store journal entries locally.

Run the app on an emulator or a physical Android device.


<img width="1285" alt="Screenshot 2025-03-06 at 8 55 58 PM" src="https://github.com/user-attachments/assets/384e3da6-a43f-4eea-a527-d7056948b0cb" />

<img width="1318" alt="Screenshot 2025-03-06 at 8 57 15 PM" src="https://github.com/user-attachments/assets/0b0e5a8d-d77c-4bd5-93e8-e44102f50926" />

<img width="1326" alt="Screenshot 2025-03-06 at 8 57 46 PM" src="https://github.com/user-attachments/assets/1733b36c-9eaa-4a6a-83ef-1256d04873d8" />
