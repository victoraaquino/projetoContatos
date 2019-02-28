package br.senai.sp.agenda;

import android.widget.EditText;

import br.senai.sp.agenda.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome,txtEndereco, txtTelefone, txtEmail, txtLinkedin;

    public CadastroContatoHelper(CadastroActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_linkedin);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
    }

    public Contato getContato(){
        Contato contato = new Contato();
        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());

        return contato;
    }

    public Boolean verificarCampos(){
        return true;
    }
}
