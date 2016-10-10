package ru.anit.anitfresh.ui.taskactivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.TaskActivityBinding;
import ru.anit.anitfresh.metaobject.entities.Catalog;

public class TaskActivity extends AppCompatActivity {


    TaskActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.task_activity);



        DelayAutoCompleteTextView bookTitle = (DelayAutoCompleteTextView) findViewById(R.id.book_title);
        bookTitle.setThreshold(2);
        bookTitle.setAdapter(new BookAutoCompleteAdapter(this));
        bookTitle.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar));
        bookTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Catalog item = (Catalog) adapterView.getItemAtPosition(position);
                bookTitle.setText(item.getName());
            }
        });


        /*
        AutoCompleteTextView textView =  binding.bookTitle;
        // Получаем массив строк для автозаполнения
        String[] cities = getResources().getStringArray(R.array.cities);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        textView.setAdapter(adapter);
        */





    }
}
