package br.senai.sp.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private ImageButton btnNovo;

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
    }

    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista(){
        ContatoDAO dao = new ContatoDAO(this);

        List<Contato> listaContato = dao.getFilmes();

        dao.close();

        listaContatos = findViewById(R.id.list_contatos);

        ArrayAdapter<Contato> listaFilmesAdapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, listaContato);

        listaContatos.setAdapter(listaFilmesAdapter);
    }
}
