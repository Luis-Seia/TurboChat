package mz.ac.isutc.lecc31.turbochat.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import mz.ac.isutc.lecc31.turbochat.databinding.ActivityCadastroBinding;
import mz.ac.isutc.lecc31.turbochat.helper.Base64Custom;
import mz.ac.isutc.lecc31.turbochat.helper.UsuarioFirebase;
import mz.ac.isutc.lecc31.turbochat.model.Usuario;
import mz.ac.isutc.lecc31.turbochat.repository.ConfigFirebase;

public class CadastroActivity extends AppCompatActivity {
    ActivityCadastroBinding binding;
    private Usuario usuario;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validationFields()){
                    usuario = new Usuario(binding.editNomeRegistar.getText().toString(), binding.editEmailRegistar.getText().toString(), binding.editSenhaRegistar.getText().toString());
                    saveUser(usuario);
                }
            }
        });

        binding.textViewFalcaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public boolean validationFields(){
        boolean validation = false;
        if( !binding.editNomeRegistar.getText().toString().isEmpty()){
            if (!binding.editEmailRegistar.getText().toString().isEmpty()){
                if(!binding.editSenhaRegistar.getText().toString().isEmpty()){
                    validation = true;
                }else{
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
        return validation;
    }

    public void saveUser(Usuario usuario){
        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar usuário!",
                            Toast.LENGTH_SHORT).show();
                    UsuarioFirebase.atualizarNomeUsuario( usuario.getNome() );
                    finish();

                    try {

                        String identificadorUsuario = Base64Custom.codificarBase64( usuario.getEmail() );
                        usuario.setId( identificadorUsuario );
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}