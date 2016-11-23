package com.jdenner.controller;

import com.jdenner.controller.util.ExceptionValidacao;
import com.jdenner.controller.util.Alerta;
import com.jdenner.model.Cidade;
import com.jdenner.model.Estado;
import com.jdenner.model.Situacao;
import com.jdenner.model.dao.CidadeDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Juliano
 */
public class CidadeController extends Controller implements Initializable {

    private Cidade cidade;

    private Controller parent;

    @FXML
    private Pane pnGrade;

    @FXML
    private TableView<Cidade> tbGrade;

    @FXML
    private TableColumn<Cidade, String> colNome;

    @FXML
    private TableColumn<Cidade, String> colEstado;

    @FXML
    private TableColumn<Cidade, Situacao> colSituacao;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private Pagination pgPaginacao;

    @FXML
    private Pane pnFormulario;

    @FXML
    private TextField tfNome;

    @FXML
    private Label tfEstado;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private RadioButton rbInativo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
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
            double quantidadeRegistros = CidadeDao.quantidade(filtro);
            double quantidadeRegistrosPorPagina = 10;
            double quantidadePaginas = Math.ceil(quantidadeRegistros / quantidadeRegistrosPorPagina);
            if (quantidadeRegistros == 0) {
                pgPaginacao.setPageCount(1);
                tbGrade.setItems(null);
            } else {
                pgPaginacao.setPageCount((int) quantidadePaginas);
                tbGrade.setItems(CidadeDao.listar(filtro, paginaAtual, (int) quantidadeRegistrosPorPagina));
                tbGrade.refresh();
            }
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir a janela:", e);
        }
    }

    protected void setParent(Controller controller) {
        throw new UnsupportedOperationException("Operação não suportada ainda.");
    }

    protected void setObject(Object object) {
        if (object instanceof Estado) {
            Estado estado = (Estado) object;
            cidade.setEstado(estado);
            tfEstado.setText(estado.toString());
        }
    }

    @FXML
    protected void onActionNovo(ActionEvent event) {
        cidade = new Cidade();
        tfNome.setText("");
        tfEstado.setText("");
        rbAtivo.setSelected(true);
        rbInativo.setSelected(false);
        pnGrade.setVisible(false);
        pnFormulario.setVisible(true);
    }

    @FXML
    protected void onActionEditar(ActionEvent event) {
        if (tbGrade.getSelectionModel().isEmpty()) {
            Alerta.alerta("Selecione um registro.");
            return;
        }

        cidade = tbGrade.getSelectionModel().getSelectedItem();
        tfNome.setText(cidade.getNome());
        tfEstado.setText(cidade.getEstado().toString());
        rbAtivo.setSelected(cidade.getSituacao() == Situacao.ATIVO);
        rbInativo.setSelected(cidade.getSituacao() == Situacao.INATIVO);
        pnGrade.setVisible(false);
        pnFormulario.setVisible(true);
    }

    @FXML
    protected void onActionSelecionar(ActionEvent event) {
        throw new UnsupportedOperationException("Operação não suportada ainda.");
    }

    @FXML
    protected void onActionBtnEstado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jdenner/view/Estado.fxml"));
            Parent root = loader.load();
            EstadoController controller = (EstadoController) loader.getController();
            controller.setParent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de estados");
            stage.show();
        } catch (IOException e) {
            Alerta.erro("Erro ao abrir a janela:", e);
        }
    }

    @FXML
    protected void onActionPesquisar(ActionEvent event) {
        atualizarGrade(0);
    }

    @FXML
    protected void onActionBtnSalvar(ActionEvent event) {
        try {
            cidade.setNome(tfNome.getText());
            if (cidade.getEstado() == null) {
                throw new ExceptionValidacao("Estado inválido.");
            }
            cidade.setSituacao(rbAtivo.isSelected() ? Situacao.ATIVO : Situacao.INATIVO);
            CidadeDao.salvar(cidade);
            Alerta.sucesso("Salvo com sucesso.");
            atualizarGrade(0);
            pnGrade.setVisible(true);
            pnFormulario.setVisible(false);
        } catch (ExceptionValidacao ev) {
            Alerta.alerta("Dados inválidos:", ev);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar:", e);
        }
    }

    @FXML
    protected void onActionBtnCancelar(ActionEvent event) {
        pnGrade.setVisible(true);
        pnFormulario.setVisible(false);
    }

}
