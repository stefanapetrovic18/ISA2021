import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MedicineService} from '../../../../service/business/medicine.service';
import {Medicine} from '../../../../model/business/medicine';
import {ItemService} from '../../../../service/business/item.service';
import {Item} from '../../../../model/business/item';

@Component({
  selector: 'app-item-add',
  templateUrl: './item-add.component.html',
  styleUrls: ['./item-add.component.css']
})
export class ItemAddComponent implements OnInit {
  data = new Item();
  medicine: Medicine[];

  constructor(private dialogRef: MatDialogRef<ItemAddComponent>, private medicineService: MedicineService,
              private itemService: ItemService) { }

  ngOnInit() {
    this.medicineService.findAll().subscribe(
      data => {
        this.medicine = data;
      }
    );
  }
  add() {
    this.itemService.create(this.data).subscribe(
      data => {
        this.dialogRef.close({item: data});
      }
    );
  }
  close() {
    this.dialogRef.close();
  }

}
