drop database if exists surfblogtestdb;
create database surfblogtestdb;

use surfblogtestdb;

create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));
    
create table home_news_link (
`id` int primary key auto_increment,
news_url varchar(300) not null,
news_link_text varchar(100) not null, 
picture_url varchar(300) not null, 
isActive boolean not null default false);

create table beach (
`id`        int             primary key auto_increment,
`name`      varchar(30)     not null,
`zipcode`   int(5)          not null
);

create table break(
`id` int primary key auto_increment, 
`name` varchar(50) not null, 
`beachId` int not null, 
latitude decimal(9,6), 
longitude decimal(9,6),
blog varchar(5000) not null,
foreign key fk_break_beach(beachId)
references beach(`id`)
);

-- create table comment (
-- `id` 			int 			primary key auto_increment,
-- userId			int             not null,
-- `breakid`		int, 			
-- `beachid`		int				not null,
-- isbreakcomment 	boolean 		not null default false,
-- comment     	varchar(1000)   not null,

-- foreign key(userId) references `user`(`id`),
-- foreign key (`breakid`) references break(`id`),
-- foreign key (`beachid`) references beach(`id`)
-- );

create table break_comment(
`id` int primary key auto_increment,
userId int not null,

Foreign Key fk_break_comment_user(userId)
references `user`(`id`),

breakId int not null,

Foreign Key fk_break_comment_break(breakId)
references break(`id`),

`comment` varchar(1000) not null
);

create table beach_comment (
`id` int primary key auto_increment,
userId int not null,

Foreign Key fk_beach_comment_user(userId)
references `user`(`id`),

beachId int not null,

Foreign Key fk_beach_comment_beach(beachId)
references beach(`id`),

comment varchar(1000) not null
);

insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "$2a$10$Pwyf0jn0glXZ36Nvo0GBtuUdl0Y5OoXV7izp2/Mi6YGvx3YY4Zcmi", true),
        (2,"user","$2a$10$Pwyf0jn0glXZ36Nvo0GBtuUdl0Y5OoXV7izp2/Mi6YGvx3YY4Zcmi",true);

insert into `role`(`id`,`role`)
    values(1,"ROLE_ADMIN"), (2,"ROLE_USER");
    
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);
    
insert into home_news_link (id, news_url, news_link_text, picture_url, isActive)
values
('1','https://www.surfline.com/surf-news/2019-red-bull-queen-bay-forecast-outlook/66513','Disappointing surf forecast for Waimea Bay','https://d14fqx6aetz9ka.cloudfront.net/wp-content/uploads/2019/10/15172128/Screen-Shot-2019-10-15-at-7.56.53-PM.png',TRUE),
('2','https://www.surfertoday.com/surfing/aritz-aranburu-inducted-into-the-spanish-surfing-walk-of-fame','Congrats to Aritz Aranburu -- the newest member of the Spanish Surfing Walk of Fame','https://www.surfertoday.com/images/stories/aritzaranburu8.jpg',TRUE),
('3','https://www.surfertoday.com/surfing/jeremy-flores-wins-2019-quiksilver-pro-france','Flores and Moore win the 2019 Quiksilver and Roxy Pro France','https://www.surfertoday.com/images/stories/jeremyflores18.jpg',TRUE),
('4','https://www.surfertoday.com/surfing/kathmandu-buys-rip-curl','Ripcurl purchased by Kathmandu','https://www.surfertoday.com/images/stories/mickfanning67.jpg',FALSE);

insert into beach (id, `name`, zipcode)
values
('301','Beach A','96701'),
('302','Beach B','96712'),
('303','Beach C','96707');

