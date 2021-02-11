import { Component, OnInit } from '@angular/core';
import {Pharmacy} from '../../../../model/business/pharmacy';
import {PharmacyService} from '../../../../service/business/pharmacy.service';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-reservation-add',
  templateUrl: './reservation-add.component.html',
  styleUrls: ['./reservation-add.component.css']
})
export class ReservationAddComponent implements OnInit {

  data: Pharmacy[];
  output: Pharmacy;
  date: Date;

  constructor(private pharmacyService: PharmacyService, private dialogRef: MatDialogRef<ReservationAddComponent>) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.data = data;
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
