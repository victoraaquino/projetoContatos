package br.senai.sp.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class AtualizarActivity extends AppCompatActivity {

    private CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        helper = new CadastroContatoHelper(AtualizarActivity.this);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");
        if(contato != null){
            helper.preencherFormulario(contato);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_atualizar, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_salvar:
                Contato contato = helper.getContato();
                 ContatoDAO dao = new ContatoDAO(AtualizarActivity.this);

                //Toast.makeText(AtualizarActivity.this, String.valueOf(contato.getId()), Toast.LENGTH_SHORT).show();

                if(helper.verificarCampos()){
                    dao.atualizar(contato);
                    dao.close();
                    Toast.makeText(AtualizarActivity.this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }

                Toast.makeText(AtualizarActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_excluir:
                final Contato contato2 = helper.getContato();
                final ContatoDAO dao2 = new ContatoDAO(AtualizarActivity.this);
                new AlertDialog.Builder(this).setTitle("Deletando Contato").
                        setMessage("Tem certeza que deseja deletar esse contato?").
                        setPositiveButton("sim", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dao2.excluir(contato2);
                                Toast.makeText(AtualizarActivity.this, "Excluir", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).setNegativeButton("não", null).show();

                break;

            default:
                Toast.makeText(AtualizarActivity.this, "Nada", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
