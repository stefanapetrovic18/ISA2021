import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { PharmacyAdmin } from 'src/app/model/user/pharmacy-admin';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PharmacyAdminService } from 'src/app/service/user/pharmacy-admin.service';

@Component({
  selector: 'app-pharmacy-admin-add',
  templateUrl: './pharmacy-admin-add.component.html',
  styleUrls: ['./pharmacy-admin-add.component.css']
})
export class PharmacyAdminAddComponent implements OnInit {

  data = new PharmacyAdmin();
  output = new PharmacyAdmin();
  pharmacies: Pharmacy[];
  users: PharmacyAdmin[];
  user: PharmacyAdmin;
  repeatPassword = '';

  constructor(private pharmacyadminService: PharmacyAdminService, private pharmacyService: PharmacyService,
              private authService: AuthService, private dialogRef: MatDialogRef<PharmacyAdminAddComponent>) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }
    );
  }

  add() {
    if (this.data.password === this.repeatPassword) {
      this.pharmacyadminService.create(this.data).subscribe(
        data => {
          window.alert('Uspešno kreiranje!');
          this.output = data;
          this.dialogRef.close();
        }, error => {
          window.alert('Neuspešno kreiranje ->' + error.message);
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }

}
