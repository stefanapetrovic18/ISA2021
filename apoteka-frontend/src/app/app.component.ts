import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginRequest } from './auth/login-request';
import { TokenStorageService } from './auth/token-storage.service';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Informacioni sistem apoteka';
  user = false;
  type = '';
  constructor(private router: Router, private token: TokenStorageService, private dialog: MatDialog) {}
  ngOnInit() {
    this.userCheck();
  }
  login() {
    const dialogRef = this.dialog.open(LoginComponent, {
      data: new LoginRequest()
    });
    dialogRef.afterClosed().subscribe(result => {
      this.user = true;
      this.userCheck();
    });
  }
  logout() {
    const dialogRef = this.token.signOut();
    this.user = false;
    this.userCheck();
  }
  register() {
    const dialogRef = this.dialog.open(RegisterComponent);
    dialogRef.afterClosed().subscribe(result => {
      this.userCheck();
    });
  }
  userCheck() {
    const tkn = this.token.getAuthorities();
    if (tkn.includes('ROLE_PATIENT')) {
      this.user = true;
    } else if (tkn.includes('ROLE_DERMATOLOGIST')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/dermatolog');
    } else if (tkn.includes('ROLE_PHARMACIST')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/farmaceut');
    } else if (tkn.includes('ROLE_PHARMACY_ADMIN')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/administrator-apoteke');
    } else if (tkn.includes('ROLE_SYSTEM_ADMIN')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/administrator-sistema');
    } else if (tkn.includes('ROLE_SUPPLIER')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/dobavljac');
    } else {
      this.router.navigateByUrl('dashboard/visitor');
    }
  }
  userCheckX() {
    const tkn = this.token.getAuthorities();
    if (tkn.includes('ROLE_PATIENT')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/pacijent');
    } else if (tkn.includes('ROLE_DERMATOLOGIST')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/dermatolog');
    } else if (tkn.includes('ROLE_PHARMACIST')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/farmaceut');
    } else if (tkn.includes('ROLE_PHARMACY_ADMIN')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/administrator-apoteke');
    } else if (tkn.includes('ROLE_SYSTEM_ADMIN')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/administrator-sistema');
    } else if (tkn.includes('ROLE_SUPPLIER')) {
      this.user = true;
      this.router.navigateByUrl('dashboard/dobavljac');
    } else {
      this.router.navigateByUrl('dashboard/visitor');
    }
  }
}
