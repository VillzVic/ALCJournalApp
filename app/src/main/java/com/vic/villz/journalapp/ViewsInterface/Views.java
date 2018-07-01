package com.vic.villz.journalapp.ViewsInterface;

public class Views {
    //this class contains all the interfaces for the views

    public interface LoginView{

         void onLoginSuccess();
         void onLoginFailure();

    }

    public interface RegisterView{

        void onRegisterSuccess();
        void onRegisterFailure();
    }

    public interface  UploadView{

        void onUploadSuccess();
        void onUploadFailure();

    }

    public interface EditView{

        void onEditSuccess();
        void onEditFailure();
    }
}
