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

import mz.ac.isutc.lecc31.turbochat.R;
import mz.ac.isutc.lecc31.turbochat.databinding.ActivityCadastroBinding;
import mz.ac.isutc.lecc31.turbochat.helper.Base64Custom;
import mz.ac.isutc.lecc31.turbochat.model.User;
import mz.ac.isutc.lecc31.turbochat.repository.ConfigFirebase;

public class CadastroActivity extends AppCompatActivity {
    ActivityCadastroBinding binding;
    private User user;
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
                    user = new User(binding.editNomeRegistar.getText().toString(), binding.editEmailRegistar.getText().toString(), binding.editSenhaRegistar.getText().toString());
                    saveUser(user);
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

    public void saveUser(User user){
        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    String idUser = Base64Custom.codificarBase64(user.getEmail());
                    user.setIdUser(idUser);
                    user.saveFromFirebase();

                    startActivity(new Intent(CadastroActivity.this, MasterActivity.class));
                    finish();
                }else{
                    String textExcecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        textExcecao = "digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        textExcecao = "digite um email valido";
                    }catch (FirebaseAuthUserCollisionException e){
                        textExcecao = "Esta conta ja foi cadastrada";
                    } catch (Exception e) {
                        textExcecao = "Erro ao cadastrar usuario";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, textExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}