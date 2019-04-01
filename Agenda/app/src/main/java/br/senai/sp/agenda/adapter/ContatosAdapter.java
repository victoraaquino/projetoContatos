package br.senai.sp.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.agenda.R;
import br.senai.sp.agenda.conversor.Imagem;
import br.senai.sp.agenda.modelo.Contato;

public class ContatosAdapter extends BaseAdapter {

    private List<Contato> contatos;
    private Context context;

    public ContatosAdapter(Context context, List<Contato> contatos)
    {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contato contato = contatos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_lista_contato, null);

        ImageView fotoContato = view.findViewById(R.id.img_contato_layout);
        TextView nomeContato = view.findViewById(R.id.txt_nome_contato);
        TextView telefoneContato = view.findViewById(R.id.txt_telefone_contato);

        nomeContato.setText(contato.getNome());
        telefoneContato.setText(contato.getTelefone());

        if(contato.getFoto() != null){
            fotoContato.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }

        return view;
    }
}
