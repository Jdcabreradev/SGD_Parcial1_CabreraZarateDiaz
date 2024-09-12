package com.upb.sgd.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class Directory implements Serializable {

    // Identificación
    public int id;
    public String name;
    public int owner;
    public int parent;

    // Propiedades del archivo/directorio
    public String path;
    public DirType dirType;
    public String permissions;
    public String size;
    public String contentType;

    // Metadatos temporales
    public Date createdAt;
    public Date updatedAt;

    // Detalles adicionales
    public List<String> tags;
    public Directory parentDirectory;

    @Override
    public String toString() {
        return this.name; // Return the name of the document
    }
}
