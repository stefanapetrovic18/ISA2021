import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Promotion } from 'src/app/model/business/promotion';
import { PromotionService } from 'src/app/service/business/promotion.service';
import { PromotionAddComponent } from '../promotion-add/promotion-add.component';
import { PromotionDeleteComponent } from '../promotion-delete/promotion-delete.component';
import { PromotionEditComponent } from '../promotion-edit/promotion-edit.component';
import { PromotionViewComponent } from '../promotion-view/promotion-view.component';

@Component({
  selector: 'app-promotion-table-view',
  templateUrl: './promotion-table-view.component.html',
  styleUrls: ['./promotion-table-view.component.css']
})
export class PromotionTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Promotion[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private promotionService: PromotionService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.promotionService.findAll().subscribe(
      data => {
        this.data = data;
        if (this.data !== undefined && this.data.length > 0) {
          this.dataSource = new MatTableDataSource(this.data);
          console.log(this.dataSource);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
          this.router.navigateByUrl('');
        }
      }, error => {
        window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
        this.router.navigateByUrl('');
      }
    )
  }

  applyFilter(value: any) {
    this.dataSource.filter = value.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  filter() {
    // Kreiraj objekat za filtriranje i izbaci podatke iz tabele.
    // Objekat sadrzi:
    // - datum (gornja i donja granica)
    // - cena (gornja i donja granica)
    // - farmaceut (izbor)
  }

  add() {
    this.dialog.open(PromotionAddComponent);
  }

  view(input: Promotion) {
    this.dialog.open(PromotionViewComponent, {
      data: input
    });
  }

  edit(input: Promotion) {
    this.dialog.open(PromotionEditComponent, {
      data: input
    });
  }

  delete(input: Promotion) {
    this.dialog.open(PromotionDeleteComponent, {
      data: input
    });
  }

}
