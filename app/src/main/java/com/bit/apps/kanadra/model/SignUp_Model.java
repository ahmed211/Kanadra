package com.bit.apps.kanadra.model;

import java.util.List;

public class SignUp_Model {

    String code;
    String message;

    public List<SignUp_Model.data> getData() {
        return data;
    }

    List<data> data;


    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {

        return code;
    }

    public String getMessage() {
        return message;
    }



    public class data{
        String id, auth_key;

        public String getId() {
            return id;
        }

        public String getAuth_key() {
            return auth_key;
        }
    }

}
