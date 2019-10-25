drop database if exists SuperHeroSightings;
create database SuperHeroSightings;
use SuperHeroSightings;


create table Powers(
Id int primary key auto_increment,
`Name` varchar(30) not null
);


create table Supes (
Id int primary key auto_increment, 
`Name` varchar(30) not null,
`Description` varchar(100) not null,
PowerId int not null,

Foreign Key fk_supes_powers(PowerId)
references Powers(Id)
);

create table Organizations(
Id int primary key auto_increment,
`Name` varchar(30) not null,
`Description` varchar(100) not null,
Address varchar(50) not null
);

create table Supes_Organizations(
SupesId int not null,
OrganizationsId int not null,

primary key pk_Supes_Organizations(SupesId, OrganizationsId),

foreign key fk_Supes_Organizations_Supes(SupesId)
references Supes(Id), 

foreign key fk_Supes_Organizations_Organization(OrganizationsId)
references Organizations(Id)
);

create table Locations(
Id int primary key auto_increment,
`Name` varchar(30) not null,
Address varchar(50) not null,
Latitude decimal(9,6),
Longitude decimal(9,6)
);

create table Sightings(
Id int primary key auto_increment,
SightingDate varchar(10) not null,
LocationId int not null, 

foreign key fk_sightings_locations(LocationId)
references Locations(Id), 

SuperId int not null, 

foreign key fk_sightings_supes(SuperId)
references Supes(Id)

);


insert into Powers (Id, `Name`)
values ('201','Power 1'),
('202','Power 2'),
('203','Power 3'),
('204','Power 4'),
('205','Power 5'),
('206','Power 6'),
('207','Power 7'),
('208','Power 8');

insert into Supes(Id, `Name`, `Description`, PowerId)
values ('1001','Super 1','Super 1 description','201'),
('1002','Super 2','Super 2 description','202'),
('1003','Super 3','Super 3 description','203'),
('1004','Super 4','Super 4 description','204'),
('1005','Super 5','Super 5 description','205'),
('1006','Super 6','Super 6 description','206'),
('1007','Super 7','Super 7 description','207'),
('1008','Super 8','Super 8 description','208'),
('1009','Super 9','Super 9 description','201'),
('1010','Super 10','Super 10 description','202');

insert into Organizations(Id, `Name`,`Description`,Address)
values('301','Org 1 name','Org 1 description','Org 1 address'),
('302','Org 2 name','Org 2 description','Org 2 address'),
('303','Org 3 name','Org 3 description','Org 3 address'),
('304','Org 4 name','Org 4 description','Org 4 address');

insert into Supes_Organizations(SupesId,OrganizationsId)
values ('1001','301'),
('1002','301'),
('1003','301'),
('1004','301'),
('1005','302'),
('1006','302'),
('1007','302'),
('1008','302'),
('1009','302'),
('1010','302'),
('1001','303'),
('1005','303'),
('1009','303'),
('1010','303'),
('1002','304'),
('1008','304');

insert into Locations(Id,`Name`, Address, Latitude, Longitude)
values ('3001','Location 1 name','Location 1 address','60.684976','17.153166'),
('3002','Location 2 name','Location 2 address','15.657635','-85.99684'),
('3003','Location 3 name','Location 3 address','16.37937','102.36929'),
('3004','Location 4 name','Location 4 address','59.262542','17.978333'),
('3005','Location 5 name','Location 5 address','33.461435','9.02947'),
('3006','Location 6 name','Location 6 address','19.503769','-99.13242'),
('3007','Location 7 name','Location 7 address','-6.264907','106.75675'),
('3008','Location 8 name','Location 8 address','32.478018','117.16756'),
('3009','Location 9 name','Location 9 address','18.114529','121.40235'),
('3010','Location 10 name','Location 10 address','7.586604','0.608574'),
('3011','Location 11 name','Location 11 address','35.071102','115.57303'),
('3012','Location 12 name','Location 12 address','-31.3699','27.03523'),
('3013','Location 13 name','Location 13 address','39.91983','116.41571'),
('3014','Location 14 name','Location 14 address','18.399999','121.51667'),
('3015','Location 15 name','Location 15 address','18.792633','109.87814'),
('3016','Location 16 name','Location 16 address','56.262189','34.302827'),
('3017','Location 17 name','Location 17 address','37.940058','110.99388'),
('3018','Location 18 name','Location 18 address','28.34305','121.57248'),
('3019','Location 19 name','Location 19 address','20.72401','-97.53081'),
('3020','Location 20 name','Location 20 address','-8.4335','115.5012');

insert into Sightings(Id, SightingDate, LocationId,SuperId)
values('1','2019-02-15','3001','1001'),
('2','2019-10-10','3002','1002'),
('3','2018-11-10','3003','1003'),
('4','2019-02-06','3004','1004'),
('5','2019-05-25','3005','1005'),
('6','2019-05-02','3006','1006'),
('7','2019-09-17','3007','1007'),
('8','2019-07-11','3008','1008'),
('9','2019-07-01','3009','1009'),
('10','2019-02-07','3010','1010'),
('11','2019-06-17','3011','1001'),
('12','2018-12-15','3012','1002'),
('13','2019-02-24','3013','1003'),
('14','2019-10-02','3014','1004'),
('15','2019-05-06','3015','1005'),
('16','2019-03-31','3016','1006'),
('17','2019-07-25','3017','1007'),
('18','2019-05-19','3018','1008'),
('19','2019-01-04','3019','1009'),
('20','2019-08-23','3020','1010'),
('21','2019-07-30','3001','1001'),
('22','2018-12-20','3002','1002'),
('23','2019-04-09','3003','1003'),
('24','2019-01-14','3004','1004'),
('25','2019-03-20','3005','1005'),
('26','2019-10-09','3006','1006'),
('27','2019-09-23','3007','1007'),
('28','2019-02-22','3008','1008'),
('29','2019-07-26','3009','1009'),
('30','2019-08-18','3010','1010'),
('31','2019-08-28','3011','1001'),
('32','2019-08-16','3012','1002'),
('33','2019-03-20','3013','1003'),
('34','2019-04-30','3014','1004'),
('35','2019-07-17','3015','1005'),
('36','2019-08-18','3016','1006'),
('37','2019-02-19','3017','1007'),
('38','2019-09-02','3018','1008'),
('39','2019-08-31','3019','1009'),
('40','2018-10-14','3020','1010'),
('41','2018-11-24','3001','1001'),
('42','2019-04-08','3002','1002');

select sig.Id, sig.SightingDate, sig.LocationId
from Sightings sig
order by sig.SightingDate desc
limit 10;









