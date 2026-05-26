INSERT INTO categoria (id, nombre, descripcion) VALUES
(1, 'Lacteos', 'Leche, quesos, yogures'),
(2, 'Carnes', 'Carnes rojas, pollo y cerdo'),
(3, 'Frutas y Verduras', 'Productos frescos'),
(4, 'Panaderia', 'Pan, galletas'),
(5, 'Bebidas', 'Jugos, gaseosas y agua'),
(6, 'Aseo', 'Limpieza del hogar'),
(7, 'Higiene Personal', 'Cuidado personal');

INSERT INTO proveedor (id, nombre, contacto, telefono, email, activo) VALUES
(1, 'Lacteos del Valle',     'Carlos Mendez', '3001234567', 'carlos@lacteosdelvalle.com', true),
(2, 'Frigorifico Central',   'Ana Torres',    '3109876543', 'ana@frigorifico.com',         true),
(3, 'Distribuidora FreshMart','Luis Garcia',  '3207654321', 'luis@freshmart.com',           true),
(4, 'Panaderia La Espiga',   'Maria Lopez',   '3154321098', 'maria@laespiga.com',           true),
(5, 'Bebidas Andinas',       'Pedro Ramirez', '3008765432', 'pedro@bebidasandinas.com',     true);

INSERT INTO producto (id, nombre, descripcion, precio, codigo_barras, categoria_id, proveedor_id, activo) VALUES
(1,  'Leche Entera 1L',      'Leche pasteurizada 1L',       2800.0,  '7702001001', 1, 1, true),
(2,  'Queso Campesino 500g', 'Queso fresco campesino 500g', 8500.0,  '7702001002', 1, 1, true),
(3,  'Yogur Natural 200g',   'Yogur natural 200g',          3200.0,  '7702001003', 1, 1, true),
(4,  'Pechuga de Pollo 1kg', 'Pechuga fresca 1kg',         12000.0,  '7702002001', 2, 2, true),
(5,  'Carne Molida 500g',    'Carne molida res 500g',       9500.0,  '7702002002', 2, 2, true),
(6,  'Tomate kg',            'Tomate chonto x kg',          3500.0,  '7702003001', 3, 3, true),
(7,  'Cebolla kg',           'Cebolla cabezona 1kg',        2800.0,  '7702003002', 3, 3, true),
(8,  'Manzana x6',           'Manzanas rojas paquete x6',   5500.0,  '7702003003', 3, 3, true),
(9,  'Pan Tajado',           'Pan tajado grande 500g',      3800.0,  '7702004001', 4, 4, true),
(10, 'Galletas Soda x3',     'Galletas soda paquete x3',    4200.0,  '7702004002', 4, 4, true),
(11, 'Jugo Naranja 1L',      'Jugo de naranja 1L',          4500.0,  '7702005001', 5, 5, true),
(12, 'Agua Mineral 600ml',   'Agua mineral 600ml',          1800.0,  '7702005002', 5, 5, true),
(13, 'Gaseosa 2L',           'Gaseosa cola 2L',             5800.0,  '7702005003', 5, 5, true),
(14, 'Jabon Rey 500g',       'Jabon en polvo 500g',         6500.0,  '7702006001', 6, 3, true),
(15, 'Shampoo 400ml',        'Shampoo 400ml',              12500.0,  '7702007001', 7, 3, true);

INSERT INTO inventario (id, producto_id, cantidad_estanteria, cantidad_bodega, stock_minimo, stock_maximo) VALUES
(1,1,25,80,20,200),(2,2,15,40,10,100),(3,3,20,60,15,150),
(4,4,10,30,8,80),(5,5,12,35,10,100),(6,6,18,50,15,120),
(7,7,22,60,15,120),(8,8,14,40,10,100),(9,9,30,90,25,200),
(10,10,25,70,20,150),(11,11,20,55,15,130),(12,12,40,120,30,250),
(13,13,18,50,15,120),(14,14,16,45,12,100),(15,15,10,25,8,80);

