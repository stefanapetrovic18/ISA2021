import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { PharmacyAdmin } from 'src/app/model/user/pharmacy-admin';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PharmacyAdminService } from 'src/app/service/user/pharmacy-admin.service';

@Component({
  selector: 'app-pharmacy-admin-edit',
  templateUrl: './pharmacy-admin-edit.component.html',
  styleUrls: ['./pharmacy-admin-edit.component.css']
})
export class PharmacyAdminEditComponent implements OnInit {

  output = new PharmacyAdmin();
  pharmacies: Pharmacy[];
  pharmacy: Pharmacy;
  users: PharmacyAdmin[];
  user: PharmacyAdmin;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private pharmacyadminService: PharmacyAdminService,
              private pharmacyService: PharmacyService, private authService: AuthService,
              private dialogRef: MatDialogRef<PharmacyAdminEditComponent>) { }

  ngOnInit() {
    this.pharmacyadminService.findAll().subscribe(
      data => {
        this.users = data;
        let u = this.data;
        this.users.forEach((usr) => {
          if (u.id === usr.id) {
            u = usr;
          }
        });
        this.user = u;
      }
    );
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
        if (this.data.pharmacy !== undefined && this.data.pharmacy !== null) {
          let p = this.data.pharmacy;
          this.pharmacies.forEach((ph) => {
            if (p.id === ph.id) {
              console.log('MATCH!');
              p = ph;
            }
          });
          this.pharmacy = p;
        }
      }
    );
  }

  edit() {
    this.user.pharmacy = this.pharmacy;
    this.pharmacyadminService.update(this.data).subscribe(
      data => {
        window.alert('Uspešna izmena!');
        this.output = data;
        this.dialogRef.close();
      }, error => {
        window.alert('Neuspešna izmena ->' + error.message);
      }
    );
  }

  close() {
    this.dialogRef.close();
  }

}
