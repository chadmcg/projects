-- ------------------------------------------
-- 1. Write a query that returns a list of 
-- reservations that end in July 2023, 
-- including the name of the guest, the 
-- room number(s), and the reservation dates.

select Reservation.ReservationID, Guest.CustName,Room_Reservation.RoomNumber,Reservation.StartDate,Reservation.EndDate
from Guest
inner join Reservation on Reservation.CustomerID = Guest.CustomerID
inner join Room_Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
where Reservation.EndDate  like '2023-07%';

-- Result:
-- 		1004	Bettyann Seery	303	2023-07-28	2023-07-29
-- 		1007	Chad McGrath	205	2023-06-28	2023-07-02
-- 		1020	Walter Holaway	204	2023-07-13	2023-07-14
-- 		1022	Wilfred Vise	401	2023-07-18	2023-07-21

-- ------------------------------------------
-- ------------------------------------------
-- 2. Write a query that returns a list of 
-- all reservations for rooms with a jacuzzi, 
-- displaying the guest's name, the room number, 
-- and the dates of the reservation.

select Guest.CustName,Room_Reservation.RoomNumber, Reservation.StartDate, Reservation.EndDate
from Guest
inner join Reservation on Reservation.CustomerID = Guest.CustomerID
inner join Room_Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
inner join Room on Room.RoomNumber = Room_Reservation.RoomNumber 
inner join Amenities on Amenities.AmenitiesID = Room.amenitiesID 
where Amenities.Jacuzzi='1';

-- Result:
-- 		Bettyann Seery	203	2023-02-05	2023-02-10
-- 		Bettyann Seery	303	2023-07-28	2023-07-29
-- 		Bettyann Seery	305	2023-08-30	2023-09-01
-- 		Chad McGrath	205	2023-06-28	2023-07-02
-- 		Chad McGrath	307	2023-03-17	2023-03-20
-- 		Duane Cullison	305	2023-02-22	2023-02-24
-- 		Karie Yang	201	2023-03-06	2023-03-07
-- 		Karie Yang	203	2023-09-13	2023-09-15
-- 		Mack Simmer	301	2023-11-22	2023-11-25
-- 		Walter Holaway	301	2023-04-09	2023-04-13
-- 		Wilfred Vise	207	2023-04-23	2023-04-24

