use surfblogtestdb;

Select br.id breakid, br.`name` breakname, br.latitude, br.longitude, be.id beachid, be.`name` beachname, be.zipcode
From break br
Inner Join beach be On br.beachId = be.id
Where beachId = 301;

Select bc.id commentid, bc.userid, bc.beachid, bc.`comment`, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled
From beach_comment bc
Inner Join `User` u On bc.userid = u.id
Inner Join beach be On bc.beachid = be.id
Where beachId = 301;

Select brc.id commentid, brc.userid, brc.breakid, brc.`comment`, br.`name` breakname, br.beachid, br.latitude, br.longitude, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled
From break_comment brc
Inner Join `User` u On brc.userid = u.id
Inner Join break br On brc.breakid = br.id
Inner Join beach be On br.beachid = be.id 
Where breakId = 801;

Select * 
From home_news_link 
Where isactive = true;

Select * 
From home_news_link 
Where id = 1;

Select * 
From home_news_link 

Insert into home_news_link(news_url, news_link_text, picture_url, isactive)
Values
('test1', 'test2', 'test3', true);

Update home_news_link 
Set news_url = 'test6',
news_link_text = 'test6',
picture_url = 'test6',
isactive = false
Where id = 5;

Delete 
From home_news_link 
Where id = 5;

Select * 
From Beach
Where id = 301;

Insert into beach(`name`, zipcode)
Values 
('Test', 55555);

Update Beach 
Set `name` = 'testtest',
zipcode = 99999
Where id = 304;

Delete 
From beach 
Where id = 304;

Select br.id breakid, br.`name` breakname, br.latitude, br.longitude, be.id beachid, be.`name` beachname, be.zipcode
From break br
Inner Join beach be On br.beachId = be.id
Where br.id = 801;

Insert into Break(`name`, beachid, latitude, longitude)
Values 
('test1', 303, -39.933844, 123.339428);

Update Break 
Set `name` = 'testyy',
beachid = 301,
latitude = '-23.555555',
longitude = '110.112233'
Where id = 810;

Select bc.id commentid, bc.userid, bc.beachid, bc.`comment`, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled
From beach_comment bc
Inner Join `User` u On bc.userid = u.id
Inner Join beach be On bc.beachid = be.id
Where bc.id = 1012;

Insert into beach_comment(userid, beachid, `comment`)
Values
(1, 301, 'beach so nice');

Update beach_comment 
Set userId = 2,
beachid = 303,
`comment` = 'wavy duuudddee'
Where id = 1021;

Delete 
From beach_comment 
Where id = 1012;

Select brc.id commentid, brc.userid, brc.breakid, brc.`comment`, br.`name` breakname, br.beachid, br.latitude, br.longitude, be.`name` beachname, be.zipcode, u.username, u.`password`, u.enabled
From break_comment brc
Inner Join `User` u On brc.userid = u.id
Inner Join break br On brc.breakid = br.id
Inner Join beach be On br.beachid = be.id 
Where brc.id = 1001;

Insert into break_comment(userid, breakid, `comment`)
Values 
(1, 802, 'nasty break broooo');

Update break_comment 
Set userId = 1,
breakid = 803,
`comment` = 'wavy duuudddee'
Where id = 1012;

Delete 
From break_comment
Where id = 1012;

Delete 
From break_comment 
Where breakid = 805;

Delete 
From break 
Where id = 805;

Delete bc.*
From break_comment bc
Inner Join break br On bc.breakid = br.id
Inner Join Beach be On br.beachid = be.id
Where be.id = 303;

Delete 
From beach_comment 
Where beachId = 303;

Delete 
From break 
Where beachid = 303; 

Delete 
From beach 
Where id = 303;

Delete 
From home_news_link 
Where id > 0;

Delete 
From 
beach_comment 
Where id > 0;

Delete 
From break_comment 
Where id > 0;

Delete 
From break 
Where id > 0;

Delete 
From beach 
Where id > 0;

Delete 
From user_role 
Where user_id > 0;

Delete 
From User 
Where id > 0;