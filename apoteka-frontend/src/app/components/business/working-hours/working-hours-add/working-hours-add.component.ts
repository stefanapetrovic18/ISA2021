import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { WorkingHours } from 'src/app/model/business/working-hours';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { WorkingHoursService } from 'src/app/service/business/working-hours.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-working-hours-add',
  templateUrl: './working-hours-add.component.html',
  styleUrls: ['./working-hours-add.component.css']
})
export class WorkingHoursAddComponent implements OnInit {

  workingHours = new WorkingHours();
  pharmacies: Pharmacy[];
  pharmacist: Pharmacist;
  dermatologist: Dermatologist;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private workingHoursService: WorkingHoursService,
              private dialogRef: MatDialogRef<WorkingHoursAddComponent>) { }

  ngOnInit() {
    if (this.data.pharmacy !== undefined) {
      this.pharmacist = this.data;
      this.pharmacies = [];
      this.pharmacies.push(this.pharmacist.pharmacy);
      this.workingHours.employeeID = this.pharmacist.id;
    } else if (this.data.pharmacies !== undefined) {
      this.dermatologist = this.data;
      this.pharmacies = this.dermatologist.pharmacies;
      this.workingHours.employeeID = this.pharmacist.id;
    } else {
      window.alert('Greška!');
      this.close();
    }
  }

  add() {
    this.workingHoursService.create(this.workingHours).subscribe(
      data => {
        window.alert('Uspešno dodavanje!');
        this.close();
      }, error => {
        window.alert('Neuspešno dodavanje ->' + error.message);
      }
    );
  }

  close() {
    this.dialogRef.close();
  }

}