-- ------------------------------------------
-- ------------------------------------------
-- 3. Write a query that returns all the rooms 
-- reserved for a specific guest, including the 
-- guest's name, the room(s) reserved, the 
-- starting date of the Reservation, and how many 
-- people were included in the Reservation. 
-- (Choose a guest's name from the existing data.)

select Guest.CustName, Room_Reservation.RoomNumber,Reservation.StartDate, Reservation.EndDate, Room_Reservation.AdultQty+Room_Reservation.ChildrenQty as TotalPeople
from Guest
inner join Reservation on Reservation.CustomerID = Guest.CustomerID
inner join Room_Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
where Guest.CustName = 'Mack Simmer';

-- Result:
-- 		Mack Simmer	308	2023-02-02	2023-02-04	1
-- 		Mack Simmer	208	2023-09-16	2023-09-17	2
-- 		Mack Simmer	206	2023-11-22	2023-11-25	2
-- 		Mack Simmer	301	2023-11-22	2023-11-25	4

-- ------------------------------------------
-- ------------------------------------------
-- 4. Write a query that returns a list of rooms, 
-- Reservation ID, and per-room cost for each 
-- Reservation. The results should include all 
-- rooms, whether or not there is a Reservation 
-- associated with the room.

select Room.RoomNumber, Room_Reservation.ReservationID, 
	(Room_Type_Attributes.StandardPrice*datediff(Reservation.EndDate,Reservation.StartDate)) 
    +
	if(Room_Reservation.AdultQty>Room_Type_Attributes.StandardOccupancy,
			(Room_Reservation.AdultQty-Room_Type_Attributes.StandardOccupancy)*Room_Type_Attributes.ExtraPersonCharge*datediff(Reservation.EndDate,Reservation.StartDate),
			0)
	+
	if(Amenities.Jacuzzi = '1',
			25*datediff(Reservation.EndDate,Reservation.StartDate),
            0)
    
    as RoomTotal
from Room
left outer join Room_Reservation on Room.RoomNumber = Room_Reservation.RoomNumber
left outer join Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
inner join Room_Type_Attributes on Room.RoomType = Room_Type_Attributes.RoomType
inner join Amenities on Amenities.AmenitiesID = Room.AmenitiesID
inner join Jacuzzi_Pricing on Jacuzzi_Pricing.Jacuzzi = Amenities.Jacuzzi;

-- Result:
-- 		201	1012	199.99
-- 		202	1023	349.98
-- 		203	1003	999.95
-- 		203	1013	399.98
-- 		204	1020	184.99
-- 		205	1007	699.96
-- 		206	1011	599.96
-- 		206	1016	449.97
-- 		207	1021	174.99
-- 		208	1011	599.96
-- 		208	1015	149.99
-- 		301	1016	599.97
-- 		301	1019	799.96
-- 		302	1001	924.95
-- 		302	1018	699.96
-- 		303	1004	199.99
-- 		304	1002	184.99
-- 		305	1005	349.98
-- 		305	1008	349.98
-- 		306		
-- 		307	1006	524.97
-- 		308	1014	299.98
-- 		401	1009	1199.97
-- 		401	1017	1199.97
-- 		401	1022	1259.97
-- 		402		


-- ------------------------------------------
-- ------------------------------------------
-- 5. Write a query that returns all the rooms 
-- accommodating at least three guests and that 
-- are reserved on any date in April 2023.

select Room.RoomNumber 
from Room
inner join Room_Reservation on Room.RoomNumber = Room_Reservation.RoomNumber
inner join Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
inner join Room_Type_Attributes on Room.RoomType = Room_Type_Attributes.RoomType
where Room_Type_Attributes.RoomType in ('Double','Suite') and ((Reservation.StartDate between '2023-04-01' and '2023-04-30') or (Reservation.EndDate between '2023-04-01' and '2023-04-30'));

-- Result:
-- 		301
-- ------------------------------------------
-- ------------------------------------------
-- 6. Write a query that returns a list of all 
-- guest names and the number of Reservations 
-- per guest, sorted starting with the guest 
-- with the most reservations and then by the 
-- guest's last name.

select Guest.CustName,count(Reservation.ReservationID)
from Guest
inner join Reservation on Reservation.CustomerID = Guest.CustomerID
inner join Room_Reservation on Room_Reservation.ReservationID = Reservation.ReservationID
group by Guest.CustName
order by count(ReservationID) desc, substring(Guest.CustName,locate(' ',Guest.CustName), length(Guest.CustName)-locate(' ',Guest.CustName)) asc;

-- Result:
-- 		Mack Simmer	4
-- 		Bettyann Seery	3
-- 		Duane Cullison	2
-- 		Walter Holaway	2
-- 		Aurore Lipton	2
-- 		Chad McGrath	2
-- 		Maritza Tilton	2
-- 		Joleen Tison	2
-- 		Wilfred Vise	2
-- 		Karie Yang	2
-- 		Zachery Luechtefeld	1

-- ------------------------------------------
-- ------------------------------------------
-- 7. Write a query that displays the name, 
-- address, and phone number of a guest based 
-- on their phone number. (Choose a phone 
-- number from the existing data.)

select Guest.CustName, Guest.Address, Guest.City, Guest.State, Guest.Phone
from Guest
where Guest.Phone = '4463966785';

-- Result:
-- 		Walter Holaway	7556 Arrowhead St.	Cumberland	RI	4463966785

-- ------------------------------------------


