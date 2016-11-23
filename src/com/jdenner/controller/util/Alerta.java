package com.jdenner.controller.util;

import javafx.scene.control.Alert;

/**
 *
 * @author Juliano
 */
public class Alerta {

    public static void erro(String titulo, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(e.getMessage());
        alert.show();
    }

    public static void alerta(String titulo, Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(titulo);
        alert.setContentText(e.getMessage());
        alert.show();
    }

    public static void alerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(mensagem);
        alert.show();
    }

    public static void sucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(mensagem);
        alert.show();
    }

}
