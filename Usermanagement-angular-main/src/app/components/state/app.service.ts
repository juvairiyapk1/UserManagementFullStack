import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class appService{
    constructor(private http:HttpClient){}

    loadProfile(){
        return this.http.get("http://localhost:8080/user/profile",{
            withCredentials:true
        })
    }

    loadUsers(){
        return this.http.get("http://localhost:8080/admin_only/usersList",{
            withCredentials:true
        });
    }
}