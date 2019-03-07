package br.senai.sp.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    private EditText txtNome,txtEndereco,txtTelefone,txtEmail,txtLinkedin;
    private CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(CadastroActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_salvar:
                Contato contato = helper.getContato();
                ContatoDAO dao = new ContatoDAO(CadastroActivity.this);
                if(helper.verificarCampos()){
                    dao.salvar(contato);
                    Toast.makeText(CadastroActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_limpar:
                limparCampos();
                Toast.makeText(CadastroActivity.this, "Limpo", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(CadastroActivity.this, "Nada", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void limparCampos(){
        this.txtNome = findViewById(R.id.txt_nome);
        this.txtEndereco = findViewById(R.id.txt_endereco);
        this.txtTelefone = findViewById(R.id.txt_telefone);
        this.txtEmail = findViewById(R.id.txt_email);
        this.txtLinkedin = findViewById(R.id.txt_linkedin);

        this.txtNome.setText("");
        this.txtEndereco.setText("");
        this.txtTelefone.setText("");
        this.txtEmail.setText("");
        this.txtLinkedin.setText("");
    }
}
