use ChadMcGrath_HotelDB;

insert into Guest (CustomerID,CustName, Address,City,State,ZIP,Phone)
    values ('201','Aurore Lipton','762 Wild Rose Street','Saginaw','MI','48601','3775070974'),
('202','Bettyann Seery','750 Wintergreen Dr.','Wasilla','AK','99654','4782779632'),
('203','Chad McGrath','100 Main St','Minneapolis','MN','55417','1234567890'),
('204','Duane Cullison','9662 Foxrun Lane','Harlingen','TX','78552','3084940198'),
('205','Jeremiah Pendergrass','70 Oakwood St.','Zion','IL','60099','2794910960'),
('206','Joleen Tison','87 Queen St.','Drexel Hill','PA','19026','2318932755'),
('207','Karie Yang','9378 W. Augusta Ave.','West Deptford','NJ','08096','2147300298'),
('208','Mack Simmer','379 Old Shore Street','Council Bluffs','IA','51501','2915530508'),
('209','Maritza Tilton','939 Linda Rd.','Burke','VA','22015','4463516860'),
('210','Walter Holaway','7556 Arrowhead St.','Cumberland','RI','02864','4463966785'),
('211','Wilfred Vise','77 West Surrey Street','Oswego','NY','13126','8347271001'),
('212','Zachery Luechtefeld','7 Poplar Dr.','Arvada','CO','80003','8144852615');


insert into Reservation (ReservationID, CustomerID,StartDate,EndDate)
    values ('1001','201','2023-03-18','2023-03-23'),
('1002','201','2023-06-17','2023-06-18'),
('1003','202','2023-02-05','2023-02-10'),
('1004','202','2023-07-28','2023-07-29'),
('1005','202','2023-08-30','2023-09-01'),
('1006','203','2023-03-17','2023-03-20'),
('1007','203','2023-06-28','2023-07-02'),
('1008','204','2023-02-22','2023-02-24'),
('1009','204','2023-11-22','2023-11-25'),
('1010','205','2023-03-31','2023-04-05'),
('1011','206','2023-06-10','2023-06-14'),
('1012','207','2023-03-06','2023-03-07'),
('1013','207','2023-09-13','2023-09-15'),
('1014','208','2023-02-02','2023-02-04'),
('1015','208','2023-09-16','2023-09-17'),
('1016','208','2023-11-22','2023-11-25'),
('1017','209','2023-05-30','2023-06-02'),
('1018','209','2023-12-24','2023-12-28'),
('1019','210','2023-04-09','2023-04-13'),
('1020','210','2023-07-13','2023-07-14'),
('1021','211','2023-04-23','2023-04-24'),
('1022','211','2023-07-18','2023-07-21'),
('1023','212','2023-03-29','2023-03-31');


insert into Jacuzzi_Pricing (Jacuzzi, ExtraCharge)
    values ('1','25'),
    ('0','0');

insert into Amenities(AmenitiesID,Jacuzzi,Microwave,Refrigerator,Stove)
values ('101','0','0','1','0'),
('102','1','1','0','0'),
('103','1','1','1','0'),
('104','0','1','1','1'),
('105','0','1','1','0');


insert into Room_Type_Attributes(RoomType,StandardOccupancy,MaxOccupancy,SleeperSofaQty,QueenQty,KingQty,StandardPrice,ExtraPersonCharge)
values ('SINGLE','2','2','0','0','1','149.99',0),
('DOUBLE','2','4','0','2','0','174.99','10.00'),
('SUITE','3','8','1','2','1','399.99','20.00');

insert into  Room(RoomNumber, RoomType, AmenitiesID,AdaAccessible)
values ('201','Double','102','0'),
('202','Double','101','1'),
('203','Double','102','0'),
('204','Double','101','1'),
('205','Single','103','0'),
('206','Single','105','1'),
('207','Single','103','0'),
('208','Single','105','1'),
('301','Double','102','0'),
('302','Double','101','1'),
('303','Double','102','0'),
('304','Double','101','1'),
('305','Single','103','0'),
('306','Single','105','1'),
('307','Single','103','0'),
('308','Single','105','1'),
('401','Suite','104','1'),
('402','Suite','104','1');


insert into  Room_Reservation(ReservationID, RoomNumber,AdultQty,ChildrenQty)
values ('1001','302','3','0'),
('1002','304','3','0'),
('1003','203','2','1'),
('1004','303','2','1'),
('1005','305','1','0'),
('1006','307','1','1'),
('1007','205','2','0'),
('1008','305','2','0'),
('1009','401','2','2'),
('1010','304','2','0'),
('1011','206','2','0'),
('1011','208','1','0'),
('1012','201','2','2'),
('1013','203','2','2'),
('1014','308','1','0'),
('1015','208','2','0'),
('1016','206','2','0'),
('1016','301','2','2'),
('1017','401','2','4'),
('1018','302','2','0'),
('1019','301','1','0'),
('1020','204','3','1'),
('1021','207','1','1'),
('1022','401','4','2'),
('1023','202','2','2');


-- Deleting Jeremiah Pendergrass and 
-- his reservations from the database.

-- -- Get the Customer ID for Jeremiah Pendergrass
select CustomerID
from Guest
where CustName = "Jeremiah Pendergrass";

-- -- Get the reservations associated with JP
select ReservationID
from Reservation
where CustomerID = "205";

-- -- Delete from the room_reservation bridge table 
-- -- the records associated with JP's reservations

-- -- --Count the number of records in the
-- -- --roomreservation table
select count(*)
from Room_Reservation;

-- -- --Delete the records
delete from Room_Reservation
where ReservationID = '1010';

-- -- --Count the number of records in the
-- -- --roomreservation table
select count(*)
from Room_Reservation;

-- -- Delete from  the reservation table any
-- -- reservations made by JP

-- -- --Count the number of records in the
-- -- --reservation table
select count(*)
from Reservation;

-- -- --Delete the records
delete from Reservation
where ReservationID = '1010';

-- -- --Count the number of records in the
-- -- --Reservation table
select count(*)
from Reservation;

-- -- Delete JP from the guest table

-- -- --Count the number of records in the
-- -- --Guest table
select count(*)
from Guest;

-- -- --Delete the records
delete from Guest
where CustomerID = '205';

-- -- --Count the number of records in the
-- -- --Guest table
select count(*)
from Guest;

-- -----------------------------------------
select *
from Guest;






