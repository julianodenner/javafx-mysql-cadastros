package com.jdenner.controller;

import com.jdenner.controller.util.ExceptionValidacao;
import com.jdenner.controller.util.Alerta;
import com.jdenner.model.Estado;
import com.jdenner.model.Situacao;
import com.jdenner.model.dao.EstadoDao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Juliano
 */
public class EstadoController implements Initializable {

    @FXML
    private Pane pnGrade;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSelecionar;

    @FXML
    private TableView<Estado> tbGrade;

    @FXML
    private TableColumn<Estado, String> colNome;

    @FXML
    private TableColumn<Estado, String> colSigla;

    @FXML
    private TableColumn<Estado, Situacao> colSituacao;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private Pagination pgPaginacao;

    @FXML
    private Pane pnFormulario;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSigla;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup tgSituacao;

    @FXML
    private RadioButton rbInativo;

    private Estado estado;
    private CidadeController cidadeController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        pgPaginacao.setPageFactory(new Callback<Integer, Node>() {

            @Override
            public Node call(Integer pagina) {
                atualizarGrade(pagina);
                return tbGrade;
            }

        });
    }

    protected void atualizarGrade(int paginaAtual) {
        try {
            String filtro = tfPesquisar.getText();
            double quantidadeRegistros = EstadoDao.quantidade(filtro);
            double quantidadeRegistrosPorPagina = 10;
            double quantidadePaginas = Math.ceil(quantidadeRegistros / quantidadeRegistrosPorPagina);
            if (quantidadeRegistros == 0) {
                pgPaginacao.setPageCount(1);
                tbGrade.setItems(null);
            } else {
                pgPaginacao.setPageCount((int) quantidadePaginas);
                tbGrade.setItems(EstadoDao.listar(filtro, paginaAtual, (int) quantidadeRegistrosPorPagina));
                tbGrade.refresh();
            }
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir a janela", e);
        }
    }

    protected void setCidadeController(CidadeController cidadeController) {
        this.cidadeController = cidadeController;
        this.btnSelecionar.setVisible(true);
    }

    @FXML
    protected void onActionNovo(ActionEvent event) {
        estado = new Estado();
        tfNome.setText("");
        tfSigla.setText("");
        rbAtivo.setSelected(true);
        rbInativo.setSelected(false);
        pnGrade.setVisible(false);
        pnFormulario.setVisible(true);
    }

    @FXML
    protected void onActionEditar(ActionEvent event) {
        if (tbGrade.getSelectionModel().isEmpty()) {
            Alerta.alerta("Selecione um registro");
            return;
        }

        estado = tbGrade.getSelectionModel().getSelectedItem();
        tfNome.setText(estado.getNome());
        tfSigla.setText(estado.getSigla());
        rbAtivo.setSelected(estado.getSituacao() == Situacao.ATIVO);
        rbInativo.setSelected(estado.getSituacao() == Situacao.INATIVO);
        pnGrade.setVisible(false);
        pnFormulario.setVisible(true);
    }

    @FXML
    protected void onActionSelecionar(ActionEvent event) {
        if (tbGrade.getSelectionModel().isEmpty()) {
            Alerta.alerta("Selecione um registro");
            return;
        }

        estado = tbGrade.getSelectionModel().getSelectedItem();
        
        if(estado.getSituacao() == Situacao.INATIVO){
            Alerta.alerta("Estado inativo");
            return;
        }
        cidadeController.setEstado(estado);

        Stage stage = (Stage) pnGrade.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onActionPesquisar(ActionEvent event) {
        atualizarGrade(0);
    }

    @FXML
    protected void onActionBtnSalvar(ActionEvent event) {
        try {
            estado.setNome(tfNome.getText());
            estado.setSigla(tfSigla.getText());
            estado.setSituacao(rbAtivo.isSelected() ? Situacao.ATIVO : Situacao.INATIVO);
            EstadoDao.salvar(estado);
            Alerta.sucesso("Salvo com sucesso");
            atualizarGrade(0);
            pnGrade.setVisible(true);
            pnFormulario.setVisible(false);
        } catch (ExceptionValidacao ev) {
            Alerta.alerta("Dados inválidos", ev);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar", e);
        }
    }

    @FXML
    protected void onActionBtnCancelar(ActionEvent event) {
        pnGrade.setVisible(true);
        pnFormulario.setVisible(false);
    }
}
