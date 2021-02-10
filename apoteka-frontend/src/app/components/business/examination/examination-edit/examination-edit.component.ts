import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Examination } from 'src/app/model/business/examination';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { ExaminationService } from 'src/app/service/business/examination.service';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-examination-edit',
  templateUrl: './examination-edit.component.html',
  styleUrls: ['./examination-edit.component.css']
})
export class ExaminationEditComponent implements OnInit {

  output = new Examination();
  pharmacies: Pharmacy[];
  dermatologists: Dermatologist[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private examinationService: ExaminationService, private pharmacyService: PharmacyService,
    private dermatologistService: DermatologistService, private dialogRef: MatDialogRef<ExaminationEditComponent>) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }
    );
    this.dermatologistService.findAll().subscribe(
      data => {
        this.dermatologists = data;
      }
    );
  }

  edit() {
    this.examinationService.create(this.data).subscribe(
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
