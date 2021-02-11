import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AuthService} from 'src/app/auth/auth.service';
import {Pharmacy} from 'src/app/model/business/pharmacy';
import {Pharmacist} from 'src/app/model/user/pharmacist';
import {PharmacyService} from 'src/app/service/business/pharmacy.service';
import {PharmacistService} from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacist-edit',
  templateUrl: './pharmacist-edit.component.html',
  styleUrls: ['./pharmacist-edit.component.css']
})
export class PharmacistEditComponent implements OnInit {

  output = new Pharmacist();
  pharmacies: Pharmacy[];
  pharmacy: Pharmacy;
  users: Pharmacist[];
  user: Pharmacist;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private pharmacistService: PharmacistService,
              private pharmacyService: PharmacyService, private authService: AuthService,
              private dialogRef: MatDialogRef<PharmacistEditComponent>) {
  }

  ngOnInit() {
    this.pharmacistService.findAll().subscribe(
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
    this.pharmacistService.update(this.data).subscribe(
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
