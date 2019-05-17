package EmployeeAPI;

import java.util.List;

import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeAPI {

    @GET("getEmployee")
    Call<List<Employee>> getAllEmployee();

    @POST("employee")
    Call<Void> registerEmployee(@Body EmployeeCUD emp);



    @GET("employee/{empID}")
    Call<Employee> getEmployeeID(@Path("empID") int empId);

    @PUT("update/{empID}")
    Call<Void> updateEmployee(@Path("empID") int empId, @Body EmployeeCUD emp);

    @DELETE("delete/{empID}")
    Call<Void> deleteEmployee(@Path("empID") int empId);

}
