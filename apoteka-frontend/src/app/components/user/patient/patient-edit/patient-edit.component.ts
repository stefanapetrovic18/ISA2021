import {Component, Inject, OnInit} from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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
  allergies: Medicine[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private patientService: PatientService,
              private dialogRef: MatDialogRef<PatientEditComponent>, private medicineService: MedicineService,
              fb: FormBuilder) {

  }

  // allergies: FormGroup;


  ngOnInit() {
    this.medicine = [];
    this.allergies = [];
    this.medicineService.findAll().subscribe(
      data => {
        // this.medicine = data;
        data.forEach(m => {
          let flag = true;
          this.data.allergies.forEach(d => {
            if (d.id === m.id) {
              flag = false;
            }
          });
          if (flag) {
            this.medicine.push(m);
            console.log(m);
          }
        });
        console.log(this.medicine);
        console.log(this.data.allergies);
        this.medicine.forEach(m => this.allergies.push(m));
        this.data.allergies.forEach(m => this.allergies.push(m));
        console.log(this.allergies);
      }
    );

  }

  edit() {
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

  close() {
    this.dialogRef.close();
  }

}
