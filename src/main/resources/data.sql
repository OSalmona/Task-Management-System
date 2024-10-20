DROP TABLE task;
DROP TABLE task_status;
DROP TABLE task_priority;
DROP TABLE _user;

create table _user (id bigint generated by default as identity,
        email varchar(255),
        firstname varchar(255),
        lastname varchar(255),
        password varchar(255),
        role enum ('ADMIN','USER'),
        primary key (id));

create table task (id bigint generated by default as identity,
        created_on date, description varchar(255),
        due_date date, title varchar(255),
        updated_on date,
        priority_id bigint,
        status_id bigint,
        user_id bigint,
        primary key (id));

create table task_priority (id bigint generated by default as identity,
        name varchar(255),
        primary key (id));

create table task_status (id bigint generated by default as identity,
        name varchar(255),
        primary key (id));

alter table if exists task
add constraint priority_FK foreign key (priority_id) references task_priority ;

alter table if exists task
add constraint status_FK foreign key (status_id) references task_status;

alter table if exists task
add constraint user_FK foreign key (user_id) references _user ;

insert into task_status (name) values ('to do');
insert into task_status (name) values ('in progress');
insert into task_status (name) values ('done');

insert into TASK_PRIORITY  (name) values ('low');
insert into TASK_PRIORITY (name) values ('medium');
insert into TASK_PRIORITY (name) values ('high');

insert into _user (email , firstname , lastname , password , role)
values ('1@admin.com','osama','admin','$2a$10$gHoMV276xOurYT9Gv7uNIO837VewOVtFG4snb2B27SEKn6TmUpVk6','ADMIN');
insert into _user (email , firstname , lastname , password , role)
values ('1@user.com','osama','user','$2a$10$gHoMV276xOurYT9Gv7uNIO837VewOVtFG4snb2B27SEKn6TmUpVk6','USER');
insert into _user (email , firstname , lastname , password , role)
values ('2@user.com','osama','user','$2a$10$gHoMV276xOurYT9Gv7uNIO837VewOVtFG4snb2B27SEKn6TmUpVk6','USER');
--password : 12345

insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 1' , 'description 1' , 1 , 1 , '2018-10-20' , '2018-10-20', 2);
insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 2' , 'description 1' , 2 , 2 , '2018-11-20' , '2018-10-20', 2);
insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 3' , 'description 1' , 3 , 3 , '2018-12-20' , '2018-10-20', 2);
insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 4' , 'description 1' , 1 , 3 , '2019-10-20' , '2019-10-20', 2);

insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 5' , 'description 1' , 3 , 3 , '2018-12-20' , '2018-10-20', 3);
insert into TASK (title , description , status_id , priority_id , due_Date , created_On  , user_id)
values ('task 6' , 'description 1' , 1 , 3 , '2019-10-20' , '2019-10-20', 3);