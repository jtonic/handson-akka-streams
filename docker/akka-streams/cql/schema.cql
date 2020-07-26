CREATE KEYSPACE IF NOT EXISTS test
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE test;

CREATE TABLE test.person (
    id UUID,
    last_name text,
    birthday timestamp,
    nationality text,
    weight text,
    height text,
    primary key (id)
);

insert into test.person
(id, last_name, birthday, nationality, weight, height)
VALUES
(uuid(), 'Antonel-Ernest', toUnixTimestamp(now()), 'Romania', '78', '172')
;
