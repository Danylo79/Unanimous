import { Component, OnInit } from '@angular/core';
import { User } from '../data/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: User;

  constructor(private userService: UserService) { 
    this.user = userService.getCurrentUser();
  }

  ngOnInit(): void {
      this.user = this.userService.getCurrentUser();
  }
}
