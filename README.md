# SmartHR (💼 HR Management System)

SmartHR is a complete **HR management web application** built using Spring Boot, MySQL, and Thymeleaf. It provides features like employee management, attendance tracking, leave management, a dedicated employee dashboard, profile creation, custom login, an admin dashboard, and role-based access — all without using Spring Security!

---

## 🚀 Key Features

- ✅ Add, update, view, and delete employee records
- ✅ Track **employee attendance**
- ✅ Manage **employee leave requests**
- ✅ **Employee Dashboard** — where employees can:
  - 👤 View personal details
  - 🕒 Mark daily attendance
  - 📝 Submit leave requests
  - 💼 **Create and update personal profiles**
- ✅ **Create Account Page** for employees to register and create their profiles
- ✅ **Admin Dashboard** — for managing employees, attendance, and leave
- ✅ Persistent data storage using Spring Data JPA and MySQL
- ✅ Responsive UI using Thymeleaf and Bootstrap
- ✅ **Custom authentication system** (without Spring Security)
- ✅ **Role-based access** for Admin and User

---

## 🧰 Tech Stack

| Layer             | Technology         |
|------------------|--------------------|
| Language          | Java               |
| Framework         | Spring Boot        |
| Web Layer         | Spring MVC         |
| Template Engine   | Thymeleaf          |
| Styling & Layout  | Bootstrap (CSS)    |
| Authentication    | Custom login (no Spring Security) |
| Role Access       | Session-based Admin/User roles |
| Persistence Layer | Spring Data JPA    |
| Database          | MySQL              |
| Build Tool        | Maven              |
| IDE               | Eclipse            |

---

## 🔐 Custom Authentication & Role Management

This project includes:

- Manual login authentication via MySQL
- Session management
- Role-based access (Admin/User)
- Redirects and access control logic based on role
- Logout functionality

---

## 🧑‍💻 Employee Profile Creation

- **Create Account Page** allows employees to register by providing personal details (e.g., name, email, password).
- After registration, employees can access their **profile page** and update their personal information.
- This **Create Account** feature is designed to easily onboard new employees to the system.

---

## 🛠️ How to Run (Locally)

1. **Clone the Repo**
   ```bash
   git clone https://github.com/kartik-sonar123/SmartHR.git
