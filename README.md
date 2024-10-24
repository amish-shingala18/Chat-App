# 💬 Chat App

This **Chat Application** for Android enables users to register, log in using **Firebase Authentication** (Email/Password), and send real-time messages. Messages are stored in **Firestore Database**, which synchronizes chats across devices, providing an efficient, seamless experience.

## ✨ Features

- **🔑 User Authentication**:
  - Sign up and log in with email/password.
  
- **💬 Real-time Chat**:
  - Send and receive messages in real time.
  - Messages are instantly stored and synchronized using Firestore.

- **👤 Profile Management**:
  - View and manage user profiles with display names and email addresses.

- **👥 Chat with Users**:
  - View a list of users you’ve chatted with and continue conversations.

- **🔒 Secure Data**:
  - Messages and user data are stored securely in Firebase Firestore.
  - Authentication ensures that only registered users can access chats.

- **🎨 Attractive UI**:
  - Smooth user interface designed using **Material Design** principles.
  - Custom chat bubbles, sleek color schemes, and intuitive navigation.

## 📱 App Screens

### 1. **🚀 Splash Screen**
   - **Description**: Initial loading screen displayed when the app starts. Contains the app logo and name, with a quick transition to the login screen.
   - **Features**: App logo, loading animation.

### 2. **🔓 Login Option Screen**
   - **Description**: Users are prompted to either sign in or sign up using email/password.
   - **Features**: Two options — Sign In and Sign Up buttons.

### 3. **📝 Sign Up Screen**
   - **Description**: Allows users to register a new account by providing their email, password, and name.
   - **Features**: Form for email, password, and name input with validation.

### 4. **🔑 Sign In Screen**
   - **Description**: Users can log in using their registered email and password.
   - **Features**: Form for email and password.

### 5. **👤 Profile Screen**
   - **Description**: Displays the user's profile information such as name and email. The user can edit their profile or log out from this screen.
   - **Features**: Display and edit profile details.

### 6. **🏠 Main Screen**
   - **Description**: The primary screen where users can see a list of users they have previously chatted with.
   - **Features**: List of users with whom the user has had conversations, navigate to chat by tapping on a user.

### 7. **📜 All User Screen**
   - **Description**: Displays a list of all registered users in the app. Users can tap on a name to start chatting.
   - **Features**: View all registered users in the app and initiate a chat by selecting a user.

### 8. **💬 Chat Screen**
   - **Description**: The main chat interface where users can send and receive messages in real-time.
   - **Features**: 
     - Send text messages.
     - View chat history.
     - Display timestamps for each message.
     - Different chat bubbles for sender and receiver.
     - Real-time updates as new messages arrive.

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/c716823f-99f4-4d95-82db-a88a0b6aa3b0" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/8e87a942-87ee-44cc-bba4-f31a133393a0" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/5c5cd2f8-ec8b-4c42-b24a-4537730f3d8e" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/e088df58-d780-4f8a-8a5d-c01fa3d0ff3a" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/c7b4f047-acca-4b98-a6e6-8e9ed68ff939" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/0a46b54e-cdfa-4b3b-8596-a3c1867edd1f" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/c61ed16e-799c-4cfd-9a11-0f1e197178c3" width="200" height="400"/>
</p>

## 💻 Tech Stack

### **Frontend (Android)**:
- **🛠️ Programming Language**: Kotlin
- **🎨 UI/UX Design**: Material Design, Custom Views, XML Layouts
- **🏛️ Architecture**: MVVM (Model-View-ViewModel)
- **💧 Kotlin Flow**: For asynchronous data stream handling.
- **📄 RecyclerView**: For displaying chat messages and user lists.

### **Backend (Firebase)**:
- **🔐 Firebase Authentication**: For secure login and registration using email/password.
- **💾 Firebase Firestore**: For real-time storage and synchronization of chat messages.
- **☁️ Firebase Cloud Functions** (Optional): For backend logic like push notifications.

## 📄 Summary

This **Chat App** provides a modern and secure platform for users to communicate in real-time. Using **Firebase Authentication** for secure login and **Firestore** for instant message synchronization, the app ensures a smooth user experience across devices. Its **MVVM architecture** in Kotlin, combined with **Material Design**, offers a responsive and visually appealing interface. Users can sign up, log in, chat, and manage their profiles efficiently. Whether it's one-on-one chats or browsing all registered users, the app provides a robust, easy-to-navigate platform for seamless communication.



