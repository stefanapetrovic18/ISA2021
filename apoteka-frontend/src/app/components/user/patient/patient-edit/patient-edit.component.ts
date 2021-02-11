import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Medicine} from 'src/app/model/business/medicine';
import {Patient} from 'src/app/model/user/patient';
import {MedicineService} from 'src/app/service/business/medicine.service';
import {PatientService} from 'src/app/service/user/patient.service';

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

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private patientService: PatientService, private dialogRef: MatDialogRef<PatientEditComponent>, private medicineService: MedicineService) {
  }

  ngOnInit() {
    this.medicineService.findAll().subscribe(
      data => {
        this.medicine = data;
      }
    );
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
