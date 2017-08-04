package booksearching.test.com.booksearch;

import android.support.annotation.NonNull;

/**
 * Created by KSH on 2017-08-02.
 */

public class Book implements Comparable<Book> {
    private String title = "";
    private String cover = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book){
            Book comparedBook = (Book)obj;
            return title.equals(comparedBook.getTitle()) && cover.equals(comparedBook.getCover());
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return title + " - " + cover;
    }

    @Override
    public int compareTo(@NonNull Book o) {
        if(o instanceof Book){
            Book comparedBook = (Book)o;
            return title.compareTo(comparedBook.getTitle());
        }else{
            return 0;
        }
    }
}
