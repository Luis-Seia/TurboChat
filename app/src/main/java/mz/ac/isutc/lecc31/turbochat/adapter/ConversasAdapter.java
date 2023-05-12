package mz.ac.isutc.lecc31.turbochat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mz.ac.isutc.lecc31.turbochat.R;
import mz.ac.isutc.lecc31.turbochat.model.Conversa;

public class ConversasAdapter extends BaseAdapter {
    private ArrayList<Conversa> conversas;
    private Context context;
    private LayoutInflater inflater;

    public ConversasAdapter(ArrayList<Conversa> conversas, Context context) {
        this.conversas = conversas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return this.conversas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_conversas, null);

        ImageView imageView = convertView.findViewById(R.id.imageViewFotoContatoConversa);
        TextView nomeAmigo = convertView.findViewById(R.id.textNomeContatoConversa);
        TextView lastMensagem = convertView.findViewById(R.id.textLastMensagem);

        nomeAmigo.setText(conversas.get(position).getAmigo().getNome());
        lastMensagem.setText(conversas.get(position).getLastMensagem().getMensagem());



        return convertView;
    }
}
