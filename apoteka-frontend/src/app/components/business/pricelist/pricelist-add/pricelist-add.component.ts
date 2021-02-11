import { Component, OnInit } from '@angular/core';
import {PricelistService} from '../../../../service/business/pricelist.service';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {ItemAddComponent} from '../../item/item-add/item-add.component';
import {Pricelist} from '../../../../model/business/pricelist';

@Component({
  selector: 'app-pricelist-add',
  templateUrl: './pricelist-add.component.html',
  styleUrls: ['./pricelist-add.component.css']
})
export class PricelistAddComponent implements OnInit {
  data = new Pricelist();

  constructor(private pricelistService: PricelistService, private dialog: MatDialog, private router: Router,
              private dialogRef: MatDialogRef<PricelistAddComponent>) { }

  ngOnInit() {
    this.data.items = [];
  }
  add() {
    this.pricelistService.create(this.data).subscribe(
      data => {
        window.alert('Cenovnik dodat!');
      }, error => {
        window.alert('Cenovnik nije dodat!');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }
  addItem() {
    const dialogRef = this.dialog.open(ItemAddComponent);
    dialogRef.afterClosed().subscribe(
      result => {
        this.data.items.push(result);
      }
    );
  }

}
