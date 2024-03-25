package com.datos;
import java.util.ArrayList;

public class ListaUsuarios {
    private static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();


    public static Usuario getUsuario(int id) {
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public static Usuario getUsuario(String nombre) {
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(nombre)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public static boolean existeUsuario(String nombre){
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existeUsuario(int id){
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId()== id) {
                return true;
            }
        }
        return false;
    }

    public static void addUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public static void updateUsuario(Usuario usuario){
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(usuario.getNombre())) {
                usuarios.get(i).setfechaNacimiento(usuario.getfechaNacimiento());
                usuarios.get(i).setEmail(usuario.getEmail());
            }
        }
        System.out.println("Usuario actualizado!!");
    }

    public static ArrayList<Usuario> getUsuarios(){
        return usuarios;
    }

    public static void deleteUsuario(String nombre){
        for (int i=0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(nombre)) {
                // usuarios.remove(usuarios.get(i));
                usuarios.remove(i);
            }
        }
    }

    public static String toStringUsuarios() {
        String res = "\n";
        for (int i=0; i<usuarios.size(); i++) {
            res += usuarios.get(i).toString() + "\n";
        }
        return res;
    }

   
}