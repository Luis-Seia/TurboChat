package mz.ac.isutc.lecc31.turbochat.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.Manifest;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;



import mz.ac.isutc.lecc31.turbochat.R;
import mz.ac.isutc.lecc31.turbochat.adapter.ViewPagerAdapter;
import mz.ac.isutc.lecc31.turbochat.databinding.ActivityLoginBinding;
import mz.ac.isutc.lecc31.turbochat.databinding.ActivityMasterBinding;

public class MasterActivity extends AppCompatActivity {
    ActivityMasterBinding binding;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FirebaseAuth autenticacao;
    Toolbar toolbar;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        binding = ActivityMasterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Turbochat");
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new ConversasFragment(), "Conversas");
        viewPagerAdapter.add(new ContactoFragment(), "Contactos");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.menuSair :
                deslogarUsuario();
                finish();
                break;
            case R.id.menuConfiguracoes:
                if (isCameraPermitida()){
                    startActivity(new Intent(this, ConfiguracaoesActivity.class));
                }else{
                    pedirPermissao();

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){

        try {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, ActivityLoginBinding.class));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private boolean isCameraPermitida() {

        int resultado = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }


    private void pedirPermissao() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                startActivity(new Intent(this, ConfiguracaoesActivity.class));
            } else {

            }
        }
    }
}