# The Chat App

The Chat App is an Android messaging prototype designed for focused 1-on-1 conversations. It emphasizes a clean user interface and reliable local message handling.

## Key Features

- **1-on-1 Chat**: Core messaging flow for direct user interaction.
- **Message History**: Persistent local storage ensuring your messages are always available.
- **Pagination**: Efficiently load messages in chunks for smooth scrolling and performance.
- **Conversation Control**: Easily clear chat history to start fresh.
- **Mocked bot replying**: Simulated responses to demonstrate conversation flow without a backend.

## Technologies & Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Local Persistence**: Room
- **Pagination**: Jetpack Paging
- **Image Loading**: Coil
- **Dependency Injection**: Koin
- **Minimum SDK**: Android 24 (API Level 24)
- **Target SDK**: Android 37

## Assumptions and Tradeoffs
## Tradeoffs with the current implementation
- **No networking** -> Faster implementation and easier local development, but no real-time server-backed communication
- **No group chat support** -> Simpler data model and UI, but limited to 1-on-1 conversations
- **No authentication** -> Easier onboarding and testing, but no user identity or security
- **No security/moderation layer** -> Reduced complexity, but no filtering of inappropriate content
- **Text-only messages** -> Simpler data and UI model, but no media richness
- **No reactions/reply/edit/delete/report** -> Cleaner initial domain model, but weaker conversation ergonomics
- **No notifications/deep links** -> Fewer integration points now, but reduced re-engagement and discoverability
- **No read markers** -> Less state management, but less delivery/read transparency
- **Simplified room ID strategy** -> Easy mapping logic, but may not scale cleanly to broader participant/chat scenarios

## Potential Future Enhancements
- **Images within chat**: Implement image sending and receiving to enhance message richness.
- **Reactions and message actions**: Add features like reactions, editing, deleting messages for better user interaction.
- **Expanded message details**: Include timestamps, read receipts, and delivery status for improved conversation context.
- **Group chat support**: Extend the app to support multi-user conversations, enhancing its utility.
- **Notifications and deep linking**: Implement push notifications and deep linking for better user engagement and navigation.
- **Security and moderation**: Introduce content filtering and moderation tools to ensure safe communication.
- **Backend integration**: Connect to a real-time backend service for live messaging and data synchronization.

## Note:
GitHub repository with Bitrise CI/CD. To test the app without app [here](https://app.bitrise.io/app/3868b8f0-60ef-4ef3-ab00-6c321d6b789f/installable-artifacts/df044ebb67e49e24/public-install-page/adcd32c3f8df8dd6236a883b72bdb819).


**Specs can be found [here](specs.pdf).**
