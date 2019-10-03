drop database if exists ChadMcGrath_HotelDB;

create database ChadMcGrath_HotelDB;

use ChadMcGrath_HotelDB;

create table Guest (
    CustomerID int primary key auto_increment,
    CustName varchar(30) not null,
    Address varchar(50) not null,
    City varchar(25) not null,
    State char(2) not null,
    ZIP char(5)  not null,
    Phone char (10) not null
);

create table Reservation(
ReservationID int primary key auto_increment,
CustomerID int not null,
Foreign Key fk_reservation_guest(CustomerID)
references Guest(CustomerID),
StartDate date not null,
EndDate date not null
);

create table Room_Type_Attributes(
RoomType char(6) primary key,
StandardOccupancy smallint not null,
MaxOccupancy smallint not null,
SleeperSofaQty smallint not null,
QueenQty smallint not null,
KingQty smallint not null,
StandardPrice decimal(5,2) not null,
ExtraPersonCharge decimal(4,2) not null
);

create table Jacuzzi_Pricing(
Jacuzzi boolean primary key,
ExtraCharge decimal(4,2) not null
);

create table Amenities(
AmenitiesID int primary key auto_increment, 
Jacuzzi boolean not null,
Microwave boolean not null,
Refrigerator boolean not null,
Stove boolean not null,
Foreign Key fk_Amenities_Jacuzzi_Pricing(Jacuzzi)
references Jacuzzi_Pricing(Jacuzzi)
);

create table Room (
RoomNumber int primary key,
RoomType char(6) not null,
Foreign Key fk_Room_Room_Type_Attributes(RoomType)
references Room_Type_Attributes(RoomType),
AmenitiesID int not null,
Foreign Key fk_Room_Amenities(AmenitiesID)
references Amenities(AmenitiesID),
AdaAccessible boolean not null
);

create table Room_Reservation(
ReservationID int not null,
RoomNumber int not null,
Primary Key pk_Room_Reservation(ReservationID,RoomNumber),
Foreign Key fk_Room_Reservation_Room(RoomNumber)
references Room(RoomNumber),
foreign Key fk_Room_Reservation_Reservation(ReservationID)
references Reservation(ReservationID),
AdultQty smallint not null,
ChildrenQty smallint not null
);









