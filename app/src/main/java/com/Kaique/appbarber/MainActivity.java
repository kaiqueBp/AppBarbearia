package com.Kaique.appbarber;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView nome;
    private TextView telefone;
    private RatingBar avaliacao;
    private Button botao;
    private ListView listagem;
    List dados;

    ArrayAdapter listaAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        nome= findViewById(R.id.idNome);
        telefone= findViewById(R.id.idTele);
        avaliacao= findViewById(R.id.idVali);
        botao= findViewById(R.id.idBotao);
        listagem= findViewById(R.id.lista);
        dados= new LinkedList();

        listaAdapter= new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dados);
        listagem.setAdapter(listaAdapter);
        ler();

    }

    public void salvar(View view){
        Pessoa p = new Pessoa();
        p.setNome(nome.getText().toString());
        p.setTelefone(telefone.getText().toString());
        p.setAvaliacao(String.valueOf(avaliacao.getRating()));
        DatabaseReference pessoas = databaseReference.child("pessoas");
        pessoas.push().setValue(p);
        Toast.makeText(getApplicationContext(),"Salvo",Toast.LENGTH_SHORT).show();
    }
    public void ler(){
        DatabaseReference pessoas = databaseReference.child("pessoas");

        pessoas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dados.clear();
                Log.i("Firebase",snapshot.getValue().toString());
                for (DataSnapshot item:snapshot.getChildren()
                ) {
                    Pessoa pessoa = item.getValue(Pessoa.class);
                    dados.add(pessoa);
                    listaAdapter.notifyDataSetChanged();
                    Log.i("Firebase",pessoa.getNome());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Firebase",error.toString());
                Toast.makeText(getApplicationContext(),"erro de conexão",Toast.LENGTH_SHORT).show();

            }
        });
    }

}