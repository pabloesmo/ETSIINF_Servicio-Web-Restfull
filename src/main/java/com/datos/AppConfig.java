package com.datos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class AppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(com.recursos.UsuariosRecurso.class);
        return classes;
    }
}