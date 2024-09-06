package com.upb.sgd.shared.domain;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Directory{
    List<Directory> children;

    public Folder(){
        super();
        this.type = 1;
        this.children = new ArrayList<>();
    }

    // Método para buscar un directorio por ID en el árbol
    private Directory findById(int id) {
        if (this.id == id) {
            return this;
        }
        for (Directory child : this.children) {
            if (child.type == 1) {
                Directory found = ((Folder) child).findById(id);
                if (found != null) {
                    return found;
                }
            } else if (child.id == id) {
                return child;
            }
        }
        return null;
    }

    // Método para insertar un único directorio basado en el campo parent
    public void insert(Directory dir) {
        if (dir != null) {
            // Si el directorio a insertar no tiene un padre (parent == 0), es la raíz
            if (dir.parent == 0) {
                this.children.add(dir);
                dir.parentDirectory = this;  // Establecemos que el padre del nuevo directorio es la raíz
            } else {
                // Buscar el directorio padre por su ID
                Directory parentDir = findById(dir.parent);
                if (parentDir != null && parentDir.type == 1) {
                    Folder parentFolder = (Folder) parentDir;
                    parentFolder.children.add(dir);  // Añadimos el directorio a los hijos del padre
                    dir.parentDirectory = parentFolder;  // Establecemos el padre del nuevo directorio
                } else {
                    System.out.println("No se encontró el directorio padre con ID: " + dir.parent);
                }
            }
        }
    }

    // Método para insertar varios directorios a la vez
    public void insert(List<Directory> dirs) {
        if (dirs != null && !dirs.isEmpty()) {
            for (Directory dir : dirs) {
                insert(dir);  // Reutilizamos el método de inserción única
            }
        }
    }

    // Método para eliminar un directorio por ID
    public boolean remove(int id) {
        Directory dirToRemove = findById(id);
        if (dirToRemove!=null){
            Folder dirToRemoveParent = (Folder) dirToRemove.parentDirectory;
            return dirToRemoveParent.children.remove(dirToRemove);
        }
        return false;
    }

    // Método para listar todos los archivos y directorios en la carpeta actual
    public List<Directory> listChildren() {
        return this.children;  // (NO) Retorna una copia de la lista de hijos
    }
}