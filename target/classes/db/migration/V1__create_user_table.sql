create sequence tb_user_seq;
create table tb_user(
	id bigint primary key  not null DEFAULT nextval('tb_user_seq'),
	name varchar(100) not null,
  created_at timestamp not null,
  created_by varchar(60) not null,
  updated_at timestamp,
  updated_by varchar(60)
);
ALTER SEQUENCE tb_user_seq
OWNED BY tb_user.id;
