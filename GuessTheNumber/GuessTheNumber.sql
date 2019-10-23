DROP DATABASE IF EXISTS GuessTheNumberDB;
CREATE DATABASE GuessTheNumberDB;
USE GuessTheNumberDB;

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

SELECT *
FROM Game
left JOIN Round on round.gameId = Game.gameId;

insert into Game ( targetNum, isOver )
values 
( '1234', false ),
( '4567', false ),
( '9127', false);

-- insert into Round(roundId,gameId,guessNum,exactMatchCt,partialMatchCt)
-- values
-- ('1','1','4321','0','4'),
-- ('2','1','1243','2','2'),
-- ('3','1','1234','4','0'),
-- ('1','2','6789','0','2'),
-- ('2','2','4567','4','0');






