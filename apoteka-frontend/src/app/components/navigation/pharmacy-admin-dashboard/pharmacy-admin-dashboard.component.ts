import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pharmacy-admin-dashboard',
  templateUrl: './pharmacy-admin-dashboard.component.html',
  styleUrls: ['./pharmacy-admin-dashboard.component.css']
})
export class PharmacyAdminDashboardComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  navigate(route: string) {
    this.router.navigateByUrl(route);
  }
  open(component: string) {
    if (component === 'edit') {}
  }

}
