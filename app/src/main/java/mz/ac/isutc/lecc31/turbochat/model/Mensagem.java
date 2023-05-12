package mz.ac.isutc.lecc31.turbochat.model;

import java.util.Date;

public class Mensagem {
    private String mensagem, hora;
    private User remetente;//sera a pessoa que envia a mensagem;


    public Mensagem(String mensagem,User remetente) {
        this.mensagem = mensagem;
        this.remetente = remetente;
        Date d = new Date();
        this.hora = d.getHours()+":"+d.getMinutes();
    }

    //Nao criei setters porque mensagens uma vez criadas nao sao alteradas


    public String getMensagem() {
        return mensagem;
    }

    public String getHora() {
        return hora;
    }

    public User getRemetente() {
        return remetente;
    }
}
