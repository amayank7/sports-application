# Solution Overview

## Problem Statement 1

Regarding endpoint `/tour/matches` , the operation queries based on a join table including tours and matches, the operation relies on a WHERE clause querying tours.name column. 
Since the column was not indexed, the apis latency linearly increased with size. 

This has been optimized by introducing a SQL index on the tours.name column. (migration/base.sql) 
```
-- add indexes
create index idx_name on tours (name);
```

While MySQL probably should perform the original query in an optimal manner, <br>
The query 
```
select * from matches left join tours on matches.tourId = tours.id where tours.name = ?
```
can also be modified to  
```
SELECT * FROM matches LEFT JOIN (SELECT * FROM tours WHERE tours.name= ?) T ON matches.tourId = T.id
```
in order to ensure the JOIN is performed on the required subset of tours table.
<br>
<br>

---

## Problem Statement 2
Changes have been made in the implementation of endpoint `/sport/tour/match` in order to include the required fields. Instead of returning a list of match names in the nested objects. An object including all required details is returned.<br>
Updated Response:
```
{
    "Cricket": {
        "Indian Premier League, 2023": [
            {
                "matchName": "GT vs RCB",
                "matchId": 1,
                "matchStartTime": "2023-04-09T18:00:00.000Z",
                "matchFormat": "T20"
            },
            {
                "matchName": "CSK vs MI",
                "matchId": 2,
                "matchStartTime": "2023-04-10T18:00:00.000Z",
                "matchFormat": "T20"
            },
            {
                "matchName": "LSG vs KXIP",
                "matchId": 3,
                "matchStartTime": "2023-04-11T18:00:00.000Z",
                "matchFormat": "T20"
            },
            {
                "matchName": "RR vs SRH",
                "matchId": 4,
                "matchStartTime": "2023-04-12T18:00:00.000Z",
                "matchFormat": "T20"
            }
        ],
        "India Tour of West Indies, 2023": [
            {
                "matchName": "IND vs WI",
                "matchId": 8,
                "matchStartTime": "2023-06-10T10:00:00.000Z",
                "matchFormat": "ODI"
            },
            {
                "matchName": "IND vs WI",
                "matchId": 9,
                "matchStartTime": "2023-06-12T10:00:00.000Z",
                "matchFormat": "ODI"
            },
            {
                "matchName": "IND vs WI",
                "matchId": 10,
                "matchStartTime": "2023-06-14T10:00:00.000Z",
                "matchFormat": "ODI"
            }
        ]
    },
    "Football": {
        "India Super League, 2023": [
            {
                "matchName": "BLR vs BEN",
                "matchId": 5,
                "matchStartTime": "2023-04-29T18:00:00.000Z",
                "matchFormat": "soccer"
            },
            {
                "matchName": "ATK vs MCFC",
                "matchId": 6,
                "matchStartTime": "2023-04-21T18:00:00.000Z",
                "matchFormat": "soccer"
            },
            {
                "matchName": "KER vs JFC",
                "matchId": 7,
                "matchStartTime": "2023-04-22T18:00:00.000Z",
                "matchFormat": "soccer"
            }
        ],
        "English Premier League, 2022": [
            {
                "matchName": "KER vs JFC",
                "matchId": 11,
                "matchStartTime": "2022-04-09T18:00:00.000Z",
                "matchFormat": "soccer"
            }
        ]
    }
}
```
<br>
<br>

---

## Problem Statement 3
A seperate Java/Spring Boot based application has been hosted on a seperate docker container and exposed on port 3001, Implementation for this news service can be found in `/news-service` directory. A news table has been introduced in the existing db connection.
```
create table if not exists mydb.news
(
    id int auto_increment not null primary key,
    title varchar(50) not null,
    description text,
    matchId int,
    tourId int,
    sportId int not null,
    status boolean not null default true,
    recUpdatedAt timestamp not null default current_timestamp on update current_timestamp,
    createdAt timestamp not null default current_timestamp,
    foreign key (matchId) references matches(id),
    foreign key (tourId) references tours(id),
    foreign key (sportId) references sports(id)

);
```

 This structure has been preferred despite having transitive dependencies, since it optimizes our search operations.

### API Endpoints
`POST` Create news
```
curl --location 'localhost:3001/news' \
--header 'Content-Type: application/json' \
--data '{
    "title":"Everton points penalty reduced",
    "description":"Everton'\''s appeal has been heard by the Premier League. A reduced penalty of 6 points has now been imposed, reduced from the 10 point penalty before.",
    "referenceType":"TOUR",
    "referenceId":4
}'
```
Here request has 4 requirements:

- title -> News Title
- description -> News Description
- referenceType -> `MATCH` / `TOUR` / `SPORT` (the type of reference the news is associated with)
- referenceId -> corresponding id of `MATCH` / `TOUR` / `SPORT`  in respective tables, that the news is associated with.

___
`GET` Fetch news by Sport
```
curl --location 'localhost:3001/news/sport?id=1'
```

___
`GET` Fetch news by Tour
```
curl --location 'localhost:3001/news/tour?id=1'
```

___
`GET` Fetch news by Match
```
curl --location 'localhost:3001/news/match?id=1'
```