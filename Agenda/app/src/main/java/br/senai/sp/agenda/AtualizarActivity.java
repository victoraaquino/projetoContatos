package br.senai.sp.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class AtualizarActivity extends AppCompatActivity {
    public static final int GALERIA_REQUEST = 101;
    public static final int CAMERA_REQUEST = 102;
    private ImageButton btnCamera, btnGaleria;
    private ImageView imgContato;
    private String caminhoFoto;
    private CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        helper = new CadastroContatoHelper(AtualizarActivity.this);

        final Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");
        if(contato != null){
            helper.preencherFormulario(contato);
        }

        btnCamera = findViewById(R.id.btnimg_camera);
        btnGaleria = findViewById(R.id.btnimg_pasta);
        imgContato = findViewById(R.id.img_contato_atualizar);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                intentGaleria.setType("image/*");
                startActivityForResult(intentGaleria, GALERIA_REQUEST);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String nomeImagem = "/IMG_" + System.currentTimeMillis() + ".jpg";

                caminhoFoto = getExternalFilesDir(null) + nomeImagem;

                File arquivoFoto = new File(caminhoFoto);

                Uri fotoUri = FileProvider.getUriForFile(AtualizarActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            try {

                if(requestCode == GALERIA_REQUEST){
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    imgContato.setImageBitmap(bitmap);
                }

                if(requestCode == CAMERA_REQUEST){
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap,256,256,true);
                    imgContato.setImageBitmap(bitmapReduzido);

                }

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
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
                }else{
                    Toast.makeText(AtualizarActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

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
                                Toast.makeText(AtualizarActivity.this, "Excluido com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).setNegativeButton("não", null).show();

                break;

            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
