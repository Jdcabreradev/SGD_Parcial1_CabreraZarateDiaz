package com.upb.sgd.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class Directory implements Serializable {

    // Identificación
    int id;
    String name;
    int owner;
    int parent;

    // Propiedades del archivo/directorio
    String path;
    DirType dirType;
    String permissions;
    String size;
    String contentType;

    // Metadatos temporales
    Date createdAt;
    Date updatedAt;

    // Detalles adicionales
    List<String> tags;
    Directory parentDirectory;
}
