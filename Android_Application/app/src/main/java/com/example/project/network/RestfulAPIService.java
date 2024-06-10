package com.example.project.network;

import com.example.project.entities.Book;
import com.example.project.entities.Category;
import com.example.project.entities.DetailImportSlip;
import com.example.project.entities.DetailLoanSlip;
import com.example.project.entities.DetailOrderSlip;
import com.example.project.entities.DetailReturnSlip;
import com.example.project.entities.ImportSlip;
import com.example.project.entities.LoanSlip;
import com.example.project.entities.NewBook;
import com.example.project.entities.OrderSlip;
import com.example.project.entities.Publisher;
import com.example.project.entities.ReturnSlip;
import com.example.project.entities.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestfulAPIService {

    @GET("book/getAll")
    Call<List<NewBook>> getAllBooks();

    @POST("book/add")
    Call<List<NewBook>> addBook(@Body NewBook book);

    @GET("book/getBorrowable")
    Call<List<NewBook>> getBorrowableBooks();

    @GET("book/findById/{id}")
    Call<NewBook> findBookById(@Path("id") String id);

    @PUT("book/update")
    Call<NewBook> updateBook(@Body NewBook bookDto);

    @POST("category/add")
    Call<List<Category>> addCategory(@Body Category category);

    @PUT("category/update")
    Call<List<Category>> updateCategory(@Body Category category);

    @GET("category/getAll")
    Call<List<Category>> getAllCategories();

    @POST("publisher/add")
    Call<List<Publisher>> addPublisher(@Body Publisher publisher);

    @PUT("publisher/update")
    Call<List<Publisher>> updatePublisher(@Body Publisher publisher);

    @GET("publisher/getAll")
    Call<List<Publisher>> getAllPublishers();

    @GET("publisher/name/{name}")
    Call<List<Publisher>> findPublisherByName(@Path("name") String name);

    @GET("loanSlip/getAll")
    Call<List<LoanSlip>> GetAllLoanSlip();

    @POST("loanSlip/add")
    Call<LoanSlip> AddLoanSlip(@Body LoanSlip loanSlip);

    @GET("detailLoanSlip/getAll")
    Call<List<DetailLoanSlip>> GetAllDetailLoanSlip();

    @GET("detailLoanSlip/getByLoan/{id}")
    Call<List<DetailLoanSlip>> GetDetailLoanSlipByLoanId(@Path("id") Integer id);

    @POST("detailLoanSlip/add")
    Call<List<DetailLoanSlip>> AddDetailLoanSlip(@Body DetailLoanSlip detailLoanSlip);

    @GET("detailLoanSlip/getBooks")
    Call<List<NewBook>> GetBooks();

    @PUT("detailLoanSlip/updateDetailStatus/{id}/{bookName}")
    Call<DetailLoanSlip> UpdateDetailLoanSlipStatus(@Path("id") int id, @Path("bookName") String bookName);

    @GET("returnSlip/getAll")
    Call<List<ReturnSlip>> GetAllReturnSlip();

    @POST("returnSlip/add")
    Call<ReturnSlip> AddReturnSlip(@Body ReturnSlip ReturnSlip);

    @GET("detailReturnSlip/getAll")
    Call<List<DetailReturnSlip>> GetAllDetailReturnSlip();

    @GET("detailReturnSlip/getByReturn/{id}")
    Call<List<DetailReturnSlip>> GetDetailReturnSlipByLoanId(@Path("id") int id);

    @POST("detailReturnSlip/add")
    Call<List<DetailReturnSlip>> AddDetailReturnSlip(@Body DetailReturnSlip detailReturnSlip);

    @GET("supplier/getAll")
    Call<List<Supplier>> getAllSuppliers();

    @POST("supplier/add")
    Call<List<Supplier>> addSupplier(@Body Supplier supplier);

    @PUT("supplier/update")
    Call<Supplier> updateSupplier(@Body Supplier supplier);

    @GET("supplier/getByActiveStatus")
    Call<List<Supplier>> getSuppliersByActiveStatus();

    @GET("supplier/getSuppliersForSlip")
    Call<List<Supplier>> getAllSuppliersForSLip();

    @GET("importSlip/getAll")
    Call<List<ImportSlip>> GetAllImportSlip();

    @POST("importSlip/getById/{id}")
    Call<ImportSlip> GetImportSlipById(@Path("id") Integer id);

    @POST("importSlip/add")
    Call<ImportSlip> AddImportSlip(@Body ImportSlip importSlip);

    @GET("detailImportSlip/getByImportId/{id}")
    Call<List<DetailImportSlip>> GetDetailImportSlip(@Path("id") Integer id);

    @POST("detailImportSlip/add")
    Call<String> AddDetailImportSlip(@Body DetailImportSlip DetailImportSlip);

    @GET("orderSlip/getAll")
    Call<List<OrderSlip>> GetAllOrderSlip();

    @POST("orderSlip/getById/{id}")
    Call<OrderSlip> GetOrderSlipById(@Path("id") Integer id);

    @POST("orderSlip/add")
    Call<OrderSlip> AddOrderSlip(@Body OrderSlip orderSlip);

    @GET("detailOrderSlip/getAll")
    Call<List<DetailOrderSlip>> GetAllDetailOrderSlip();

    @POST("detailOrderSlip/add")
    Call<DetailOrderSlip> AddDetailOrderSlip(@Body DetailOrderSlip detailOrderSlip);

    @GET("detailOrderSlip/findByOrderId/{id}")
    Call<List<DetailOrderSlip>> GetByOrderId(@Path("id") Integer id);

    @PUT("detailOrderSlip/updateStatus/{id}/{bookName}")
    Call<String> UpdateStatus(@Path("id") Integer id, @Path("bookName") String bookName);
}
