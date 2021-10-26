insert into TBL_USER(id,email,user_name,address,password) values(1000,'vaibhav@gmail.com','vaibhav','hyderabad','$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi');
insert into TBL_USER(id,email,user_name,address,password) values(1001,'prinsee@gmail.com','prinsee','chirmiri','$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi');
insert into TBL_USER(id,email,user_name,address,password) values(1002,'anshul@gmail.com','anshul','chandigarh','$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi');

insert into TBL_ROLE(id,name,description) values(1,'ADMIN',null);
insert into TBL_ROLE(id,name,description) values(2,'USER',null);

insert into USER_ROLE(user_id,role_id) values(1000,1);
insert into USER_ROLE(user_id,role_id) values(1001,1);
insert into USER_ROLE(user_id,role_id) values(1002,2);

insert into TBL_COURSE(id,name,description) values(2000,'Spring-Boot','Learn Spring Boot with real time projects');
insert into TBL_COURSE(id,name,description) values(2001,'Micro Services','Learn Micro Services using Spring Boot with real time projects');
insert into TBL_COURSE(id,name,description) values(2002,'Core Java','Learn Core Java');

insert into USER_COURSE(user_id,course_id) values(1002,2002);