package com.httplibrary.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.httplibrary.net.GetCitiesCase;
import com.httplibrary.net.RxObserver;
import com.why.modul_net.Bean.BaseData;
import com.why.modul_net.interceptor.Transformer;
import com.why.modul_net.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycyler;
    private MyAdapter mAdapter;
    private List<City> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter();
        recycyler = findViewById(R.id.recycyler);
        mAdapter.setData(list);
        recycyler.setAdapter(mAdapter);
        recycyler.setLayoutManager(linearLayoutManager);
        getMovie();
    }

    private void getMovie(){
        new GetCitiesCase().getCities()
                .compose(Transformer.<BaseData<List<City>>>switchSchedulers())
                .subscribe(new RxObserver<List<City>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(List<City> data) {
                        KLog.d("SSS","DATA :" +data);
                        list.addAll(data);
                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyHolder>{

        public List<City> list = new ArrayList<>();

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(list.get(position).name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<City> list){
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
