import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacy-add',
  templateUrl: './pharmacy-add.component.html',
  styleUrls: ['./pharmacy-add.component.css']
})
export class PharmacyAddComponent implements OnInit {

  data = new Pharmacy();
  output = new Pharmacy();
  pharmacists: Pharmacist[];
  dermatologists: Dermatologist[];

  constructor(private pharmacyService: PharmacyService, private pharmacistService: PharmacistService,
    private dermatologistService: DermatologistService, private dialogRef: MatDialogRef<PharmacyAddComponent>) { }

  ngOnInit() {
    this.pharmacistService.findAllUnemployed().subscribe(
      data => {
        this.pharmacists = data;
      }
    );
    this.dermatologistService.findAll().subscribe(
      data => {
        this.dermatologists = data;
      }
    )
  }

  add() {
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
