import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacist-add',
  templateUrl: './pharmacist-add.component.html',
  styleUrls: ['./pharmacist-add.component.css']
})
export class PharmacistAddComponent implements OnInit {

  data = new Pharmacist();
  output = new Pharmacist();

  constructor(private pharmacistService: PharmacistService, private dialogRef: MatDialogRef<PharmacistAddComponent>) { }

  ngOnInit() {
  }

  add() {
    this.pharmacistService.create(this.data).subscribe(
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
