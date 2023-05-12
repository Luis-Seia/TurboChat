package mz.ac.isutc.lecc31.turbochat.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import mz.ac.isutc.lecc31.turbochat.helper.UsuarioFirebase;
import mz.ac.isutc.lecc31.turbochat.repository.ConfigFirebase;

public class User {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String foto;

    public User() {
    }

    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public User(String nome, String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
        DatabaseReference usuario = firebaseRef.child("usuarios").child( getId() );

        usuario.setValue( this );

    }

    public void atualizar(){

        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        DatabaseReference database = ConfigFirebase.getFirebaseDataBase();

        DatabaseReference usuariosRef = database.child("usuarios")
                .child( identificadorUsuario );

        Map<String, Object> valoresUsuario = converterParaMap();

        usuariosRef.updateChildren( valoresUsuario );

    }

    @Exclude
    public Map<String, Object> converterParaMap(){

        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail() );
        usuarioMap.put("nome", getNome() );
        usuarioMap.put("foto", getFoto() );

        return usuarioMap;

    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
