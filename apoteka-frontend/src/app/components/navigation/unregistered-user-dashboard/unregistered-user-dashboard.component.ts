import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unregistered-user-dashboard',
  templateUrl: './unregistered-user-dashboard.component.html',
  styleUrls: ['./unregistered-user-dashboard.component.css']
})
export class UnregisteredUserDashboardComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigate(path: string) {
    this.router.navigateByUrl(path);
  }

}
