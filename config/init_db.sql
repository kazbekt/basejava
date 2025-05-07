create table resume
(
    uuid      VARCHAR(36) not null
        constraint resume_pk
            primary key,
    full_name text        not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table text_section
(
    id             serial
        constraint text_section_pk
            primary key,
    "ts.type"      text     not null,
    "ts.value"     text     not null,
    ts_resume_uuid char(36) not null
        constraint text_section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table text_section
    owner to postgres;

create index "text_section_ts_resume_uuid_ts.type_index"
    on text_section (ts_resume_uuid, "ts.type");

create table list_section
(
    id             serial
        constraint list_section_pk
            primary key,
    ls_type        text not null,
    ls_value       text not null,
    ls_resume_uuid char(36)
        constraint list_section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table list_section
    owner to postgres;

create index list_section_ls_resume_uuid_ls_type_index
    on list_section (ls_resume_uuid, ls_type);