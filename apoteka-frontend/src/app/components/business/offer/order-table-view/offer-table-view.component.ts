import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Offer } from 'src/app/model/business/offer';
import { OfferService } from 'src/app/service/business/offer.service';
import { OfferAddComponent } from '../offer-add/offer-add.component';
import { OfferDeleteComponent } from '../offer-delete/offer-delete.component';
import { OfferEditComponent } from '../offer-edit/offer-edit.component';
import { OfferViewComponent } from '../offer-view/offer-view.component';

@Component({
  selector: 'app-offer-table-view',
  templateUrl: './offer-table-view.component.html',
  styleUrls: ['./offer-table-view.component.css']
})
export class OfferTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Offer[];
  columns = ['expiryDate', 'y'];
  actions = ['view', 'edit', 'delete', 'medicine'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private offerService: OfferService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.offerService.findAll().subscribe(
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
    );
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
    this.dialog.open(OfferAddComponent);
  }

  view(input: Offer) {
    this.dialog.open(OfferViewComponent, {
      data: input
    });
  }

  edit(input: Offer) {
    this.dialog.open(OfferEditComponent, {
      data: input
    });
  }

  delete(input: Offer) {
    this.dialog.open(OfferDeleteComponent, {
      data: input
    });
  }
  medicine(input: Offer) {
    this.router.navigateByUrl('lekovi?offerID=' + input.id);
  }

}
