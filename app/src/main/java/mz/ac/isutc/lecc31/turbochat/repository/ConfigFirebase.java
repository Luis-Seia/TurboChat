package mz.ac.isutc.lecc31.turbochat.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference refFirebase;
    private static StorageReference storage;
    public static FirebaseAuth getAuth() {

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

    public  static DatabaseReference getFirebaseDataBase(){
        if(refFirebase == null){
            refFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return refFirebase;
    }

    public static StorageReference getFirebaseStorage(){
        if( storage == null ){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }




}
