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

                dao.salvar(contato);
                Toast.makeText(CadastroActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                finish();
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
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtLinkedin.setText("");
    }
}
