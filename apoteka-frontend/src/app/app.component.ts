import { Component } from '@angular/core';
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
export class AppComponent {
  title = 'Informacioni sistem apoteka';
  user = false;
  constructor(private router: Router, private token: TokenStorageService, private dialog: MatDialog) {}
  login() {
    const dialogRef = this.dialog.open(LoginComponent, {
      data: new LoginRequest()
    });
    dialogRef.afterClosed().subscribe(result => {
      this.user = true;
      // location.reload();
    });
  }
  logout() {
    const dialogRef = this.token.signOut();
    this.user = false;
    // location.reload();
  }
  register() {
    const dialogRef = this.dialog.open(RegisterComponent);
    dialogRef.afterClosed().subscribe(result => {
      // location.reload();
    });
  }
}
