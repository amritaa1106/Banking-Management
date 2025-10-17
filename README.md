# 🏦 Banking Management System

A simple yet powerful **Java Swing** desktop application for managing bank accounts 💳 — designed to make account handling fast, visual, and efficient! Data is stored securely in a **MySQL** database, connected via **JDBC**.

---

## ✨ Features

💡 **Add New Account Holders** – Quickly register new customers with full details.
📝 **Update Account Info** – Modify existing records easily.
❌ **Delete Accounts** – Remove inactive or test accounts with one click.
📊 **View Balances & Status** – Display all accounts neatly in a table view.
⚙️ **Test Database Connection** – Validate your MySQL setup right from the GUI!

---

## 🧠 Technologies Used

🟨 **Java Swing** – For a modern, interactive desktop interface
🗄️ **MySQL** – Reliable backend data storage
🔗 **JDBC (MySQL Connector)** – For smooth database connectivity

---

## 🚀 Setup Instructions

### 1️⃣ Clone or Download the Project

```bash
git clone https://github.com/YOUR_USERNAME/BankingManagementSystem.git
```

Or simply copy all `.java` and required files into a folder (e.g., `week7`).

### 2️⃣ Set Up the Database

* Create a MySQL database:

  ```sql
  CREATE DATABASE bankcontent;
  ```
* Add a table for storing accounts (you can customize the schema).

### 3️⃣ Add the JDBC Driver

Download `mysql-connector-j-9.3.0.jar` and place it in your project directory.

### 4️⃣ Compile the Code

```bash
javac week8.java
```

### 5️⃣ Run the Application

```bash
java -cp ".;mysql-connector-j-9.3.0.jar" week8
```

👉 *(Use `:` instead of `;` on Mac/Linux.)*

### 6️⃣ Configure Your Database Credentials

At the top of `week8.java`, update your MySQL details if needed:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bankcontent";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

---

## 🖼️ Screenshots

|                                                               💻 GUI Preview                                                              |
| :---------------------------------------------------------------------------------------------------------------------------------------: |
| <img width="1176" height="877" alt="Banking GUI" src="https://github.com/user-attachments/assets/010ecda6-9645-4f4e-a7f8-9574176cbfb5" /> |

---

## 📂 File Structure

```
week7/
├── week8.java
├── week8.class
├── mysql-connector-j-9.3.0.jar
└── ...
```

---

## 👩‍💻 Author

**Amrita Hariharan**
💬 Passionate about building reliable, scalable Java applications
🌱 Currently exploring full-stack development and AI-driven systems

If you liked this project, don’t forget to ⭐ it on GitHub!
Let’s make banking management simpler — one line of code at a time 💻💙

---







Author
Amrita Hariharan
