import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Promotion } from 'src/app/model/business/promotion';
import { PromotionService } from 'src/app/service/business/promotion.service';

@Component({
  selector: 'app-promotion-add',
  templateUrl: './promotion-add.component.html',
  styleUrls: ['./promotion-add.component.css']
})
export class PromotionAddComponent implements OnInit {
  data: Promotion;
  constructor(private promotionService: PromotionService, private dialogRef: MatDialogRef<PromotionAddComponent>) { }

  ngOnInit() {
  }
  add() {
    this.promotionService.create(this.data).subscribe(
      data => {
        window.alert('Promocija je kreirana!');
      }, error => {
        window.alert('Promocija nije kreirana!');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }

}
