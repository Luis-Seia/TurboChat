package mz.ac.isutc.lecc31.turbochat.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import mz.ac.isutc.lecc31.turbochat.R;
import mz.ac.isutc.lecc31.turbochat.adapter.ConversasAdapter;
import mz.ac.isutc.lecc31.turbochat.helper.RecyclerItemClickListener;
import mz.ac.isutc.lecc31.turbochat.helper.UsuarioFirebase;
import mz.ac.isutc.lecc31.turbochat.model.Conversa;
import mz.ac.isutc.lecc31.turbochat.repository.ConfigFirebase;


public class ConversasFragment extends Fragment {
    private RecyclerView recyclerViewConversas;
    private List<Conversa> listaConversas = new ArrayList<>();
    private ConversasAdapter adapter;
    private DatabaseReference database;
    private DatabaseReference conversasRef;
    private ChildEventListener childEventListenerConversas;

    public ConversasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewConversas = view.findViewById(R.id.recyclerListaConversas);

        //Configurar adapter
        adapter = new ConversasAdapter(listaConversas, getActivity());

        //Configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager( layoutManager );
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter( adapter );

        //Configurar evento de clique
        recyclerViewConversas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewConversas,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {

                                Conversa conversaSelecionada = listaConversas.get( position );

                                Intent i = new Intent(getActivity(), ChatActivity.class);
                                i.putExtra("chatContato", conversaSelecionada.getUsuarioExibicao() );
                                startActivity( i );

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        //Configura conversas ref
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        database = ConfigFirebase.getFirebaseDataBase();
        conversasRef = database.child("conversas")
                .child( identificadorUsuario );

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasRef.removeEventListener( childEventListenerConversas );
    }

    public void recuperarConversas(){
        listaConversas.clear();
        childEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // Recuperar conversas
                Conversa conversa = dataSnapshot.getValue( Conversa.class );
                listaConversas.add( conversa );
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}