<p align="center">
    <h1 align="center">Task Management System</h1>
</p>
<p align="center">
    <em> 📖 This repo is for implement simple spring application to manage tasks by two type of users [regular user / admin]</em>
  </br>
</p>

---

## 📍 Project Features
- Register / login (authentication using jwt)
- regular user have permission
    - create task
    - update task
    - delete task
    - get all task related to him
    - search and filter by any task criteria (id / title / description / due date)
- radmin have permission
    - update any task
    - get all tasks in the system
    - search and filter by any task criteria (id / title / description / due date / user_id)
- send periodic remainder email for user before 24h on her task duedate using SMTP gmail
- Pagination approach in search and filter end points

---

## 📍 Dataلاase Entitites
![image](https://github.com/user-attachments/assets/76fbc284-e8d1-48f9-99f9-8612d8915b9a)
</br>
- Task
- Task_Status
- Task_Priority
- User


## 📍 Project Properties
- Database : H2 database
- Server Port : 8080
- spring.h2.console.path=/h2-console
- spring.datasource.url=jdbc:h2:mem:banquemisrDB
- spring.datasource.password=
- deafult users :
  </br>
  1- email: 1@admin.com -> password : 12345
  </br>
  2- email: 1@user.com  ->  password : 12345
  </br>
  3- email: 2@user.com  -> password : 12345
  </br>

---

## 📍 End Points APIs
![image](https://github.com/user-attachments/assets/617c1890-b8a0-4480-8be0-5dea70e16b26)

---


## 🔗 Quick Links 
- h2 databae : http://localhost:8080/h2-console
- swagger-ui : http://localhost:8080/swagger-ui/index.html

