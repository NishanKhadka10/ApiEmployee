package com.e.apiemployee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import EmployeeAPI.EmployeeAPI;
import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDeleteActivity extends AppCompatActivity {
    private final static String url = "http://dummy.restapiexample.com/api/v1/";
    private EditText etEmpID, etEmpName, etEmpAge, etEmpSalary;
    private Button btnSearch, btnUpdate, btnDelete;
    Retrofit retrofit;
    EmployeeAPI employeeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        etEmpName = findViewById(R.id.etName);
        etEmpID = findViewById(R.id.etEmpID);
        etEmpSalary = findViewById(R.id.etEmpSalary);
        etEmpAge = findViewById(R.id.etEmpAge);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateEmployee();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });
    }

    private void CreateInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        employeeAPI = retrofit.create(EmployeeAPI.class);
    }

    private void loadData() {
        CreateInstance();
        Call<Employee> listCall = employeeAPI.getEmployeeID(Integer.parseInt(etEmpID.getText().toString()));

        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                etEmpName.setText(response.body().getEmployee_name());
                etEmpAge.setText(Integer.toString(response.body().getEmployee_age()));
                etEmpSalary.setText(Float.toString(response.body().getEmployee_salary()));
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void deleteEmployee() {
        CreateInstance();

        Call<Void> voidCall = employeeAPI.deleteEmployee(Integer.parseInt(etEmpID.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this, "Sucessfully Deleted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void updateEmployee() {
        CreateInstance();

        EmployeeCUD employeeCUD = new EmployeeCUD(Float.parseFloat(etEmpSalary.getText().toString()), etEmpName.getText().toString(),
                Integer.parseInt(etEmpAge.getText().toString()));


        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmpID.getText().toString()), employeeCUD);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this, "Sucessfully Updated", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
