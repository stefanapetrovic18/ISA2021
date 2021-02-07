import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { User } from 'src/app/model/auth/user';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacist-add',
  templateUrl: './pharmacist-add.component.html',
  styleUrls: ['./pharmacist-add.component.css']
})
export class PharmacistAddComponent implements OnInit {

  data = new Pharmacist();
  output = new Pharmacist();
  pharmacies: Pharmacy[];
  users: User[];
  user: User;

  constructor(private pharmacistService: PharmacistService, private pharmacyService: PharmacyService, private authService: AuthService, private dialogRef: MatDialogRef<PharmacistAddComponent>) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }
    );
    this.authService.getUsersWithoutType().subscribe(
      data => {
        this.users = data;
        console.log(data);
      }
    );
  }

  add() {
    if (this.user !== undefined || this.user !== null) {
      this.data = this.data.convert(this.data, this.user);
    }
    this.pharmacistService.create(this.data).subscribe(
      data => {
        window.alert("Uspešno kreiranje!");
        this.output = data;
        this.dialogRef.close();
      }, error => {
        window.alert("Neuspešno kreiranje ->" + error.message);
      }
    )
  }

  close() {
    this.dialogRef.close();
  }

}
