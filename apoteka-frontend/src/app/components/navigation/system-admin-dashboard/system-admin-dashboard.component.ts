import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MedicineAddComponent } from '../../business/medicine/medicine-add/medicine-add.component';
import { PharmacyAddComponent } from '../../business/pharmacy/pharmacy-add/pharmacy-add.component';
import { DermatologistAddComponent } from '../../user/dermatologist/dermatologist-add/dermatologist-add.component';
import { PharmacyAdminAddComponent } from '../../user/pharmacy-admin/pharmacy-admin-add/pharmacy-admin-add.component';
import { SystemAdminAddComponent } from '../../user/system-admin/system-admin-add/system-admin-add.component';

@Component({
  selector: 'app-system-admin-dashboard',
  templateUrl: './system-admin-dashboard.component.html',
  styleUrls: ['./system-admin-dashboard.component.css']
})
export class SystemAdminDashboardComponent implements OnInit {

  constructor(private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
  }
  navigate(route: string) {
    this.router.navigateByUrl(route);
  }
  open(component: string) {
    if (component === 'add-pharmacy-admin') {
      this.dialog.open(PharmacyAdminAddComponent);
    } else if (component === 'add-pharmacy') {
      this.dialog.open(PharmacyAddComponent);
    } else if (component === 'add-medicine') {
      this.dialog.open(MedicineAddComponent);
    } else if (component === 'add-dermatologist') {
      this.dialog.open(DermatologistAddComponent);
    } else if (component === 'add-system-admin') {
      this.dialog.open(SystemAdminAddComponent);
    }
  }
}
