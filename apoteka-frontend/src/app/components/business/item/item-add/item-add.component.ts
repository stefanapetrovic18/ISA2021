import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MedicineService} from '../../../../service/business/medicine.service';
import {Medicine} from '../../../../model/business/medicine';
import {ItemService} from '../../../../service/business/item.service';
import {Item} from '../../../../model/business/item';
import { Stockpile } from 'src/app/model/business/stockpile';
import { StockpileService } from 'src/app/service/business/stockpile.service';

@Component({
  selector: 'app-item-add',
  templateUrl: './item-add.component.html',
  styleUrls: ['./item-add.component.css']
})
export class ItemAddComponent implements OnInit {
  data = new Item();
  stockpile = new Stockpile();
  medicine: Medicine[];

  constructor(private dialogRef: MatDialogRef<ItemAddComponent>, private medicineService: MedicineService,
              private itemService: ItemService, private stockpileService: StockpileService) { }

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
        let item = data;
        this.stockpile.medicine = this.data.medicine;
        this.stockpileService.create(this.stockpile).subscribe(
          data => {
            this.dialogRef.close({item: item});
          }, error => {
            window.alert('Dodavanje zaliha nije uspelo.');
          }
        )
      }, error => {
        window.alert('Dodavanje stavke u cenovnik nije uspelo.');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }

}
