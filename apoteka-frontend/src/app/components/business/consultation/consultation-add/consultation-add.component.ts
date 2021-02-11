import { Component, OnInit } from '@angular/core';
import {PharmacyService} from '../../../../service/business/pharmacy.service';
import {PharmacistService} from '../../../../service/user/pharmacist.service';
import {DermatologistService} from '../../../../service/user/dermatologist.service';
import {MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';

@Component({
  selector: 'app-consultation-add',
  templateUrl: './consultation-add.component.html',
  styleUrls: ['./consultation-add.component.css']
})
export class ConsultationAddComponent implements OnInit {
  date = new Date();

  constructor(private router: Router, private dialogRef: MatDialogRef<ConsultationAddComponent>) { }

  ngOnInit() {
  }

  search() {
    this.router.navigateByUrl('apoteka?localDateTime=' + this.date.toString());
  }

  close() {
    this.dialogRef.close();
  }

}
