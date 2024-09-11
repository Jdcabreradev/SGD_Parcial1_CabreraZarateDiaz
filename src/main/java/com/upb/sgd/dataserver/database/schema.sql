-- Tabla principal para directorios y archivos
CREATE TABLE Directory (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- Identificación única
    name VARCHAR(255) NOT NULL,          -- Nombre del archivo/directorio
    owner INT NOT NULL,                  -- Identificación del dueño
    parent INT,                          -- Identificación del directorio padre (NULL si es raíz)

    -- Propiedades del archivo/directorio
    dirType ENUM('FILE', 'DIRECTORY') NOT NULL,  -- Tipo (archivo o directorio)
    permissions CHAR(6) NOT NULL,               -- Permisos en formato rw--rw
    size VARCHAR(5) NOT NULL,                   -- Tamaño (ej: 999MB, 10KB)
    contentType VARCHAR(10) NOT NULL,           -- Tipo de contenido (ej: "jpeg", "png", "txt")

    -- Metadatos temporales
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Fecha de última modificación

    -- Clave foránea a sí misma para los directorios padres
    FOREIGN KEY (parent) REFERENCES Directory(id)
);

-- Tabla para las etiquetas (tags) con relación muchos a muchos
CREATE TABLE Tag (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- Identificación única de la etiqueta
    name VARCHAR(100) NOT NULL UNIQUE   -- Nombre único de la etiqueta
);

-- Tabla intermedia para la relación muchos a muchos entre Directory y Tag
CREATE TABLE DirectoryTag (
    directory_id INT NOT NULL,          -- ID del directorio o archivo
    tag_id INT NOT NULL,                -- ID de la etiqueta

    -- Establecer claves foráneas
    FOREIGN KEY (directory_id) REFERENCES Directory(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES Tag(id) ON DELETE CASCADE,

    -- Establecer la clave primaria compuesta
    PRIMARY KEY (directory_id, tag_id)
);