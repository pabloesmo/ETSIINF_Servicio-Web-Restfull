package com.datos;
import java.util.ArrayList;

public class ListaVinos {
    private static ArrayList<Vino> vinos = new ArrayList<Vino>();


    public static Vino getVino(String nombre) {
        for (int i=0; i < vinos.size(); i++) {
            if (vinos.get(i).getNombre().equals(nombre)) {
                return vinos.get(i);
            }
        }
        return null;
    }

    public static boolean existeVino(String nombre){
        for (int i=0; i < vinos.size(); i++) {
            if (vinos.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public static void addVino(Vino vino){
        vinos.add(vino);
    }

    public static void updateVino(Vino vino){
        for (int i=0; i < vinos.size(); i++) {
            if (vinos.get(i).getNombre().equals(vino.getNombre())) {
                vinos.get(i).setBodega(vino.getBodega());
                vinos.get(i).setAñada(vino.getAñada());
                vinos.get(i).setDenominacion(vino.getDenominacion());
                vinos.get(i).setTipo(vino.getTipo());
                vinos.get(i).setTiposUva(vino.getTiposUva());
            }
        }
        System.out.println("Vino actualizado!!");
    }

    public static ArrayList<Vino> getVinos(){
        return vinos;
    }

    public static void deleteVino(String nombre){
        for (int i=0; i < vinos.size(); i++) {
            if (vinos.get(i).getNombre().equals(nombre)) {
                // vinos.remove(vinos.get(i));
                vinos.remove(i);
            }
        }
    }

    public static String toStringvinos() {
        String res = "\n";
        for (int i=0; i<vinos.size(); i++) {
            res += vinos.get(i).toString() + "\n";
        }
        return res;
    }
}