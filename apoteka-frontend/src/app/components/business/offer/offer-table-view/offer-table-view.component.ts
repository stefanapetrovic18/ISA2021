import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
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
  columns = ['shippingDate', 'price'];
  actions = ['supplier', 'accept', 'reject'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private offerService: OfferService, private router: Router, private dialog: MatDialog, private route: ActivatedRoute) {}
  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      if (params.get('orderID') !== undefined && params.get('orderID') !== null) {
        this.offerService.findByOrder(Number(params.get('orderID'))).subscribe(
          data => {
            this.data = data;
            this.dataSource = new MatTableDataSource(this.data);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          }, error => {
            window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
            this.router.navigateByUrl('');
          }
        );
      } else {
        this.offerService.findAll().subscribe(
          data => {
            this.data = data;
            if (this.data !== undefined && this.data.length > 0) {
              this.dataSource = new MatTableDataSource(this.data);
              this.dataSource.paginator = this.paginator;
              this.dataSource.sort = this.sort;
            } else {
              window.alert('Podaci ne postoje!');
            // this.router.navigateByUrl('');
            }
          }, error => {
            window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
            this.router.navigateByUrl('');
          }
        );
      }
    });

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
    this.offerService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }

  accept(input: Offer) {
    this.offerService.accept(input).subscribe(
      data => {
        window.alert('Prihvaćeno!');
        location.reload();
      }, error => {
        window.alert('Nije prihvaćeno!');
      }
    );
  }

  reject(input: Offer) {
    this.offerService.reject(input).subscribe(
      data => {
        window.alert('Odbijeno!');
        location.reload();
      }, error => {
        window.alert('Nije odbijeno!');
      }
    );
  }

  checkDate(input: Offer) {
    return input.shippingDate < new Date();
  }

}
