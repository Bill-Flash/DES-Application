#创建diary表
create table `diary`(
    `did` int(11) NOT NULL auto_increment primary key comment 'index primary key',
	`title` char(30) not null collate utf8mb4_unicode_ci comment 'title',
	`key_en` text not null collate utf8mb4_unicode_ci comment 'password_sha1',
	`weather` int not null comment 'the index of weather',
	`date` date not null comment 'date',
	`content` text not null comment 'encrypted_content'
)engine=InnoDB default charset=utf8mb4 collate utf8mb4_unicode_ci comment 'diary_encrypted_password_and_content';