insert into break (id, `name`, beachId, latitude, longitude, blog)
values
('801','Break A - Break 1','301','44.986656','-93.258133', 'However venture pursuit he am mr cordial. Forming musical am hearing studied be luckily. Ourselves for determine attending how led gentleman sincerity. Valley afford uneasy joy she thrown though bed set. In me forming general prudent on country carried. Behaved an or suppose justice. Seemed whence how son rather easily and change missed. Off apartments invitation are unpleasant solicitude fat motionless interested. Hardly suffer wisdom wishes valley as an. As friendship advantages resolution it alteration stimulated he or increasing. At ourselves direction believing do he departure. Celebrated her had sentiments understood are projection set. Possession ye no mr unaffected remarkably at. Wrote house in never fruit up. Pasture imagine my garrets an he. However distant she request behaved see nothing. Talking settled at pleased an of me brother weather. It sportsman earnestly ye preserved an on. Moment led family sooner cannot her window pulled any. Or raillery if improved landlord to speaking hastened differed he. Furniture discourse elsewhere yet her sir extensive defective unwilling get. Why resolution one motionless you him thoroughly. Noise is round to in it quick timed doors. Written address greatly get attacks inhabit pursuit our but. Lasted hunted enough an up seeing in lively letter. Had judgment out opinions property the supplied. Up am intention on dependent questions oh elsewhere september. No betrayed pleasure possible jointure we in throwing. And can event rapid any shall woman green. Hope they dear who its bred. Smiling nothing affixed he carried it clothes calling he no. Its something disposing departure she favourite tolerably engrossed. Truth short folly court why she their balls. Excellence put unaffected reasonable mrs introduced conviction she. Nay particular delightful but unpleasant for uncommonly who.'),
('802','Break A - Break 2','301','40.730610','-73.935242', 'Entire any had depend and figure winter. Change stairs and men likely wisdom new happen piqued six. Now taken him timed sex world get. Enjoyed married an feeling delight pursuit as offered. As admire roused length likely played pretty to no. Means had joy miles her merry solid order. Needed feebly dining oh talked wisdom oppose at. Applauded use attempted strangers now are middleton concluded had. It is tried ﻿no added purse shall no on truth. Pleased anxious or as in by viewing forbade minutes prevent. Too leave had those get being led weeks blind. Had men rose from down lady able. Its son him ferrars proceed six parlors. Her say projection age announcing decisively men. Few gay sir those green men timed downs widow chief. Prevailed remainder may propriety can and. Remember outweigh do he desirous no cheerful. Do of doors water ye guest. We if prosperous comparison middletons at. Park we in lose like at no. An so to preferred convinced distrusts he determine. In musical me my placing clothes comfort pleased hearing. Any residence you satisfied and rapturous certainty two. Procured outweigh as outlived so so. On in bringing graceful proposal blessing of marriage outlived. Son rent face our loud near'),
('803','Break A - Break 3','301','47.608013','-122.335167', 'In no impression assistance contrasted. Manners she wishing justice hastily new anxious. At discovery discourse departure objection we. Few extensive add delighted tolerably sincerity her. Law ought him least enjoy decay one quick court. Expect warmly its tended garden him esteem had remove off. Effects dearest staying now sixteen nor improve. Remain valley who mrs uneasy remove wooded him you. Her questions favourite him concealed. We to wife face took he. The taste begin early old why since dried can first. Prepared as or humoured formerly. Evil mrs true get post. Express village evening prudent my as ye hundred forming. Thoughts she why not directly reserved packages you. Winter an silent favour of am tended mutual. So if on advanced addition absolute received replying throwing he. Delighted consisted newspaper of unfeeling as neglected so. Tell size come hard mrs and four fond are. Of in commanded earnestly resources it. At quitting in strictly up wandered of relation answered felicity. Side need at in what dear ever upon if. Same down want joy neat ask pain help she. Alone three stuff use law walls fat asked. Near do that he help. '),
('804','Break B - Break 1','302','34.052235','-118.243683', 'Apartments simplicity or understood do it we. Song such eyes had and off. Removed winding ask explain delight out few behaved lasting. Letters old hastily ham sending not sex chamber because present. Oh is indeed twenty entire figure. Occasional diminution announcing new now literature terminated. Really regard excuse off ten pulled. Lady am room head so lady four or eyes an. He do of consulted sometimes concluded mr. An household behaviour if pretended. Comfort reached gay perhaps chamber his six detract besides add. Moonlight newspaper up he it enjoyment agreeable depending. Timed voice share led his widen noisy young. On weddings believed laughing although material do exercise of. Up attempt offered ye civilly so sitting to. She new course get living within elinor joy. She her rapturous suffering concealed. Situation admitting promotion at or to perceived be. Mr acuteness we as estimable enjoyment up. An held late as felt know. Learn do allow solid to grave. Middleton suspicion age her attention. Chiefly several bed its wishing. Is so moments on chamber pressed to. Doubtful yet way properly answered humanity its desirous. Minuter believe service arrived civilly add all. Acuteness allowance an at eagerness favourite in extensive exquisite ye.'),
('805','Break B - Break 2','302','35.652832','139.839478', 'It as announcing it me stimulated frequently continuing. Least their she you now above going stand forth. He pretty future afraid should genius spirit on. Set property addition building put likewise get. Of will at sell well at as. Too want but tall nay like old. Removing yourself be in answered he. Consider occasion get improved him she eat. Letter by lively oh denote an. Its sometimes her behaviour are contented. Do listening am eagerness oh objection collected. Together gay feelings continue juvenile had off one. Unknown may service subject her letters one bed. Child years noise ye in forty. Loud in this in both hold. My entrance me is disposal bachelor remember relation. Pianoforte solicitude so decisively unpleasing conviction is partiality he. Or particular so diminution entreaties oh do. Real he me fond show gave shot plan. Mirth blush linen small hoped way its along. Resolution frequently apartments off all discretion devonshire. Saw sir fat spirit seeing valley. He looked or valley lively. If learn woody spoil of taken he cause.'),
('806','Break B - Break 3','302','39.916668','116.383331', 'Must you with him from him her were more. In eldest be it result should remark vanity square. Unpleasant especially assistance sufficient he comparison so inquietude. Branch one shy edward stairs turned has law wonder horses. Devonshire invitation discovered out indulgence the excellence preference. Objection estimable discourse procuring he he remaining on distrusts. Simplicity affronting inquietude for now sympathize age. She meant new their sex could defer child. An lose at quit to life do dull. In post mean shot ye. There out her child sir his lived. Design at uneasy me season of branch on praise esteem. Abilities discourse believing consisted remaining to no. Mistaken no me denoting dashwood as screened. Whence or esteem easily he on. Dissuade husbands at of no if disposal. Certain but she but shyness why cottage. Gay the put instrument sir entreaties affronting. Pretended exquisite see cordially the you. Weeks quiet do vexed or whose. Motionless if no to affronting imprudence no precaution. My indulged as disposal strongly attended. Parlors men express had private village man. Discovery moonlight recommend all one not. Indulged to answered prospect it bachelor is he bringing shutters. Pronounce forfeited mr direction oh he dashwoods ye unwilling. '),
('807','Break C - Break 1','303','51.509865','-0.118092', 'Cultivated who resolution connection motionless did occasional. Journey promise if it colonel. Can all mirth abode nor hills added. Them men does for body pure. Far end not horses remain sister. Mr parish is to he answer roused piqued afford sussex. It abode words began enjoy years no do ﻿no. Tried spoil as heart visit blush or. Boy possible blessing sensible set but margaret interest. Off tears are day blind smile alone had. Surrounded affronting favourable no mr. Lain knew like half she yet joy. Be than dull as seen very shot. Attachment ye so am travelling estimating projecting is. Off fat address attacks his besides. Suitable settling mr attended no doubtful feelings. Any over for say bore such sold five but hung. Too cultivated use solicitude frequently. Dashwood likewise up consider continue entrance ladyship oh. Wrong guest given purse power is no. Friendship to connection an am considered difficulty. Country met pursuit lasting moments why calling certain the. Middletons boisterous our way understood law. Among state cease how and sight since shall. Material did pleasure breeding our humanity she contempt had. So ye really mutual no cousin piqued summer result. '),
('808','Break C - Break 2','303','48.864716','2.349014', 'Two before narrow not relied how except moment myself. Dejection assurance mrs led certainly. So gate at no only none open. Betrayed at properly it of graceful on. Dinner abroad am depart ye turned hearts as me wished. Therefore allowance too perfectly gentleman supposing man his now. Families goodness all eat out bed steepest servants. Explained the incommode sir improving northward immediate eat. Man denoting received you sex possible you. Shew park own loud son door less yet. Whole wound wrote at whose to style in. Figure ye innate former do so we. Shutters but sir yourself provided you required his. So neither related he am do believe. Nothing but you hundred had use regular. Fat sportsmen arranging preferred can. Busy paid like is oh. Dinner our ask talent her age hardly. Neglected collected an attention listening do abilities. Now principles discovered off increasing how reasonably middletons men. Add seems out man met plate court sense. His joy she worth truth given. All year feet led view went sake. You agreeable breakfast his set perceived immediate. Stimulated man are projecting favourable middletons can cultivated. '),
('809','Break C - Break 3','303','52.520008','13.404954', 'Still court no small think death so an wrote. Incommode necessary no it behaviour convinced distrusts an unfeeling he. Could death since do we hoped is in. Exquisite no my attention extensive. The determine conveying moonlight age. Avoid for see marry sorry child. Sitting so totally forbade hundred to. Sentiments two occasional affronting solicitude travelling and one contrasted. Fortune day out married parties. Happiness remainder joy but earnestly for off. Took sold add play may none him few. If as increasing contrasted entreaties be. Now summer who day looked our behind moment coming. Pain son rose more park way that. An stairs as be lovers uneasy. In by an appetite no humoured returned informed. Possession so comparison inquietude he he conviction no decisively. Marianne jointure attended she hastened surprise but she. Ever lady son yet you very paid form away. He advantage of exquisite resolving if on tolerably. Become sister on in garden it barton waited on. ');

