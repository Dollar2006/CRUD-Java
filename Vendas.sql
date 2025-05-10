create database vendas;

use vendas;

create table clientes (
cli_id int primary key not null auto_increment,
cli_nome varchar(120) not null,
cli_CPF char(14) unique not null,
cli_email varchar(120) not null,
cli_tel char(11) not null,
cli_end varchar(120) not null,
cli_data date not null
);

alter table clientes add Unique (cli_CPF);
use vendas;

