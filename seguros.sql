drop database seguros;

CREATE DATABASE seguros;

use seguros;

create table Usuarios (
	id 			int AUTO_INCREMENT,
	nombre 		varchar(50) not null,
	clave 		varchar(50) not null,
	rol			enum('Pacientes', 'medico', 'admin') not null,
	fecha_registro	datetime default current_timestamp,
	Primary key (id)
);

create table Pacientes (
	id 			int,
	telefono 	varchar(20),
	direccion 	varchar(100),
	Primary key (id)
);

create table Medicos (
	id 				int,
	especialidad	varchar(50) not null,
	costo_consulta	float not null,
	localidad		varchar(100) not null,
	horario_semanal	varchar(100),
	frecuencia_citas int not null,
	foto_url		varchar(255),
	resena			varchar(500),
	aprobado		boolean default false,
	Primary key (id)
);

create table Administradores (
	id				int,
	departamento	varchar(50),
	Primary	key (id)
);


create table Horarios (
	id 			int 	AUTO_INCREMENT,
	medico_id	int		not null,
	fecha		date 	not null,
	hora_inicio	time	not null,
	hora_fin	time	not null,
	disponible	boolean	default true,
	Primary key (id)
);

create table Citas (
	id 			int		AUTO_INCREMENT,
	paciente_id	int		not null,
	medico_id	int		not null,
	horario_id	int		not null,
	estado		enum('pendiente','confirmada','completada','cancelada') default 'pendiente',
	fecha_creacion datetime	default current_timestamp,
	Primary key (id)
);

alter table Pacientes add foreign key (id) references Usuarios(id);
alter table Medicos add foreign key (id) references Usuarios(id);
alter table Administradores add foreign key (id) references Usuarios(id);
alter table Horarios add foreign key (medico_id) references Medicos(id);
alter table Citas add foreign key (paciente_id) references Pacientes(id);
alter table Citas add foreign key (medico_id) references Medicos(id);
alter table Citas add foreign key (horario_id) references Horarios(id);


-- Insertar 5 Usuarioss con distintos roles
INSERT INTO Usuarios (nombre, clave, rol) VALUES
('Carlos Lopez', 'clave123', 'Pacientes'),
('Ana Perez', 'segura456', 'medico'),
('Javier Gomez', 'admin789', 'admin'),
('Lucia Fernandez', 'docpass', 'medico'),
('Pedro Sanchez', 'mypassword', 'Pacientes');

-- Insertar 5 Pacientess (deben coincidir con Usuarioss de rol 'Pacientes')
INSERT INTO Pacientes (id, telefono, direccion) VALUES
(1, '8888-1234', 'Calle 1, San Jose'),
(5, '8777-5678', 'Avenida 3, Alajuela');

-- Insertar 5 medicos (deben coincidir con Usuarioss de rol 'medico')
INSERT INTO Medicos (id, especialidad, costo_consulta, localidad, horario_semanal, frecuencia_citas, foto_url, resena, aprobado) VALUES
(2, 'Cardiologia', 40000, 'San Jose', 'Lunes a Viernes', 2, 'url1.jpg', 'Especialista en enfermedades del corazon.', true),
(4, 'Pediatria', 35000, 'Cartago', 'Martes y Jueves', 3, 'url2.jpg', 'Atencion a ninos y adolescentes.', false);

-- Insertar 5 administradores (deben coincidir con Usuarioss de rol 'admin')
INSERT INTO Administradores (id, departamento) VALUES
(3, 'Recursos Humanos');

-- Insertar 5 horarios para medicos
INSERT INTO Horarios (medico_id, fecha, hora_inicio, hora_fin) VALUES
(2, '2025-03-11', '08:00:00', '10:00:00'),
(2, '2025-03-12', '14:00:00', '16:00:00'),
(4, '2025-03-13', '09:00:00', '11:00:00'),
(4, '2025-03-14', '15:00:00', '17:00:00'),
(2, '2025-03-15', '07:00:00', '09:00:00');

-- Insertar 5 citas entre Pacientess y medicos en diferentes horarios
INSERT INTO Citas (paciente_id, medico_id, horario_id, estado) VALUES
(1, 2, 1, 'confirmada'),
(5, 4, 3, 'pendiente'),
(1, 2, 2, 'completada'),
(5, 4, 4, 'cancelada'),
(1, 2, 5, 'pendiente');