INSERT INTO cliente (id, nombre, apellido, email, telefono, password, fecha_registro, activo) VALUES
(1,'Juan','Perez','juan.perez@email.com','3001111111','juan123','2024-01-15',true),
(2,'Maria','Gomez','maria.gomez@email.com','3002222222','maria123','2024-02-20',true),
(3,'Carlos','Ruiz','carlos.ruiz@email.com','3003333333','carlos123','2024-03-10',true),
(4,'Ana','Martinez','ana.martinez@email.com','3004444444','ana1234','2024-03-25',true),
(5,'Pedro','Lopez','pedro.lopez@email.com','3005555555','pedro123','2024-04-05',true),
(6,'Sofia','Torres','sofia.torres@email.com','3006666666','sofia123','2024-04-18',true),
(7,'Diego','Herrera','diego.herrera@email.com','3007777777','diego123','2024-05-01',true),
(8,'Valentina','Castro','vale.castro@email.com','3008888888','vale1234','2024-05-14',true);

INSERT INTO compra (id, cliente_id, fecha, total, metodo_pago, cajero) VALUES
(1,1,'2024-06-01 09:30:00',18100.0,'EFECTIVO','cajero1'),
(2,2,'2024-06-01 10:15:00',25300.0,'TARJETA','cajero1'),
(3,3,'2024-06-02 11:00:00',15600.0,'EFECTIVO','cajero2'),
(4,1,'2024-06-05 08:45:00',22400.0,'EFECTIVO','cajero1'),
(5,4,'2024-06-07 14:30:00',31200.0,'TARJETA','cajero2'),
(6,2,'2024-06-10 16:00:00',18700.0,'NEQUI','cajero1'),
(7,5,'2024-06-12 09:00:00',27500.0,'EFECTIVO','cajero2'),
(8,1,'2024-06-15 10:30:00',19800.0,'TARJETA','cajero1'),
(9,3,'2024-06-18 12:00:00',16400.0,'EFECTIVO','cajero2'),
(10,6,'2024-06-20 15:30:00',35600.0,'TARJETA','cajero1');

INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1,1,1,2,2800.0,5600.0),(2,1,9,1,3800.0,3800.0),(3,1,12,5,1800.0,9000.0),
(4,2,4,1,12000.0,12000.0),(5,2,6,2,3500.0,7000.0),(6,2,7,1,2800.0,2800.0),
(7,3,1,1,2800.0,2800.0),(8,3,3,2,3200.0,6400.0),(9,3,11,1,4500.0,4500.0),
(10,4,5,1,9500.0,9500.0),(11,4,6,2,3500.0,7000.0),(12,4,12,3,1800.0,5400.0),
(13,5,2,1,8500.0,8500.0),(14,5,4,1,12000.0,12000.0),(15,5,10,1,4200.0,4200.0),
(16,6,1,2,2800.0,5600.0),(17,6,8,1,5500.0,5500.0),(18,6,12,4,1800.0,7200.0),
(19,7,4,1,12000.0,12000.0),(20,7,7,2,2800.0,5600.0),(21,7,13,1,5800.0,5800.0),
(22,8,1,2,2800.0,5600.0),(23,8,9,2,3800.0,7600.0),(24,8,3,2,3200.0,6400.0),
(25,9,6,2,3500.0,7000.0),(26,9,7,1,2800.0,2800.0),(27,9,12,4,1800.0,7200.0),
(28,10,14,2,6500.0,13000.0),(29,10,15,1,12500.0,12500.0),(30,10,1,2,2800.0,5600.0);

-- Reiniciar secuencias de ID (H2) tras inserts con IDs fijos en data.sql
ALTER TABLE categoria ALTER COLUMN id RESTART WITH 8;
ALTER TABLE proveedor ALTER COLUMN id RESTART WITH 6;
ALTER TABLE producto ALTER COLUMN id RESTART WITH 16;
ALTER TABLE inventario ALTER COLUMN id RESTART WITH 16;
ALTER TABLE cliente ALTER COLUMN id RESTART WITH 9;
ALTER TABLE compra ALTER COLUMN id RESTART WITH 11;
ALTER TABLE detalle_compra ALTER COLUMN id RESTART WITH 31;

-- Tabla administrador (agregar al schema si no existe aún)
CREATE TABLE IF NOT EXISTS administrador (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    fecha_registro  DATE         NOT NULL DEFAULT CURRENT_DATE,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Admin: Luchos / lmramirezb@gmail.com  → contraseña: admin123
INSERT INTO administrador (nombre, email, password, fecha_registro, activo)
SELECT 'Luchos',
       'lmramirezb@gmail.com',
       'admin123',
       CURRENT_DATE,
       TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM administrador WHERE email = 'lmramirezb@gmail.com'
);