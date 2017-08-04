package booksearching.test.com.booksearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.android.volley.toolbox.ImageLoader;
//import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by KSH on 2017-08-02.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Book> mBooks;

    private static TextView mBookTitle;
    private static ImageView mBookCover;
   // private static NetworkImageView mBookCover;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);

            mBookTitle = (TextView)view.findViewById(R.id.book_title);
            mBookCover = (ImageView)view.findViewById(R.id.book_cover);
         //   mBookCover = (NetworkImageView)view.findViewById(R.id.book_cover);
        }
    }

    /*   private void loadImage(String imgUrl){
           ImageLoader imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
           imageLoader.get(imgUrl, ImageLoader.getImageListener(mBookCover, R.drawable.no_bookcover, android.R.drawable.ic_dialog_alert));
           mBookCover.setImageUrl(imgUrl, imageLoader);
       }
   */
    public ListAdapter(Context context, ArrayList<Book> books){
        mContext = context;
        mBooks = books;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Book book = mBooks.get(position);
        mBookTitle.setText(book.getTitle());
        if(book.getCover().equals("")){
            mBookCover.setImageResource(R.drawable.no_bookcover);
        }else{
            Glide.with(mContext).load(book.getCover()).into(mBookCover);
        }

    //    loadImage(book.getCover());
    }

    @Override
    public int getItemCount(){
        return mBooks.size();
    }
}
