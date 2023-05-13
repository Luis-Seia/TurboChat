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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import mz.ac.isutc.lecc31.turbochat.databinding.ActivityLoginBinding;
import mz.ac.isutc.lecc31.turbochat.model.Usuario;
import mz.ac.isutc.lecc31.turbochat.repository.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.GONE);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editLoginEmail.getText().toString().isEmpty()) {
                    if (!binding.editLoginSenha.getText().toString().isEmpty()) {
                        usuario = new Usuario(binding.editLoginEmail.getText().toString(), binding.editLoginSenha.getText().toString());
                        validateUser(usuario);
                    } else {
                        Toast.makeText(LoginActivity.this, "preencha o email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textViewRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
                finish();
            }
        });
    }

    public void validateUser(Usuario usuario) {
        binding.progressBar.setVisibility(View.VISIBLE);
        auth = ConfigFirebase.getAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            openMasterActivity();
                            finish();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e) {
                                excecao = "Email ou senhas invalidos";
                            } catch (Exception e) {
                                excecao = "erro ao efectuar Login";
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void openMasterActivity() {
        startActivity(new Intent(this, MasterActivity.class));
    }
}