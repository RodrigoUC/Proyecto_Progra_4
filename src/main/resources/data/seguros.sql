drop database seguros;

create database seguros;

use seguros;

create table if not exists Usuarios (
	id 			varchar(40),
	nombre 		varchar(50) not null,
	clave 		varchar(250) not null,
	rol			enum('paciente', 'medico', 'admin') not null,
	fecha_registro	datetime default current_timestamp,
	Primary key (id)
);

create table if not exists Pacientes (
	id 			varchar(40),
	telefono 	varchar(20),
	direccion 	varchar(100),
	Primary key (id)
);

create table if not exists Medicos (
	id 				varchar(40),
	especialidad	varchar(50) not null,
	localidad		varchar(75) not null,
	hospital		varchar(100) not null,
	costo_consulta	float not null,
	frecuencia_citas int not null,
	foto			varchar(255) not null,
	presentacion	text,
	Primary key (id)
);

create table if not exists Administradores (
	id				varchar(40),
	departamento	varchar(50),
	Primary	key (id)
);

create table if not exists Slots (
	id			int AUTO_INCREMENT,
	medico_id	varchar(40) not null,
	dia			int not null,
	hora_inicio	time not null,
	hora_fin	time not null,
	Primary Key (id)
);

create table if not exists Citas (
	id 			int		AUTO_INCREMENT,
	paciente_id	varchar(40)		not null,
	medico_id	varchar(40)		not null,
	slot_id		int		not null,
	estado		enum('pendiente','confirmada','completada','cancelada') default 'pendiente',
	notas		text,
	fecha_creacion datetime	default current_timestamp,
	reservada	boolean default false,
	Primary key (id)
);

alter table Pacientes add foreign key (id) references Usuarios(id) on delete cascade;
alter table Medicos add foreign key (id) references Usuarios(id) on delete cascade;
alter table Administradores add foreign key (id) references Usuarios(id) on delete cascade;
alter table Slots add foreign key (medico_id) references Medicos(id) on delete cascade;
alter table Citas add foreign key (paciente_id) references Pacientes(id) on delete cascade;
alter table Citas add foreign key (medico_id) references Medicos(id) on delete cascade;
alter table Citas add foreign key (slot_id) references Slots(id) on delete cascade;

-- Contraseña 123
-- Inserts para Usuarios
INSERT INTO Usuarios (id, nombre, clave, rol) VALUES
('u001', 'Juan Perez', '$2a$12$Ds5LIAfmJYEyGyIAeHV7PeseUsjvxKK8kHzq3e2.uQvcDbp27Ggx2', 'paciente'),
('u002', 'Maria Gomez', '$2a$12$Ds5LIAfmJYEyGyIAeHV7PeseUsjvxKK8kHzq3e2.uQvcDbp27Ggx2', 'medico'),
('u003', 'Carlos Ramirez', '$2a$12$Ds5LIAfmJYEyGyIAeHV7PeseUsjvxKK8kHzq3e2.uQvcDbp27Ggx2', 'admin'),
('u004', 'Ana Martinez', '$2a$12$Ds5LIAfmJYEyGyIAeHV7PeseUsjvxKK8kHzq3e2.uQvcDbp27Ggx2', 'paciente'),
('u005', 'Pedro Jimenez', '$2a$12$Ds5LIAfmJYEyGyIAeHV7PeseUsjvxKK8kHzq3e2.uQvcDbp27Ggx2', 'medico');

-- Inserts para Pacientes
INSERT INTO Pacientes (id, telefono, direccion) VALUES
('u001', '8888-1111', 'Calle 1, San Jose'),
('u004', '8888-2222', 'Calle 2, Heredia');

-- Inserts para Medicos
INSERT INTO Medicos (id, especialidad, localidad, hospital, costo_consulta, frecuencia_citas, foto, presentacion) VALUES
('u002', 'Cardiologia', 'San Jose', 'Hospital San Juan de Dios', 50000, 60,'/images/medica.png', 'Es importante mantener un buen ritmo cardiaco.'),
('u005', 'Dermatologia', 'Alajuela', 'Hospital San Rafael', 45000, 30, '/images/medico.png', 'Siempre en busca del cuidado de la piel para mis pacientes.');

-- Inserts para Administradores
INSERT INTO Administradores (id, departamento) VALUES
('u003', 'Recursos Humanos');

-- Inserts para Slot (Horarios de Medicos)
INSERT INTO Slots (medico_id, dia, hora_inicio, hora_fin) VALUES
('u002', 1, '08:00:00', '10:00:00'),
('u002', 3, '14:00:00', '16:00:00'),
('u005', 2, '09:00:00', '11:00:00'),
('u005', 4, '13:00:00', '15:00:00'),
('u005', 5, '15:00:00', '17:00:00');

-- Inserts para Citas
INSERT INTO Citas (paciente_id, medico_id, slot_id, estado, notas) VALUES
('u001', 'u002', 1, 'confirmada', 'Chequeo anual'),
('u001', 'u005', 3, 'pendiente', 'Revisar erupcion en la piel'),
('u004', 'u002', 2, 'completada', 'Control de presion arterial'),
('u004', 'u005', 5, 'cancelada', 'Cambio de planes'),
('u001', 'u005', 4, 'confirmada', 'Consulta de rutina');


