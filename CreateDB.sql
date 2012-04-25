--2.	״̬��״̬��š�״̬���ƣ�
create table state(
	id int ,
	name varchar(10),
	primary key(id)
);

--4.	Ȩ�ޱ�Ȩ�ޱ�š�Ȩ�����ƣ�
create table permission(
	id int,
	name varchar(50),
	primary key(id)
);

insert into state values(1,"online"),(2,"offline"),(3,"hide");
insert into permission values(1,"none"),(2,"hideisvisual"),(3,"onlineisinvisible");
--1.	�û�������š��û����֡�����ǩ�������롢�Ա�(0=man 1=woman)��״̬��ע�����ڡ����ա���ַ��
create table chatuser(
	id int ,
	name  varchar(50),
	pswd  varchar(20),
	address varchar(100),
	birthday date,
	signature varchar(255),
	sex int,
	state int,
	registerday date,
	primary key(id),
	foreign key(state) references state(id)
);

insert into chatuser values(0,"god","11","XD","1988-03-12","lihailinisgod!",0,2,"2012-04-22");
insert into chatuser values(1,"author","11","XD","1988-03-12","lihailinisauther!",0,2,"2012-04-22");
--6.	�����¼����¼��š������ߡ������ߡ����ݡ�ʱ�䡢ͼƬ������
create table info(
	id int AUTO_INCREMENT,
	fromid int,
	toid int,
	content varchar(255),
	sendtime datetime,
	photonum int,
	primary key(id),
	index(id,toid),
	foreign key(fromid) references chatuser(id),
	foreign key(toid) references chatuser(id),
	check(fromid<>toid)
);
insert into info values(1,0,1,"helloworld!","2008-12-26 23:23:55",0);
insert into info(fromid,toid,content,sendtime,photonum) 
values(0,1,"helloworld!","2008-12-26 23:23:55",0);
--8.	�������������š������ߡ������ߡ�������Ϣ��ʱ�䣩��̬��
create table askfrd(
	id int AUTO_INCREMENT,
	fromid int,
	toid int,
	msg varchar(255),
	asktime datetime,
	primary key(id),
	foreign key(fromid) references chatuser(id),
	foreign key(toid) references chatuser(id),
	check(fromid<>toid)
);
insert into askfrd(fromid,toid,msg,asktime) values(1,0,"i'myou!","2012-04-12 12:12:22");
select * from askfrd where toid=0;
--7.	���Լ�¼�������¼��š������ߣ������Էŵ��ϱ��У���ôÿ����¼Ҫ��һ���ֶΣ����ڶ�̬��������
create table msgleft(
	infoid int,
	touserid int,
	foreign key(infoid,touserid) references info(id,toid),
	primary key(infoid)
);
insert into msgleft values(1,0);
select fromid,toid,sendtime,content from info,msgleft where infoid=id and toid=1;
--5.	������û���š��������ƣ�
create table frdgroup(
	userid int,
	name varchar(50),
	primary key(userid,name),
	foreign key(userid) references chatuser(id)
);
insert into frdgroup values(0,'friend'),(1,'friend');
update frdgroup set name='friend' where userid=1 and name='closefriend';
--3.	���ѱ����˱�š����ѱ�š��������ơ���ע��Ȩ�ޱ�ţ�
create table frdlist(
	myid int ,
	frdid int,
	ingroup varchar(50),
	remark varchar(50),
	permissionid int ,
	primary key(myid,frdid),
	foreign key(myid) references chatuser(id),
	foreign key(frdid) references chatuser(id),
	foreign key(myid,ingroup) references frdgroup(userid,name) 
	                          on update cascade,
	foreign key(permissionid) references permission(id)
);
insert into frdlist values(0,1,'friend','isme',1),(1,0,'friend','ismetoo',1);
update frdlist set remark='secondme' where myid=0;
select frdid,name,remark,signature,state,ingroup from chatuser,frdlist where myid=0 and frdid=id;