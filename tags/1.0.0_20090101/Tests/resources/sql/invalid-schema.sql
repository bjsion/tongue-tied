
    alter table Translation 
        drop constraint FKF5B39A9121237D2C;

    alter table Translation 
        drop constraint FKF5B39A91B2B40028;

    alter table Translation 
        drop constraint FKF5B39A91401B820C;

    alter table Translation 
        drop constraint FKF5B39A918B3B54E8;

    alter table authorities 
        drop constraint FK2B0F13219D90DDE5;

    drop table AuditLogRecord if exists;

    drop table Bundle if exists;

    drop table Country if exists;

    drop table Keyword if exists;

    drop table Language if exists;

    drop table ServerData if exists;

    drop table Translation if exists;

    drop table User if exists;

    drop table authorities if exists;

    create table AuditLogRecord (
        id bigint generated by default as identity (start with 1),
        created timestamp not null,
        entityClass varchar(255) not null,
        entityId bigint not null,
        message varchar(255) not null,
        username varchar(255),
        primary key (id)
    );

    create table Bundle (
        id bigint generated by default as identity (start with 1),
        description varchar(255),
        isDefault bit not null,
        isGlobal bit not null,
        name varchar(255) not null,
        resourceName varchar(255) not null,
        primary key (id),
