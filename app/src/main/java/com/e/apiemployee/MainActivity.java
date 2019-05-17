package com.e.apiemployee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import EmployeeAPI.EmployeeAPI;
import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvEmployeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEmployeeData = findViewById(R.id.tvEmployeeData);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:7600/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listcall = employeeAPI.getAllEmployee();

        listcall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                List<Employee> employeeList = response.body();
                for (Employee employee : employeeList) {
                    String content = "";
                    content += "id:" + employee.getId() + "\n";
                    content += "FullName:" + employee.getEmployee_name() + "\n";
                    content += "Age:" + employee.getEmployee_age() + "\n";
                    content += "Salary:" + employee.getEmployee_salary() + "\n";
                    content += "Emp_image:" + employee.getProfile_image() + "\n";
                    content += "--------------------------" + "\n";
                    tvEmployeeData.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                tvEmployeeData.setText("Error" + t.getLocalizedMessage());
            }
        });

    }
}
