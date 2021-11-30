package VP.videoplayer.ali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ModelAdapter.onClick {
    private ArrayList<ModelClass> al;
    private ModelAdapter modelAdapter;
    private RecyclerView recyclerView;
    private static final int READ_STORAGE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerVdo);
        al=new ArrayList<>();
        modelAdapter=new ModelAdapter(al,this,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(modelAdapter);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
        }
        else {
            getVideos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0&&requestCode==101)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                getVideos();
            }
            else
            {
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void getVideos() {
        ContentResolver contentResolver=getContentResolver();
        Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null,null,null);
        if(cursor!=null&&cursor.moveToFirst())
        {
            do {
                @SuppressLint("Range") String tittle=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String path=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                @SuppressLint("Range") Bitmap thumb= ThumbnailUtils.createVideoThumbnail(path,MediaStore.Images.Thumbnails.MICRO_KIND);
                al.add(new ModelClass(tittle, path,thumb));
                Log.d("TAG",tittle+" "+path+" "+thumb);
            }
            while (cursor.moveToNext());
        }
        modelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVdoClk(int poss) {
        Intent intent=new Intent(this,VideoPlayer.class);
        intent.putExtra("tittle",al.get(poss).getName());
        intent.putExtra("path",al.get(poss).getVideoPath());
        startActivity(intent);
    }
}