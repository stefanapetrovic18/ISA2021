import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacy-edit',
  templateUrl: './pharmacy-edit.component.html',
  styleUrls: ['./pharmacy-edit.component.css']
})
export class PharmacyEditComponent implements OnInit {

  output = new Pharmacy();
  pharmacists: Pharmacist[];
  dermatologists: Dermatologist[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private pharmacyService: PharmacyService, private pharmacistService: PharmacistService,
    private dermatologistService: DermatologistService, private dialogRef: MatDialogRef<PharmacyEditComponent>) { }

  ngOnInit() {
    this.pharmacistService.findAllUnemployed().subscribe(
      data => {
        this.pharmacists = data;
      }
    );
    this.dermatologistService.findAllNotEmployedInPharmacy(this.data.id).subscribe(
      data => {
        this.dermatologists = data;
      }
    )
  }

  edit() {
    this.pharmacyService.create(this.data).subscribe(
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
