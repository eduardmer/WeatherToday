# Today's Weather 🌤️

**Today's Weather** is a modern Android weather application built using clean architecture principles and Jetpack Compose for a fully declarative UI experience.

---

## 🧱 Architecture Overview

The app is structured using a **3-layered clean architecture**:

### 1. **Data Layer**
- Contains two data sources:
  - **Remote API** – Fetches current weather information.
  - **Proto DataStore** – Stores weather data locally when a successful API response is received.
- **DataStore is the single source of truth**. All UI reflects the last successful data stored.

### 2. **Domain Layer**
- Contains **use cases** that coordinate business logic.
- Includes **domain models** that are UI-independent and used across the app.

### 3. **UI Layer**
- Built with **Jetpack Compose**.
- Fully reactive and observes data from `StateFlow` exposed by the `ViewModel`.

---

## 🌅 Dynamic Background Feature

The app displays different background images based on the current time:

- **Day background**: Shown when the current time is **between sunrise and sunset**.
- **Night background**: Shown when the current time is **outside** the sunrise-sunset window.

This dynamic change enhances the user experience and visual clarity.

---

## 🔧 Tech Stack

- **Kotlin**
- **Jetpack Compose** – Modern UI Toolkit
- **Proto DataStore** – Local persistence and caching
- **Kotlin Coroutines & Flow** – For asynchronous and reactive data handling
- **Clean Architecture** – Separation of concerns and testability
- **MVI** – Presentation layer design

---

## 🚀 Features

- Fetch weather data using API by city name.
- Automatically caches data to Proto DataStore on success.
- Displays weather data from cache when app launches or offline.
- Dynamic day/night themed backgrounds.
- Testable architecture.

---

## 📦 Download APK

You can find the latest **APK file** in the [Releases section](https://github.com/eduardmer/WeatherToday/releases/tag/1.0) of this repository.
