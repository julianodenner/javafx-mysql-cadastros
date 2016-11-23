package com.jdenner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 *
 * @author Juliano
 */
public abstract class Controller {

    protected abstract void atualizarGrade(int paginaAtual);

    protected abstract void setParent(Controller controller);
    
    protected abstract void setObject(Object object);

    protected abstract void onActionNovo(ActionEvent event);

    protected abstract void onActionEditar(ActionEvent event);

    protected abstract void onActionSelecionar(ActionEvent event);

    protected abstract void onActionPesquisar(ActionEvent event);

    protected abstract void onActionBtnSalvar(ActionEvent event);

    protected abstract void onActionBtnCancelar(ActionEvent event);
}
