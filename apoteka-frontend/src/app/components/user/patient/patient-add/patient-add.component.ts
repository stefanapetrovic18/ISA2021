import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { User } from 'src/app/model/auth/user';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Patient } from 'src/app/model/user/patient';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PatientService } from 'src/app/service/user/patient.service';

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css']
})
export class PatientAddComponent implements OnInit {

  data = new Patient();
  output = new Patient();
  user: Patient;
  repeatPassword = '';

  constructor(private patientService: PatientService, private dialogRef: MatDialogRef<PatientAddComponent>) { }

  ngOnInit() {
  }

  add() {
    if (this.data.password === this.repeatPassword) {
      this.patientService.create(this.data).subscribe(
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
