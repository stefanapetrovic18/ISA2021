import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {AuthService} from 'src/app/auth/auth.service';
import {Pharmacy} from 'src/app/model/business/pharmacy';
import {Dermatologist} from 'src/app/model/user/dermatologist';
import {PharmacyService} from 'src/app/service/business/pharmacy.service';
import {DermatologistService} from 'src/app/service/user/dermatologist.service';
import {WorkingHoursAddComponent} from '../../../business/working-hours/working-hours-add/working-hours-add.component';

@Component({
  selector: 'app-dermatologist-add',
  templateUrl: './dermatologist-add.component.html',
  styleUrls: ['./dermatologist-add.component.css']
})
export class DermatologistAddComponent implements OnInit {

  data = new Dermatologist();
  output = new Dermatologist();
  pharmacies: Pharmacy[];
  users: Dermatologist[];
  user: Dermatologist;
  repeatPassword = '';

  constructor(private dermatologistService: DermatologistService, private pharmacyService: PharmacyService,
              private authService: AuthService, private dialogRef: MatDialogRef<DermatologistAddComponent>,
              private matDialog: MatDialog) {
  }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }
    );
  }

  add() {
    if (this.data.password === this.repeatPassword) {
      this.dermatologistService.create(this.data).subscribe(
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
  addWH() {
    if (this.data.workingHours === undefined || this.data.workingHours === null) {
      this.data.workingHours = [];
    }
    this.matDialog.open(WorkingHoursAddComponent, {
      data: this.data
    }).afterClosed().subscribe(
      result => {
        this.data.workingHours.push(result.result);
      }
    );
  }

  close() {
    this.dialogRef.close();
  }

}
