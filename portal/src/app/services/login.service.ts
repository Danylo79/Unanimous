import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../data/user';

@Injectable()
export class LoginService {
    constructor(public http: HttpClient) { }

  public login(user: User){
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
