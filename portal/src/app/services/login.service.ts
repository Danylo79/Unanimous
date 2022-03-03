import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Identity } from '../data/identity';

@Injectable()
export class LoginService {
    constructor(public http: HttpClient) { }

  public login(user: Identity){
    const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/x-www-form-urlencoded'
    })
    };
    let body = new URLSearchParams();
    body.set('username', user.username);
    body.set('password', user.password);

    return this.http.post("localhost:8080/login", body.toString(), httpOptions);
  }

  public isLoggedIn(): boolean {
    return false;
  }
}
