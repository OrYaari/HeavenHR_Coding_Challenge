create table offer
(
    jobTitle varchar(255) not null,
    startDate date not null,
    numberOfApplications integer not null,
    primary key(jobTitle)
);

create table application
(
    id integer not null,
    offer varchar(255) not null,
    candidateEmail varchar(255) not null,
    resume text not null,
    applicationStatus varchar(255) not null,
    primary key(id)
);