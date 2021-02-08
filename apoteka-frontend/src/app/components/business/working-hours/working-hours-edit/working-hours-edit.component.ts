import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { WorkingHours } from 'src/app/model/business/working-hours';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { WorkingHoursService } from 'src/app/service/business/working-hours.service';
import { WorkingHoursAddComponent } from '../working-hours-add/working-hours-add.component';

@Component({
  selector: 'app-working-hours-edit',
  templateUrl: './working-hours-edit.component.html',
  styleUrls: ['./working-hours-edit.component.css']
})
export class WorkingHoursEditComponent implements OnInit {

  workingHours = new WorkingHours();
  pharmacies: Pharmacy[];
  pharmacist: Pharmacist;
  dermatologist: Dermatologist;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private workingHoursService: WorkingHoursService,
              private dialogRef: MatDialogRef<WorkingHoursAddComponent>) { }

  ngOnInit() {
    this.workingHours = this.data;
  }

  edit() {
    this.workingHoursService.update(this.workingHours).subscribe(
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