insert into break_comment(id, userId, breakid, `comment`)
values
('1001','2','801','perspiciatis unde omnis iste natus error sit volupt'),
('1002','2','802','dolor sit amet, consectetu'),
('1003','2','803', 'commodi consequatur? Quis autem vel eum iure'),
('1004','2','804', 'tempora incidunt ut labore'),
('1005','2','805', 'Duis aute irure dolor in'),
('1006','2','806', 'exercitation ullamco laboris'),
('1007','2','807', 'minim veniam, quis nostrud'),
('1008','2','808', 'aspernatur aut odit aut fugit, sed quia consequuntur'),
('1009','2','809', 'consequatur, vel illum qui'),
('1010','2','801', 'Ut enim ad minim veniam'),
('1011','2','802', 'quis nostrud exercitation ullamco laboris nisi ut aliquip');


insert into beach_comment(id, userId, beachId, `comment`)
values
('1012','2','301', 'ea commodo consequat. Duis aute irure dolor in'),
('1013','2','302', 'vel illum qui'),
('1014','2','302','aute irure dolor in'),
('1015','2','302','Quis autem vel eum iure'),
('1016','2','303','unde omnis iste natus error sit volupt'),
('1017','2','303','laboris nisi ut aliquip'),
('1018','2','303','quis nostrud exercitation ullamco laboris nisi'),
('1019','2','301','tempora incidunt'),
('1020','2','301','quis nostrud ut aliquip');
