import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { User } from 'src/app/model/auth/user';
import { Medicine } from 'src/app/model/business/medicine';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Patient } from 'src/app/model/user/patient';
import { MedicineService } from 'src/app/service/business/medicine.service';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PatientService } from 'src/app/service/user/patient.service';

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css']
})
export class PatientEditComponent implements OnInit {

  output = new Patient();
  user: Patient;
  repeatPassword = '';
  medicine: Medicine[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private patientService: PatientService, private dialogRef: MatDialogRef<PatientEditComponent>, private medicineService: MedicineService) { }

  ngOnInit() {
    this.medicineService.findAll().subscribe(
      data => {
        this.medicine = data;
      }
    )
  }

  edit() {
    if (this.data.password === this.repeatPassword) {
      this.patientService.update(this.data).subscribe(
        data => {
          window.alert('Uspešna izmena!');
          this.output = data;
          this.dialogRef.close();
        }, error => {
          window.alert('Neuspešna izmena ->' + error.message);
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }

}
