import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';

@Component({
  selector: 'app-pharmacy-add',
  templateUrl: './pharmacy-add.component.html',
  styleUrls: ['./pharmacy-add.component.css']
})
export class PharmacyAddComponent implements OnInit {

  data = new Pharmacy();
  output = new Pharmacy();

  constructor(private pharmacyService: PharmacyService, private dialogRef: MatDialogRef<PharmacyAddComponent>) { }

  ngOnInit() {
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
