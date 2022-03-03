import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'portal';

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
    if (window.location.pathname != "login") {
      if (!this.loginService.isLoggedIn()) {
        this.router.navigateByUrl("/login");
      }
    }
  }
}
