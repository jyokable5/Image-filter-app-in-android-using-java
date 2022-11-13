package com.example.instaone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.mukesh.image_processing.ImageProcessor;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import android.app.ProgressDialog;
import java.io.IOException;
import java.util.Timer;
import java.util.UUID;

public class MainActivity<ActivityMainBinding> extends AppCompatActivity {

    // creating a bitmap for our original
    // image and all image filters.
    Bitmap bitmap;

    //Buttons
    Button btnselect, btnsave;

    // constant to compare the activity result code --GFG
    int SELECT_PICTURE = 200;



    // creating a variable for image view.
    ImageView oneIV, twoIV, threeIV, fourIV, fiveIV, sixIV, sevenIV, eightIV, nineIV, tenIV, originalIV;
    Bitmap oneBitMap, twoBitMap, threeBitmap, fourBitMap, fiveBitMap, sixBitMap, sevenBitMap, eightBitMap, nineBitMap, tenBitMap;



   //PICK AN IMAGE
     private void uploadImages(byte[] imageByte) {
         StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                 .child("images")
                 .child("images" + System.currentTimeMillis() + ".jpg");
         storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         uploadImagesUri(String.valueOf(uri));
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {


                         progressDialog.cancel();
                         Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                     }
                 });
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

                 progressDialog.cancel();
                 Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }
  //UPLOAD AN IMAGE
   private void uploadImagesUri(String uri){
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("images_uri");
       String key = reference.push().getKey();
       reference.child(key).setValue(uri).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               int count;
               count += 1;
               ByteArrayOutputStream imagesUri;  // NEWWWW
               if(count == imagesUri.size()){
                   progressDialog.dismiss();
                   Toast.makeText(MainActivity.this,"Image uploaded",Toast.LENGTH_SHORT).show();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               progressDialog.cancel();
               Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
           }
       });
   }













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating a variable for our image processor.
        ImageProcessor processor = new ImageProcessor();

        // initializing bitmap with our image resource.
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mount);

        // initializing image views for our filters
        // and original image on which we will
        // be applying our filters.
        oneIV = findViewById(R.id.idIVOne);
        twoIV = findViewById(R.id.idIVTwo);
        threeIV = findViewById(R.id.idIVThree);
        fourIV = findViewById(R.id.idIVFour);
        fiveIV = findViewById(R.id.idIVFive);
        sixIV = findViewById(R.id.idIVSix);
        sevenIV = findViewById(R.id.idIVSeven);
        eightIV = findViewById(R.id.idIVEight);
        nineIV = findViewById(R.id.idIVNine);
        tenIV = findViewById(R.id.idIVTen);
        originalIV = findViewById(R.id.idIVOriginalImage);

        // below line is use to add tint effect to our original
        // image bitmap and storing that in one bitmap.
        oneBitMap = processor.tintImage(bitmap, 90);

        // after storing it to one bitmap
        // we are setting it to imageview.
        oneIV.setImageBitmap(oneBitMap);

        // below line is use to apply gaussian blur effect
        // to our original image bitmap.
        twoBitMap = processor.applyGaussianBlur(bitmap);
        twoIV.setImageBitmap(twoBitMap);

        // below line is use to add sepia toing effect
        // to our original image bitmap.
        threeBitmap = processor.createSepiaToningEffect(bitmap, 1, 2, 1, 5);
        threeIV.setImageBitmap(threeBitmap);

        // below line is use to apply saturation
        // filter to our original image bitmap.
        fourBitMap = processor.applySaturationFilter(bitmap, 3);
        fourIV.setImageBitmap(fourBitMap);

        // below line is use to apply snow effect
        // to our original image bitmap.
        fiveBitMap = processor.applySnowEffect(bitmap);
        fiveIV.setImageBitmap(fiveBitMap);

        // below line is use to add gray scale
        // to our image view.
        sixBitMap = processor.doGreyScale(bitmap);
        sixIV.setImageBitmap(sixBitMap);

        // below line is use to add engrave effect
        // to our image view.
        sevenBitMap = processor.engrave(bitmap);
        sevenIV.setImageBitmap(sevenBitMap);

        // below line is use to create a contrast
        // effect to our image view.
        eightBitMap = processor.createContrast(bitmap, 1.5);
        eightIV.setImageBitmap(eightBitMap);

        // below line is use to add shadow effect
        // to our original bitmap.
        nineBitMap = processor.createShadow(bitmap);
        nineIV.setImageBitmap(nineBitMap);

        // below line is use to add flea
        // effect to our image view.
        tenBitMap = processor.applyFleaEffect(bitmap);
        tenIV.setImageBitmap(tenBitMap);

        // below line is use to call on click
        // listener for our all image filters.
        initializeOnCLickListerns();
    }

    private void initializeOnCLickListerns() {
        oneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on clicking on each filter we are
                // setting that filter to our original image.
                originalIV.setImageBitmap(oneBitMap);
            }
        });

        twoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(twoBitMap);
            }
        });

        threeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(threeBitmap);
            }
        });

        fourIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(fourBitMap);
            }
        });

        fiveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(fiveBitMap);
            }
        });

        sixIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(sixBitMap);
            }
        });

        sevenIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(sevenBitMap);
            }
        });

        eightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(eightBitMap);
            }
        });

        nineIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(nineBitMap);
            }
        });

        tenIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalIV.setImageBitmap(tenBitMap);
            }
        });
    }
}






