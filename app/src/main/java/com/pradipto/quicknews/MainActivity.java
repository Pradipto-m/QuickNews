package com.pradipto.quicknews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.ClickInterface{

    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModal> categoryModals;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvNews = findViewById(R.id.rvNews);
        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        articlesArrayList = new ArrayList<>();
        categoryModals = new ArrayList<>();

        newsAdapter = new NewsAdapter(articlesArrayList,this);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.setAdapter(newsAdapter);

        categoryAdapter = new CategoryAdapter(categoryModals,this, this);
        rvCategories.setAdapter(categoryAdapter);

        getCategories();
        getNews("Top");
        newsAdapter.notifyDataSetChanged();
    }

    private void getNews(@NonNull String category) {
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=1f4a12d2698e432ea9cf18126dcc7acd";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=1f4a12d2698e432ea9cf18126dcc7acd";
        String Base_Url = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_Url).addConverterFactory(GsonConverterFactory.create()).build();

        RetroFit retrofitApi = retrofit.create(RetroFit.class);
        Call<NewsModal> call;
        if(category.equals("Top")) {  call = retrofitApi.getAllNews(url);  }
        else {  call = retrofitApi.getNewsByCategory(categoryURL);  }

        call.enqueue(new Callback<NewsModal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<NewsModal> call, @NonNull Response<NewsModal> response) {
                NewsModal news = response.body();
                assert news != null;
                ArrayList<Articles> articles = news.getArticles();

                for(int i=0; i < articles.size(); i++) {
                    articlesArrayList.add( new Articles( articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getUrl() ) );
                }

                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<NewsModal> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategories() {
        categoryModals.add(new CategoryModal("Top"));
        categoryModals.add(new CategoryModal("Technology"));
        categoryModals.add(new CategoryModal("Sports"));
        categoryModals.add(new CategoryModal("Health"));
        categoryModals.add(new CategoryModal("Business"));
        categoryModals.add(new CategoryModal("Entertainment"));
        categoryModals.add(new CategoryModal("General"));
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnCategoryClick(int position) {
        String category = categoryModals.get(position).getCategory();
        getNews(category);
    }
}