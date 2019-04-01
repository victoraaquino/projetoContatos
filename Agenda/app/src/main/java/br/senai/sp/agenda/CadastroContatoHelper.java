package br.senai.sp.agenda;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;

import br.senai.sp.agenda.conversor.Imagem;
import br.senai.sp.agenda.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome,txtEndereco, txtTelefone, txtEmail, txtLinkedin;
    private ImageView imgFoto;
    private Contato contato;

    public CadastroContatoHelper(CadastroActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        imgFoto = activity.findViewById(R.id.img_contato_cadastro);

        contato = new Contato();
    }

    public CadastroContatoHelper(AtualizarActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        imgFoto = activity.findViewById(R.id.img_contato_atualizar);
    }

    public Contato getContato(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());

        Bitmap bitmap = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 256,256,true);

        contato.setFoto(Imagem.bitmapToArray(bitmapReduzido));

        return contato;
    }

    public void preencherFormulario(Contato contato) {
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());
        txtTelefone.setText(contato.getTelefone());

        if(contato.getFoto() != null){
            imgFoto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }

        this.contato = contato;
    }

    public boolean verificarCampos(){
        if(txtNome.getText().toString().isEmpty()){
            return false;
        }
        if(txtEndereco.getText().toString().isEmpty()){
            return false;
        }
        if(txtEmail.getText().toString().isEmpty()){
            return false;
        }
        if(txtLinkedin.getText().toString().isEmpty()){
            return false;
        }
        if(txtTelefone.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}
