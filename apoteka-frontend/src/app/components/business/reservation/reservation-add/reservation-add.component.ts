import { Component, Inject, OnInit } from '@angular/core';
import {Pharmacy} from '../../../../model/business/pharmacy';
import {PharmacyService} from '../../../../service/business/pharmacy.service';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-reservation-add',
  templateUrl: './reservation-add.component.html',
  styleUrls: ['./reservation-add.component.css']
})
export class ReservationAddComponent implements OnInit {

  pharmacies: Pharmacy[];
  output: Pharmacy;
  date: Date;
  showPharmacyField = true;

  constructor(private pharmacyService: PharmacyService, private dialogRef: MatDialogRef<ReservationAddComponent>,
    @Inject(MAT_DIALOG_DATA) private data: {pharmacy: Pharmacy}) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
        console.log('Pharmacies: ' + this.pharmacies)
        if (this.data.pharmacy !== undefined) {
          this.output = this.data.pharmacy;
          this.showPharmacyField = false;
        }
      }
    );
  }
  add() {
    this.dialogRef.close({pharmacy: this.output, date: this.date});
  }
  close() {
    this.dialogRef.close();
  }

}
