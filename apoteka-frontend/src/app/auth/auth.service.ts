import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginRequest } from './login-request';
import { Observable } from 'rxjs';
import { JwtResponse } from './jwt-response';
import { RegistrationRequest } from './registration-request';
import { PasswordChangeRequest } from './password-change-request';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private signinURL = 'http://localhost:8080/api/user/login';
  private signupURL = 'http://localhost:8080/api/user/register';
  private requestTokenURL = 'http://localhost:8080/api/user/request-token?email=';
  private confirmURL = 'http://localhost:8080/api/user/confirm?token=';
  private changePasswordURL = 'http://localhost:8080/api/user/change-password';
  private getUsersWithoutTypeURL = 'http://localhost:8080/api/user/get/without-type';

  constructor(private http: HttpClient) { }

  attemptAuth(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.signinURL, credentials, httpOptions);
  }

  register(request: RegistrationRequest): Observable<any> {
    return this.http.post<any>(this.signupURL, request, httpOptions);
  }

  requestToken(email: string): Observable<any> {
    return this.http.get<any>(this.requestTokenURL + email);
  }

  confirm(token: string): Observable<any> {
    return this.http.get<any>(this.confirmURL + token);
  }

  changePassword(request: PasswordChangeRequest): Observable<any> {
    return this.http.post<any>(this.changePasswordURL, request, httpOptions);
  }

  getUsersWithoutType(): Observable<any> {
    return this.http.get<any>(this.getUsersWithoutTypeURL, httpOptions);
  }

}
