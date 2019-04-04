package br.senai.sp.agenda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.senai.sp.agenda.dao.ContatoDAO;
import br.senai.sp.agenda.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    public static final int GALERIA_REQUEST = 99;
    public static final int CAMERA_REQUEST = 100;
    private EditText txtNome,txtEndereco,txtTelefone,txtEmail,txtLinkedin;
    private ImageButton btnCamera, btnGaleria;
    private ImageView imgContato;
    private CadastroContatoHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(CadastroActivity.this);

        btnCamera = findViewById(R.id.btnimg_camera);
        btnGaleria = findViewById(R.id.btnimg_pasta);
        imgContato = findViewById(R.id.img_contato_cadastro);

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

                Uri fotoUri = FileProvider.getUriForFile(CadastroActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) try {

            if (requestCode == GALERIA_REQUEST) {


                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imgContato.setImageBitmap(bitmap);
            }

            if (requestCode == CAMERA_REQUEST) {

                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                imgContato.setImageBitmap(bitmapReduzido);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                }else{
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.menu_limpar:
                limparCampos();
                Toast.makeText(CadastroActivity.this, "Limpo", Toast.LENGTH_SHORT).show();
                break;

            default:
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
