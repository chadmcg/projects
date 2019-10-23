DROP DATABASE IF EXISTS GuessTheNumberDBtest;
CREATE DATABASE GuessTheNumberDBtest;
USE GuessTheNumberDBtest;

create table Game (
	gameId int primary key auto_increment,
    targetNum char(4) not null,
	isOver boolean not null default false
);

create table Round (
	gameId int not null,
    roundId	int not null,
	guessNum char(4) not null,
    guessTime timestamp not null,
    exactMatchCt numeric(1)	not null,
    partialMatchCt	numeric(1)	not null,
    
	foreign key fk_Round_Game (gameId)
    references Game(gameId)
);



-- insert into Game (gameId, targetNum, isOver )
-- values 
-- ('1', '1386', true),
-- ('2', '4910', true),
-- ('3', '9647', true),
-- ('4', '1235', false);

-- insert into Round (gameId, roundId,guessNum,exactMatchCt,partialMatchCt)
-- values
-- ('1','1','1235','1','1'),
-- ('1','2','1358','2','1'),
-- ('1','3','1386','4','0'),
-- ('2','1','1075','0','2'),
-- ('2','2','4910','4','0'),
-- ('3','1','1234','0','1'),
-- ('3','2','5678','1','1'),
-- ('3','3','918','0','1'),
-- ('3','4','2134','0','1'),
-- ('3','5','1230','0','0'),
-- ('3','6','4978','0','3'),
-- ('3','7','9647','4','0'),
-- ('4','1','1678','1','0');

select *
from Game;