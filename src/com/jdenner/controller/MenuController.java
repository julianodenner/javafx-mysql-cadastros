package com.jdenner.controller;

import com.jdenner.controller.util.Alerta;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Juliano
 */
public class MenuController implements Initializable {

    @FXML
    private Label dataHora;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Task<Void> relogio = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException {
                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy, EEEEEE, HH:mm:ss");
                    updateMessage(sdf.format(new Date()).toLowerCase());
                    Thread.sleep(1000);
                }
            }
        };
        dataHora.textProperty().bind(relogio.messageProperty());

        Thread thread = new Thread(relogio);
        thread.setDaemon(true);
        thread.start();
    }

    private void abrir(String arquivo, String titulo) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/jdenner/view/" + arquivo + ".fxml"))));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            Alerta.erro("Erro ao abrir a janela", e);
        }
    }

    @FXML
    protected void abrirCidade(ActionEvent event) {
        abrir("Cidade", "Cadastro de cidades");
    }

    @FXML
    protected void abrirEstado(ActionEvent event) {
        abrir("Estado", "Cadastro de estados");
    }
}
