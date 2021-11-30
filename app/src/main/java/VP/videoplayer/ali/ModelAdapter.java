package VP.videoplayer.ali;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelHolder> {
    private ArrayList<ModelClass> al=new ArrayList<>();
    private Context context;
    private onClick click;

    public ModelAdapter(ArrayList<ModelClass> al, Context context, onClick click) {
        this.al = al;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ModelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelClass modelClass=al.get(position);
        holder.thumb.setImageBitmap(modelClass.getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x=position;
                click.onVdoClk(x);
            }
        });
    }


    @Override
    public int getItemCount() {
        return al.size();
    }

    public class ModelHolder extends RecyclerView.ViewHolder{
        private ImageView thumb;
        public ModelHolder(@NonNull View itemView) {
            super(itemView);
            thumb=itemView.findViewById(R.id.imgThumb);

        }
    }
    public interface onClick{
        void onVdoClk(int poss);
    }
}
