# ChatApp
ChatApp is a application that simulates a chat. This application has 2 screens. First screen has a widget to enter a name and enter the chat. Second screen has widget to enter messages and widget to view messages.

![1](https://github.com/Dmitry-Zv/ChatApp/assets/70663257/4c2a23e0-9c22-46d1-a1f2-7ea5c6e201cd) ![2](https://github.com/Dmitry-Zv/ChatApp/assets/70663257/eaaae6e0-6dfa-4c67-97d8-683d62996609)

# Technical description
The technology stack includes Android Jetpack components (ViewModel, Jetpack Compose). The Ktor library is used for work with server. The WorkManager library is used to receive updates from the server once a day. The dependency inversion principle was used by the Dagger Hilt framework.
The server was written using the ktor framework (Server - https://github.com/Dmitry-Zv/ChatServer). For saving messages in database used mongoDb.

