import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Identity} from '../data/identity';
import {LoginService} from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public username: any;
  public password: any;
  public error: string | undefined;
  public returnUrl: string;

  constructor(private loginService: LoginService, private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router) {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  ngOnInit(): void {

  }

  login(): void {
    if (this.username != null && this.password != null) {
      this.loginService.login(new Identity(this.username, this.password));
    } else {
      this.error = "Invalid Username or Password";
    }
  }
}
