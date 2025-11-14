# Android Clean Architecture

This sample demonstrates a master-detail implementation following clean architecture principles,
using the MVVM pattern for Android development.

## Tech Stack

- Kotlin Programming Language
- Kotlin Coroutines & Flow for Asynchronous Programming
- Jetpack Compose for UI
- Room for Local Database
- Retrofit for Network Calls
- Koin for Dependency Injection
- Coil for Image Loading
- Junit & Mockito for Unit Tests
- Espresso for UI Tests

## Coroutines: `suspend` Functions vs `Flow` in Clean Architecture

When designing your Android app with Clean Architecture, it's important to choose the right function
types for each layer.

- `suspend` → for functions that perform one-time asynchronous operations (e.g., network requests or
  single database queries).

- `Flow` → for functions that emit streams of values over time. No suspend is needed at the
  declaration level, since Flow is cold and only executes when collected.

Below are guidelines on when to use `suspend` functions versus `Flow` in
different layers of your architecture:

| Layer                | Typical Function Type                     | Why                                          |
|----------------------|-------------------------------------------|----------------------------------------------|
| **ViewModel**        | Calls suspend or collects Flow            | Should launch coroutines, not suspend UI     |
| **UseCase**          | `suspend` or `Flow`                       | Represents one action (one-shot or reactive) |
| **Repository**       | `suspend` or `Flow`                       | Abstracts data sources; can be either        |
| **RemoteDataSource** | `suspend`                                 | Network calls are one-shot                   |
| **LocalDataSource**  | `Flow` for reading, `suspend` for writing | DB streams vs. disk writes                   |
