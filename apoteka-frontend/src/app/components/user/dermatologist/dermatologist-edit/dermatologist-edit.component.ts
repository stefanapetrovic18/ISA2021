import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { User } from 'src/app/model/auth/user';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';

@Component({
  selector: 'app-dermatologist-edit',
  templateUrl: './dermatologist-edit.component.html',
  styleUrls: ['./dermatologist-edit.component.css']
})
export class DermatologistEditComponent implements OnInit {

  output = new Dermatologist();
  pharmacies: Pharmacy[];
  pharmacies2: Pharmacy[];
  users: Dermatologist[];
  user: Dermatologist;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dermatologistService: DermatologistService,
              private pharmacyService: PharmacyService, private authService: AuthService,
              private dialogRef: MatDialogRef<DermatologistEditComponent>) { }

  ngOnInit() {
    this.dermatologistService.findAll().subscribe(
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
      }
    );
  }

  edit() {
    this.user.pharmacies = this.pharmacies2;
    this.dermatologistService.update(this.data).subscribe(
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
