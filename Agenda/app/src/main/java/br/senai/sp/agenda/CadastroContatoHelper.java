package br.senai.sp.agenda;

import android.widget.EditText;

import br.senai.sp.agenda.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome,txtEndereco, txtTelefone, txtEmail, txtLinkedin;
    private Contato contato;

    public CadastroContatoHelper(CadastroActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);

        contato = new Contato();
    }

    public CadastroContatoHelper(AtualizarActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
    }

    public Contato getContato(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());

        return contato;
    }

    public void preencherFormulario(Contato contato) {
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());
        txtTelefone.setText(contato.getTelefone());

        this.contato = contato;
    }
}
