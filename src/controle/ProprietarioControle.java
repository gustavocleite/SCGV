package controle;

import java.util.ArrayList;
import modelos.IPropietarioCRUD;
import modelos.Proprietario;
import persistencia.ProprietarioDAO;

public class ProprietarioControle implements IPropietarioCRUD {

    IPropietarioCRUD proprietarioPercistencia = null;
    ValidaCPF validarCPF = null;
    ValidarEmail validaEmail = null;
    ValidarCNH validaCNH = null;
    ValidarTelefone validaTelefone = null;
    ValidarNome validaNome = null;

    public ProprietarioControle() {
        proprietarioPercistencia = new ProprietarioDAO();
        validarCPF = new ValidaCPF();
        validaEmail = new ValidarEmail();
        validaCNH = new ValidarCNH();
        validaTelefone = new ValidarTelefone();
        validaNome = new ValidarNome();
    }

    @Override
    public void Incluir(Proprietario objPropietario) throws Exception {
        try {
            // Remover máscara do CPF
            String cpfSemMascara = objPropietario.getCpf().replaceAll("[^0-9]", "");

            // Validar CPF vazio
            if (cpfSemMascara.equals("")) {
                throw new Exception("Digite um CPF!");
            }

            // Valida CPF
            if (!validarCPF.isCPF(objPropietario.getCpf())) {
                throw new Exception("CPF Inválido!");
            }
            // Valida se cpf ja existe
            if (proprietarioPercistencia.consultar(objPropietario.getCpf()) != null) {
                throw new Exception("Ja existe este CPF cadastrado!");
            }

            //Validar nome vazio
            if (objPropietario.getNomeCompleto().equals("")) {
                throw new Exception("Digite um Nome!");
            }
            // Valida nome
            if (!validaNome.validarNome(objPropietario.getNomeCompleto())) {
                throw new Exception("Nome inválido!");
            }

            // Valida email
            if (!validaEmail.validate(objPropietario.getEmail())) {
                throw new Exception("E-mail inválido!");
            }

            //Valida se foi selecionado um campo no combobox DDI
            if (objPropietario.getFone().getDdi().equals("")) {
                throw new Exception("Selecione um DDI!");
            }

            //Valida se foi selecionado um campo no combobox DDD
            if (objPropietario.getFone().getDdd().equals("")) {
                throw new Exception("Selecione um DDD!");
            }

            //Valida telefone esta vazio
            if (objPropietario.getFone().getNumeroTelefone().isBlank()) {
                throw new Exception("Digite um numero de Telefone!");
            }
            if (!validaTelefone.validarTelefone(objPropietario.getFone().getNumeroTelefone())) {
                throw new Exception("Numero de telefone Invalido!!");
            }

            //Valida cnh 
            if (!validaCNH.validarCNH(objPropietario.getNumeroCNH())) {
                throw new Exception("Numero CNH Invalida!");
            }

            // Valida se ja existe a CNH cadastrada !
            ArrayList<Proprietario> listaProprietario = proprietarioPercistencia.listagemDePropietario();
            for (Proprietario i : listaProprietario) {
                if (objPropietario.getNumeroCNH().equals(i.getNumeroCNH())) {
                    throw new Exception("CNH Ja cadastrada!!");
                }
            }

            // Valida se a categoria esta vazia
            if (objPropietario.getCategoriaCnh().equals("")) {
                throw new Exception("Selecione uma Categoria de CNH!");
            }

            // valida imagem da CNH
            if (objPropietario.getPathCNH() == null) {
                throw new Exception("Inclua uma imagem da CNH!");
            }

            //valida imagem do proprietario
            if (objPropietario.getPatchImagemProprietario() == null) {
                throw new Exception("Inclua uma imagem do proprietario!");
            }

            proprietarioPercistencia.Incluir(objPropietario);

        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }
    }

    @Override
    public void alterar(Proprietario proprietario) throws Exception {
        try {

            // Valida nome
            if (proprietario.getNomeCompleto() == null || proprietario.getNomeCompleto().trim().isEmpty()) {
                throw new Exception("Digite um nome!");
            }
            // Valida nome
            if (!validaNome.validarNome(proprietario.getNomeCompleto())) {
                throw new Exception("Nome inválido!");
            }
            // Valida email
            if (!validaEmail.validate(proprietario.getEmail())) {
                throw new Exception("CAMPO OBRIGATORIO! E-mail inválido!");
            }

            //Valida se foi selecionado um campo no combobox DDI
            if (proprietario.getFone().getDdi().equals("")) {
                throw new Exception("Selecione um DDI!");
            }

            //Valida se foi selecionado um campo no combobox DDD
            if (proprietario.getFone().getDdd().equals("")) {
                throw new Exception("Selecione um DDD!");
            }
            //Valida telefone esta vazio
            if (proprietario.getFone().getNumeroTelefone().isBlank()) {
                throw new Exception("Digite um numero de Telefone!");
            }
            if (!validaTelefone.validarTelefone(proprietario.getFone().getNumeroTelefone())) {
                throw new Exception("Numero de telefone Invalido!!");
            }

            //Valida cnh 
            if (!validaCNH.validarCNH(proprietario.getNumeroCNH())) {
                throw new Exception("Numero CNH Invalida!");
            }

            //Valida se foi selecionado um campo no combobox Categoria CNH
            if (proprietario.getCategoriaCnh().equals("")) {
                throw new Exception("CAMPO OBRIGATORIO! Selecione uma Categoria de CNH!");
            }

            // Envia para persistencia
            proprietarioPercistencia.alterar(proprietario);

        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }
    }

    @Override
    public ArrayList<Proprietario> listagemDePropietario() throws Exception {
        return proprietarioPercistencia.listagemDePropietario();
    }

    @Override
    public Proprietario consultar(String cpf) throws Exception {
        try {
            return proprietarioPercistencia.consultar(cpf);
        } catch (Exception erro) {
            throw new Exception("Metodo consultar Propietário - " + erro.getMessage());
        }
    }
}
