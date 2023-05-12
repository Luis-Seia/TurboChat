package mz.ac.isutc.lecc31.turbochat.model;

import java.util.ArrayList;

public class Conversa {
    private User amigo;//amigo sera a pessoa com quem o usuario esta a conversar
    private ArrayList<Mensagem> mensagens;

    public Conversa(User amigo, ArrayList<Mensagem> mensagems) {
        this.amigo = amigo;
        this.mensagens = mensagems;
    }

    public User getAmigo() {
        return amigo;
    }

    public void setAmigo(User amigo) {
        this.amigo = amigo;
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(ArrayList<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public Mensagem getLastMensagem(){
        return this.mensagens.get(mensagens.size()-1);
    }
}
