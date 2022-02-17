import { Component, OnInit } from '@angular/core';
import { User } from '../data/user';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public username: any;
  public password: any;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  login(): void {
    this.loginService.login(new User(this.username, this.password));
  }
}
