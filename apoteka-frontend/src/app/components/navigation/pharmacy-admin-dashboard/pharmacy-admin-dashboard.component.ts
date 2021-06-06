import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { PharmacyAdmin } from 'src/app/model/user/pharmacy-admin';
import { PharmacyAdminService } from 'src/app/service/user/pharmacy-admin.service';

@Component({
  selector: 'app-pharmacy-admin-dashboard',
  templateUrl: './pharmacy-admin-dashboard.component.html',
  styleUrls: ['./pharmacy-admin-dashboard.component.css']
})
export class PharmacyAdminDashboardComponent implements OnInit {

  admin: PharmacyAdmin;
  constructor(private router: Router, private pharmacyAdminService: PharmacyAdminService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.pharmacyAdminService.findByUsername(this.tokenStorageService.getUsername()).subscribe(
      data => {
        this.admin = data;
      }, error => {
        window.alert('Administrator ne postoji.');
      }
    )
  }
  navigate(route: string) {
    this.router.navigateByUrl(route);
  }
  open(component: string) {
    if (component === 'edit') {}
  }

}
