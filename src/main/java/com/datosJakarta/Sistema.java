package com.datosJakarta;

public class Sistema {
    private static int id_usuario=0;
    private static int id_vino=0;
    private static int id_seguidor=0;

    public static int getId_usuario() {
        id_usuario++;
        return id_usuario;
    }

    public static int getId_vino() {
        id_vino++;
        return id_vino;
    }

    public static int getId_seguidor() {
        id_seguidor++;
        return id_seguidor;
    }

}
