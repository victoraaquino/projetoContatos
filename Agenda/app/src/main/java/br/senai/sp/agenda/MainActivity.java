package br.senai.sp.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.agenda.adapter.ContatosAdapter;
import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private ImageButton btnNovo;
    CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnNovo = findViewById(R.id.btn_novo_contato);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroContato = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(cadastroContato);
            }
        });

        listaContatos = findViewById(R.id.list_contatos);

        registerForContextMenu(listaContatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                Intent cadastro = new Intent(MainActivity.this, AtualizarActivity.class);
                cadastro.putExtra("contato", contato);
                startActivity(cadastro);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista(){
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> listaContato = dao.getContatos();
        dao.close();

        ContatosAdapter adapter = new ContatosAdapter(this, listaContato);
        listaContatos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;


        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);
        final ContatoDAO dao = new ContatoDAO(MainActivity.this);
        new AlertDialog.Builder(this).setTitle("Deletando Contato").
                setMessage("Tem certeza que deseja deletar esse contato?").
                setPositiveButton("sim", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.excluir(contato);
                        dao.close();
                        Toast.makeText(MainActivity.this, "Excluido com sucesso", Toast.LENGTH_SHORT).show();
                        carregarLista();
                    }
                }).setNegativeButton("não", null).show();

        return super.onContextItemSelected(item);
    }
}
