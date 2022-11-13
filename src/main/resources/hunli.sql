create table hunli
(
	id int auto_increment,
	name varchar(100) null,
	person int null comment '人数',
	presence int null comment '/**
     * 1。很高兴参加
     * 2。很抱歉无法参加
     */',
	comment varchar(300) null,
	created_at datetime null default CURRENT_TIMESTAMP,
	updated_at datetime null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	constraint hunli_pk
		primary key (id)
);

