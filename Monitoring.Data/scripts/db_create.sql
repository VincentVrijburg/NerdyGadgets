create table if not exists component
(
  id           varchar(36)                not null
    primary key,
  name         varchar(50) default 'NULL' null,
  type         varchar(25)                not null,
  availability float                      not null,
  cost         float                      not null,
  updated      datetime                   not null,
  created      datetime                   not null
);

create table if not exists componentdetail
(
  id             varchar(36)                    not null
    primary key,
  componentId    varchar(36) default '''NULL''' not null,
  name           varchar(50) default 'NULL'     null,
  ipAddress      varchar(40) default 'NULL'     null,
  availableSince datetime default 'NULL'        null,
  processorLoad  int default 'NULL'             null,
  diskspaceUsed  float default 'NULL'           null,
  diskspaceTotal float default 'NULL'           null,
  updated        datetime                       not null,
  created        datetime                       not null,
  constraint componentDetail_component_id_fk
  foreign key (componentId) references component (id)
    on delete cascade
);

create table if not exists log
(
  id                varchar(36)      not null
    primary key,
  componentDetailId varchar(36)      not null,
  type              varchar(25)      not null,
  error             text             not null,
  isResolved        bit default NULL null,
  created           datetime         not null,
  updated           datetime         not null,
  constraint log_componentdetail_id_fk
  foreign key (componentDetailId) references componentdetail (id)
    on delete cascade
);

create table if not exists user
(
  id       varchar(36)                not null
    primary key,
  username varchar(50) default 'NULL' null,
  password varchar(16) default 'NULL' null,
  isActive bit default b'1'           not null,
  updated  datetime                   not null,
  created  datetime                   not null
);

create table if not exists design
(
  id      varchar(36)                not null
    primary key,
  userId  varchar(36) default 'NULL' null,
  updated datetime                   not null,
  created datetime                   not null,
  constraint design_user_id_fk
  foreign key (userId) references user (id)
    on delete cascade
);

create table if not exists designcomponent
(
  id          varchar(36) not null
    primary key,
  designId    varchar(36) not null,
  componentId varchar(36) not null,
  updated     datetime    not null,
  created     datetime    not null,
  constraint designComponent_component_id_fk
  foreign key (componentId) references component (id)
    on delete cascade,
  constraint designComponent_design_id_fk
  foreign key (designId) references design (id)
    on delete cascade
);

