package br.senai.sp.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.agenda.modelo.Contato;

public class ContatoDAO extends SQLiteOpenHelper {

    public ContatoDAO(Context context) {
        super(context, "db_contato", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Código SQLite
        String sql = "CREATE TABLE tbl_contato(" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "linkedin TEXT NOT NULL, " +
                "fotoPerfil BLOB)";

        //Comando para executar o código SQL
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvar(Contato contato) {

        //Conexao com o banco de dados e o modo de "escrever" habilitada
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("email", contato.getEmail());
        dados.put("linkedin", contato.getLinkedin());
        dados.put("fotoPerfil", contato.getFoto());

        db.insert("tbl_contato", null, dados);
    }


    public List<Contato> getContatos() {

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM tbl_contato";

        Cursor c = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<>();

        while(c.moveToNext()){
            Contato contato = new Contato();
            contato.setId(c.getInt(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setEndereco(c.getString(c.getColumnIndex("endereco")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setLinkedin(c.getString(c.getColumnIndex("linkedin")));
            contato.setFoto(c.getBlob(c.getColumnIndex("fotoPerfil")));
            contatos.add(contato);
        }

        return contatos;
    }

    public void atualizar(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(contato.getId())};
        ContentValues dados = getContentValues(contato);

        db.update("tbl_contato", dados, "id = ?", params);
    }

    private ContentValues getContentValues(Contato contato) {
        ContentValues dados = new ContentValues();

        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("email", contato.getEmail());
        dados.put("linkedin", contato.getLinkedin());
        dados.put("fotoPerfil", contato.getFoto());

        return dados;
    }

    public void excluir(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(contato.getId())};

        db.delete("tbl_contato", "id = ?", params);
    }
}
